package `in`.imperialb.imperialjetbrains

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.Messages
import org.json.JSONObject

class CreateDocument: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor: Editor? = e.getData(PlatformDataKeys.EDITOR);
        if(editor === null) return;

        println("hello");

        val selectedText = editor.selectionModel.selectedText;
        if (selectedText != null && selectedText.isEmpty()) return;

        val res: khttp.responses.Response = khttp.post("${Utils.V1_URI}/document", mapOf("content" to selectedText))
        val obj: JSONObject = res.jsonObject;

        println(obj)

        Messages.showMessageDialog("Yo fuck", obj["content"] as String, Messages.getErrorIcon())
    }

}