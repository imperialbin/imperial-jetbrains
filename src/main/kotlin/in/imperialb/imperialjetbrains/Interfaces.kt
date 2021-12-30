package `in`.imperialb.imperialjetbrains

interface APIResponseStructure {
    val success: Boolean;
    val message: String?;
    val errors: Any?;
    val data: Any?;
}