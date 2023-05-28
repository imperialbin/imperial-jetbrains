// SettingsService.kt

package `in`.imperialb.imperial

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "SettingsService",
    storages = [Storage("imperialSettings.xml")]
)
@Service
class SettingsService : PersistentStateComponent<SettingsService.State> {

    data class State(var settings: Settings = Settings())

    private var state = State()

    var settings: Settings
        get() = state.settings
        set(value) {
            state.settings = value
        }

    override fun getState(): State {
        return state
    }

    override fun loadState(state: State) {
        this.state = state
    }

    companion object {
        val instance: SettingsService
            get() = com.intellij.openapi.application.ApplicationManager.getApplication()
                .getService(SettingsService::class.java)
    }
}
