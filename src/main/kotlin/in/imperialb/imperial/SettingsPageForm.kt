// SettingsPageForm.kt

package `in`.imperialb.imperial

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class SettingsPageForm : Configurable {
    private val settingsPanel = SettingsPage()
    private val settingsService = SettingsService.instance

    override fun createComponent(): JComponent? {
        reset()
        return settingsPanel.rootPanel
    }

    override fun isModified(): Boolean {
        val currentSettings = settingsService.settings
        return (
                settingsPanel.apiTokenTextField.text != currentSettings.apiToken ||
                        settingsPanel.longURLs.isSelected != currentSettings.longURLs ||
                        settingsPanel.shortURLs.isSelected != currentSettings.shortURLs ||
                        settingsPanel.imageEmbed.isSelected != currentSettings.imageEmbed ||
                        settingsPanel.expiration.text.toIntOrNull() != currentSettings.expiration ||
                        settingsPanel.encrypted.isSelected != currentSettings.encrypted ||
                        settingsPanel.gist.isSelected != currentSettings.gist
                )
    }

    override fun apply() {
        val currentSettings = settingsService.settings
        settingsPanel.applyChangesToSettings(currentSettings)
    }

    override fun reset() {
        val currentSettings = settingsService.settings
        settingsPanel.reset(currentSettings)
    }

    override fun getDisplayName(): String {
        return "IMPERIAL Settings"
    }
}
