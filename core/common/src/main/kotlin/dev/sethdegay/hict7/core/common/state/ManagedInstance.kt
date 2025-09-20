package dev.sethdegay.hict7.core.common.state

import kotlinx.coroutines.flow.StateFlow

interface ManagedInstance {
    val initState: StateFlow<InitState>
    fun initialize()
    fun release()
}

fun ManagedInstance.runOnlyWhenInitialized(runnable: () -> Unit) {
    if (initState.value == InitState.Success) {
        runnable.invoke()
    }
}
