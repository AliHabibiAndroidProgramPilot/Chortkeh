package info.alihabibi.common_android.snackbar

data class SnackBarEvent(
    val message: String,
    val actionTitle: String? = null,
    val action: (() -> Unit)? = null,
)
