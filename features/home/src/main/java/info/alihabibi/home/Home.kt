package info.alihabibi.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun HomeDestination() {

    HomeScreen()

}

@Composable
private fun HomeScreen() {

    Text(text = "home", fontSize = 32.sp)

}