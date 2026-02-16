package info.alihabibi.otp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OtpDestination() {

    OtpScreen()

}

@Composable
private fun OtpScreen() {

    OtpAddNumberScreen()

}

@Preview(showBackground = true)
@Composable
private fun OtpPreview() {

    OtpScreen()

}