package info.alihabibi.ui.scaffolds

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    bottomBar: (@Composable () -> Unit)? = null,
    snackBarHost: @Composable () -> Unit = {},
    contentWindowInsets: WindowInsets = WindowInsets.systemBars.only(
        sides = WindowInsetsSides.Bottom + WindowInsetsSides.Start + WindowInsetsSides.End
    ),
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {

    Scaffold(
        modifier = modifier,
        bottomBar = { bottomBar?.invoke() },
        snackbarHost = snackBarHost,
        contentWindowInsets = contentWindowInsets
    ) { innerPadding ->
        content(innerPadding)
    }

}