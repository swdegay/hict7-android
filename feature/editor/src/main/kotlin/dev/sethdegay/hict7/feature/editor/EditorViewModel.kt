package dev.sethdegay.hict7.feature.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sethdegay.hict7.core.data.repository.WorkoutRepository
import dev.sethdegay.hict7.core.model.Workout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@HiltViewModel(assistedFactory = EditorViewModel.Factory::class)
class EditorViewModel @AssistedInject constructor(
    @Assisted val workoutId: Long?,
    workoutRepository: WorkoutRepository,
) : ViewModel() {

    private companion object {
        const val AUTOSAVE_DELAY = 250L
    }

    @AssistedFactory
    interface Factory {
        fun create(workoutId: Long?): EditorViewModel
    }

    private val _id: MutableStateFlow<Long?> = MutableStateFlow(workoutId)

    @OptIn(ExperimentalCoroutinesApi::class)
    val workout: StateFlow<Workout?> = _id.flatMapLatest { id ->
        when (id) {
            null -> flowOf(null)
            else -> workoutRepository.workoutFlow(id)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    private val _editableWorkout: MutableStateFlow<Workout?> = MutableStateFlow(null)

    fun setBookmarked(bookmarked: Boolean) {
        _editableWorkout.value = _editableWorkout.value?.copy(bookmarked = bookmarked)
    }

    init {
        viewModelScope.launch {
            _editableWorkout
                .debounce(AUTOSAVE_DELAY)
                .filterNotNull()
                .distinctUntilChanged()
                .collect { workout ->
                    workout.let { it -> workoutRepository.saveWorkout(it) }
                }
        }

        viewModelScope.launch {
            workout.collect { _editableWorkout.value = it }
        }
    }
}
