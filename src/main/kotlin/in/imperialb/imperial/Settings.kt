// Settings.kt

package `in`.imperialb.imperial

data class Settings(
    var apiToken: String = "",
    var longURLs: Boolean = false,
    var shortURLs: Boolean = false,
    var imageEmbed: Boolean = false,
    var expiration: Number? = null,
    var encrypted: Boolean = false,
    var gist: Boolean = false
)
