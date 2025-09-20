package dev.sethdegay.hict7.feature.timer.util

import dev.sethdegay.hict7.core.common.state.ManagedInstance
import dev.sethdegay.hict7.core.common.state.asReadyState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal fun ManagedInstance.readyState(
    scope: CoroutineScope,
    onError: (status: Int?, message: String?) -> Unit = { _, _ -> },
    initializeOverride: (suspend () -> Unit)? = null,
): Flow<Boolean> {
    val readyState = this.initState.asReadyState(onError)
    scope.launch {
        initializeOverride?.invoke() ?: this@readyState.initialize()
    }
    return readyState
}
