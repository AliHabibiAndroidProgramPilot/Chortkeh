package info.alihabibi.ui.navigation

import info.alihabibi.designsystem.R

enum class BottomNavItem(val labelResId: Int, val iconResId: Int) {

    HOME(R.string.home, R.drawable.home),

    PROFILE(R.string.profile, R.drawable.profile),

    REMINDER(R.string.reminder, R.drawable.reminder),

    REPORTS(R.string.reports, R.drawable.reports);

}
