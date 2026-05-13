package info.alihabibi.common

import android.content.Context
import android.util.Log

object Utils {

    fun <T> T.loog(tag: String = "loog", param: String = "param"): T {
        return this.apply {
            Log.d(tag, "$param = $this")
        }
    }

    fun getAppVersionName(context: Context): String =
        context.packageManager.getPackageInfo(context.packageName, 0).versionName.orEmpty()

}