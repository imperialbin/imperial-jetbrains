// CreateDocument.kt

package `in`.imperialb.imperial

import PasswordDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.minidev.json.JSONObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class Data(
    val id: String,
    val content: String,
    val creator: Creator?,
    val gist_url: String?,
    val views: Int,
    val timestamps: Timestamps,
    val links: Links,
    val settings: DocumentSettings
)

@Serializable
data class Creator(
    val id: String,
    val username: String,
    val icon: String?,
    val documents_made: Int,
    val flags: Int,
)

@Serializable
data class Timestamps(
    val creation: String,
    val expiration: String?
)

@Serializable
data class Links(
    val formatted: String,
    val raw: String
)

@Serializable
data class DocumentSettings(
    val language: String,
    val image_embed: Boolean,
    val instant_delete: Boolean,
    val encrypted: Boolean,
    val public: Boolean,
    val editors: List<String>,
)

@Serializable
data class ResponseData(
    val success: Boolean,
    val data: Data
)

class CreateDocument : AnAction() {
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = false

        // Returning early because it is already set to false.
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR)
        if (editor === null) return

        e.presentation.isEnabled = editor.selectionModel.hasSelection()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR)
        if (editor === null) return

        val selectedText = editor.selectionModel.selectedText
        if (selectedText === null || selectedText.isEmpty()) return

        var settings = SettingsService.instance.settings
        val client = OkHttpClient()

        val payloadSettings = mutableMapOf<String, Any?>(
            // @everyone, if someone knows how to get the language from the editor please make a pr, jetbrains docs suck and i cant find them :(
            "language" to "auto",
            "image_embed" to settings.imageEmbed,
            // ill do this later
            "encrypted" to false,
            "long_urls" to settings.longURLs,
            "short_urls" to settings.shortURLs,
            "expiration" to settings.expiration,
            "create_gist" to settings.gist,
        )


        /*        if (settings.encrypted) {
                    var dialog = PasswordDialog()
                    dialog.show()

                    if (dialog.isOK) {
                        payloadSettings["password"] = dialog.getPassword()
                    }
                    dialog.close(1)
                }*/


        val request = okhttp3.Request.Builder()
            .url("${Constants.V1_URI}/documents")
            .post(
                JSONObject(
                    mapOf(
                        "content" to selectedText,
                        "settings" to payloadSettings,
                        "infer_settings" to true
                    )
                ).toString().toRequestBody("application/json".toMediaTypeOrNull())
            )
            .header("Authorization", settings.apiToken)
            .build()

        val response = client.newCall(request).execute()
        val responseData = response.body?.string()


        if (!response.isSuccessful || responseData == null) {
            Notifications.notifyError("Error creating document.")
            return
        }

        val parsedResponse = Json { ignoreUnknownKeys = true; }.decodeFromString<ResponseData>(responseData)
        val success = parsedResponse.success
        val data = parsedResponse.data

        if (!success) {
            Notifications.notifyError("Error creating document.")
            return
        }

        Notifications.notify("Document ${data.id} successfully created.", data.id)
    }
}
