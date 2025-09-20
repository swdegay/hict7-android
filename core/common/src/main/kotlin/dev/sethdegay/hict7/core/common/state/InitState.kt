package dev.sethdegay.hict7.core.common.state

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

sealed class InitState {
    data object Initializing : InitState()
    data object Success : InitState()
    data class Error(val status: Int? = null, val message: String? = null) : InitState()
}

fun StateFlow<InitState>.asReadyState(
    onError: (status: Int?, message: String?) -> Unit,
): Flow<Boolean> =
    this.map { state ->
        when (state) {
            is InitState.Error -> {
                onError(state.status, state.message)
                false
            }

            is InitState.Initializing -> false
            is InitState.Success -> true
        }
    }.distinctUntilChanged()
