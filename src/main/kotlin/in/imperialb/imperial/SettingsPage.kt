// SettingsPage.kt

package `in`.imperialb.imperial

import java.awt.GridLayout
import javax.swing.*

class SettingsPage {
    private val panel = JPanel(GridLayout(0, 1))

    val apiTokenTextField = JTextField("API Token")
    val longURLs = JCheckBox("Enable Longer URLs")
    val shortURLs = JCheckBox("Enable Shorter URLs")
    val imageEmbed = JCheckBox("Enable Image Embeds")
    val expiration = JTextField("Expiration")
    val encrypted = JCheckBox("Enable Encryption")
    val gist = JCheckBox("Enable Gists")

    init {
        panel.add(JLabel("API Token:"))
        panel.add(apiTokenTextField)

        panel.add(JLabel("Enable Longer URLs:"))
        panel.add(longURLs)

        panel.add(JLabel("Enable Shorter URLs:"))
        panel.add(shortURLs)

        panel.add(JLabel("Enable Image Embeds:"))
        panel.add(imageEmbed)

        panel.add(JLabel("Expiration:"))
        panel.add(expiration)

        panel.add(JLabel("Enable Encryption:"))
        panel.add(encrypted)

        panel.add(JLabel("Enable Gists:"))
        panel.add(gist)
    }

    val rootPanel: JComponent
        get() = panel

    fun applyChangesToSettings(settings: Settings) {
        settings.longURLs = longURLs.isSelected
        settings.apiToken = apiTokenTextField.text
        settings.shortURLs = shortURLs.isSelected
        settings.imageEmbed = imageEmbed.isSelected
        settings.expiration = expiration.text.toIntOrNull()
        settings.encrypted = encrypted.isSelected
        settings.gist = gist.isSelected
    }

    fun reset(settings: Settings) {
        longURLs.isSelected = settings.longURLs
        apiTokenTextField.text = settings.apiToken
        shortURLs.isSelected = settings.shortURLs
        imageEmbed.isSelected = settings.imageEmbed
        expiration.text = settings.expiration?.toString() ?: ""
        encrypted.isSelected = settings.encrypted
        gist.isSelected = settings.gist
    }
}
