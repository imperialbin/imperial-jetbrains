import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JPasswordField

class PasswordDialog : DialogWrapper(null) {
    private val passwordField: JPasswordField = JPasswordField(20)

    init {
        title = "Password Dialog"
        init()
    }

    override fun createCenterPanel(): JComponent? {
        val panel = JPanel()
        panel.add(JLabel("Password:"))
        panel.add(passwordField)
        return panel
    }

    fun getPassword(): String {
        return String(passwordField.password)
    }
}
