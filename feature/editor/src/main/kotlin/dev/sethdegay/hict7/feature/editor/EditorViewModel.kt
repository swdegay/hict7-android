package dev.sethdegay.hict7.feature.editor

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel(assistedFactory = EditorViewModel.Factory::class)
class EditorViewModel @AssistedInject constructor(
    @Assisted val workoutId: Long?,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(workoutId: Long?): EditorViewModel
    }

    private val _id: MutableStateFlow<Long?> = MutableStateFlow(null)
    val id: StateFlow<Long?>
        get() = _id

    init {
        _id.value = workoutId
    }
}