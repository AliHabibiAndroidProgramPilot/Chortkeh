package info.alihabibi.ui.scaffolds

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.alihabibi.ui.snackbars.AppSnackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    fabPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    content: @Composable (innerPadding: androidx.compose.foundation.layout.PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = { topBar?.invoke() },
        bottomBar = { bottomBar?.invoke() },
        floatingActionButton = { floatingActionButton?.invoke() },
        floatingActionButtonPosition = fabPosition,
        contentWindowInsets = contentWindowInsets,
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(bottom = 24.dp),
                hostState = snackBarHostState
            ) { data ->
                AppSnackBar(
                    description = data.visuals.message,
                    isUndoAvailable = data.visuals.withDismissAction
                )
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}