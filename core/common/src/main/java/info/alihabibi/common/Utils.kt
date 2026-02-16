package info.alihabibi.common

import android.util.Log

fun <T> T.loog(tag: String = "loog", param: String = "param"): T {
    return this.apply {
        Log.d(tag, "$param = $this")
    }
}