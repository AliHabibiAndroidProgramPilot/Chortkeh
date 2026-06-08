package info.alihabibi.ui.navigation

import info.alihabibi.designsystem.R

enum class BottomNavItem(val labelResId: Int, val iconResId: Int, val enabeldIconResId: Int) {

    HOME(R.string.home, R.drawable.home, R.drawable.home_enabled),

    PROFILE(R.string.profile, R.drawable.profile, R.drawable.profile_enabled),

    REMINDER(R.string.reminder, R.drawable.reminder, R.drawable.reminder_enabled),

    REPORTS(R.string.reports, R.drawable.reports, R.drawable.reports_enabled);

}
