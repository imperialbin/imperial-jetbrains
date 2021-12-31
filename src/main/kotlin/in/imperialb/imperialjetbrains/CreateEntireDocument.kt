package `in`.imperialb.imperialjetbrains

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import khttp.post
import khttp.responses.Response
import org.json.JSONObject

class CreateEntireDocument: AnAction() {
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = false

        // Returning early because it is already set to false.
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR)
        if(editor === null) return

        e.presentation.isEnabled = true
    }

    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR)
        if(editor === null) return

        val vFile = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        val fileExtension = vFile?.extension
        val content = editor.document.text

        val res: Response = post("${Utils.V1_URI}/document", json=mapOf("content" to content, "settings" to mapOf("language" to fileExtension)))
        if(res.statusCode != 200) {
            println("Errorroororor" + res.statusCode.toString())
            return
        }
        val obj: JSONObject = res.jsonObject

        println(obj)

        Notify.successNotification(e.project,"Success!")
    }
}