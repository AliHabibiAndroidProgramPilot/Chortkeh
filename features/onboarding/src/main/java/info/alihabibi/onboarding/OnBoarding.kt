package info.alihabibi.onboarding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun OnBoardingDestination(
    onEnterApplication: () -> Unit
) {

    OnBoardingScreen(onEnterApplication = onEnterApplication)

}

@Composable
private fun OnBoardingScreen(
    onEnterApplication: () -> Unit
) {

    Text("ALi", fontSize = 48.sp)

}