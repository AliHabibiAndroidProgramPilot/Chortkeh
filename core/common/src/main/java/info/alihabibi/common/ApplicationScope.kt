package info.alihabibi.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class ApplicationScope(
    parentScop: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : CoroutineScope by parentScop