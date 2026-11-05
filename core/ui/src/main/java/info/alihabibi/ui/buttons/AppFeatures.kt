package info.alihabibi.ui.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import info.alihabibi.designsystem.R

data class AppFeatures(
    @get:DrawableRes val iconResId: Int,
    @get:StringRes val title: Int,
    @get:StringRes val subTitle: Int,
    val isEnabled: Boolean
) {
    companion object {
        val appFeatures: List<AppFeatures> = listOf(
            AppFeatures(
                R.drawable.budgeting,
                R.string.budgeting,
                R.string.budgeting_subtitle,
                false
            ),
            AppFeatures(
                R.drawable.piggy_bank,
                R.string.piggy_bank,
                R.string.piggy_bank_subtitle,
                false
            ),
            AppFeatures(
                R.drawable.stairs,
                R.string.stairs,
                R.string.stairs_subtitle,
                false
            )
        )
    }
}