package dev.sethdegay.hict7.feature.editor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sethdegay.hict7.core.common.unixTimeNow
import dev.sethdegay.hict7.core.data.repository.WorkoutRepository
import dev.sethdegay.hict7.core.model.Exercise
import dev.sethdegay.hict7.core.model.Workout
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
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

    private val _editableWorkout: MutableStateFlow<Workout?> = MutableStateFlow(null)
    val editableWorkout: StateFlow<Workout?>
        get() = _editableWorkout

    fun setBookmarked(bookmarked: Boolean) {
        _editableWorkout.value = _editableWorkout.value?.copy(bookmarked = bookmarked)
    }

    fun setTitle(title: String) {
        _editableWorkout.value = _editableWorkout.value?.copy(title = title)
    }

    fun setDescription(description: String?) {
        _editableWorkout.value = _editableWorkout.value?.copy(description = description)
    }

    fun setExercises(exercises: List<Exercise>) {
        _editableWorkout.value = _editableWorkout.value?.copy(exercises = exercises)
    }

    init {
        viewModelScope.launch {
            _editableWorkout
                .debounce(AUTOSAVE_DELAY)
                .filterNotNull()
                .distinctUntilChanged()
                .collect { workout ->
                    val id = workoutRepository.saveWorkout(
                        workout.copy(
                            dateModified = unixTimeNow,
                            exercises = workout.exercises.mapIndexed { i, exercise ->
                                exercise.copy(order = i + 1)
                            },
                        )
                    )
                    Log.d("AUTOSAVE", "ID: $id, Timestamp: $unixTimeNow")
                }
        }

        workoutId?.let { id ->
            viewModelScope.launch {
                _editableWorkout.value = workoutRepository.workout(id)
            }
        }
    }
}
