package info.alihabibi.chortkeh.navigation

import info.alihabibi.designsystem.R
import info.alihabibi.profile.Profile
import info.alihabibi.ui.navigation.BottomNavItemData

enum class BottomNavItems(
    val labelResId: Int,
    val iconResId: Int,
    val enabledIconResId: Int,
    val route: Any
) {

    HOME(
        R.string.home,
        R.drawable.home,
        R.drawable.home_enabled,
        Home
    ),

    PROFILE(
        R.string.profile,
        R.drawable.profile,
        R.drawable.profile_enabled,
        Profile
    ),

    REMINDER(
        R.string.reminder,
        R.drawable.reminder,
        R.drawable.reminder_enabled,
        Reminder
    ),

    REPORTS(
        R.string.reports,
        R.drawable.reports,
        R.drawable.reports_enabled,
        Report
    );

    fun toUiData(): BottomNavItemData = BottomNavItemData(labelResId, iconResId, enabledIconResId, route)

}