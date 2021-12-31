package `in`.imperialb.imperialjetbrains

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.Messages
import org.json.JSONObject

class CreateDocument: AnAction() {
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabled = false;

        // Returning early because it is already set to false.
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR);
        if(editor === null) return;

        e.presentation.isEnabled = editor.selectionModel.hasSelection();
    }

    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR);
        if(editor === null) return;

        val selectedText = editor.selectionModel.selectedText;
        if (selectedText != null && selectedText.isEmpty()) return;

        val res: khttp.responses.Response = khttp.post("${Utils.V1_URI}/document", json=mapOf("content" to selectedText))
        if(res.statusCode != 200) {
            Notify.errorNotification("Error ${res.statusCode}")
            return;
        }

        val obj: JSONObject = res.jsonObject;
        Notify.successNotification(e.project, "Success! ${obj["success"]}")
    }
}