package dev.sethdegay.hict7.feature.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sethdegay.hict7.core.audio.SfxManager
import dev.sethdegay.hict7.core.audio.TtsManager
import dev.sethdegay.hict7.core.common.calculateDuration
import dev.sethdegay.hict7.core.common.unixTimeNow
import dev.sethdegay.hict7.core.data.repository.CalendarEventRepository
import dev.sethdegay.hict7.core.data.repository.UserPreferencesRepository
import dev.sethdegay.hict7.core.data.repository.WorkoutRepository
import dev.sethdegay.hict7.core.model.CalendarEvent
import dev.sethdegay.hict7.core.model.Exercise
import dev.sethdegay.hict7.core.model.Workout
import dev.sethdegay.hict7.core.timer.CountdownManager
import dev.sethdegay.hict7.feature.timer.util.readyState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel(assistedFactory = TimerViewModel.Factory::class)
class TimerViewModel @AssistedInject constructor(
    @Assisted val workoutId: Long,
    private val workoutRepository: WorkoutRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val calendarEventRepository: CalendarEventRepository,
    private val ttsManager: TtsManager,
    private val sfxManager: SfxManager,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(workoutId: Long): TimerViewModel
    }

    private var tickSound: Boolean = true
    private var completionSound: Boolean = true

    private val countdownManager: CountdownManager<Exercise> =
        object : CountdownManager<Exercise>() {
            override fun onStart(index: Int) {
                super.onStart(index)
                ttsManager.speak(list.value[index].title)
            }

            override fun onTick(time: Duration) {
                super.onTick(time)
                when (time) {
                    5.seconds, 3.seconds, 1.seconds -> {
                        if (tickSound) {
                            sfxManager.playTickOdd()
                        }
                    }

                    4.seconds, 2.seconds -> {
                        if (tickSound) {
                            sfxManager.playTickEven()
                        }
                    }

                    0.seconds -> {
                        if (completionSound) {
                            sfxManager.playBell()
                        }
                    }
                }
            }

            override fun onDurationRequest(element: Exercise): Duration =
                if (time.value > Duration.ZERO) {
                    time.value
                } else {
                    element.duration
                }

            override fun onError(e: Exception) {
                _uiState.value = TimerUiState.ERROR
            }

            override fun onFinish() {
                _uiState.value = TimerUiState.LOADING
                writeEvent()
            }
        }

    private lateinit var workout: Workout
    private var startTimestamp: Long = 0

    private val _uiState = MutableStateFlow(TimerUiState.LOADING)
    val uiState: StateFlow<TimerUiState>
        get() = _uiState

    private val _exitFlag = MutableStateFlow(false)
    val exitFlag: StateFlow<Boolean>
        get() = _exitFlag

    val exercises = countdownManager.list
    val index = countdownManager.index
    val time = countdownManager.time
    val timerState = countdownManager.state

    val overallProgress: StateFlow<Float> = index.map {
        (it?.let { i -> (i.toFloat() / exercises.value.lastIndex) } ?: 0f)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0f,
    )

    fun toggleTimer() {
        when (countdownManager.state.value) {
            CountdownManager.State.STARTED -> {
                stop()
            }

            CountdownManager.State.PAUSED -> {
                sfxManager.resume()
                countdownManager.startTimer()
            }

            CountdownManager.State.STOPPED -> {}
        }
    }

    fun stop() {
        ttsManager.stop()
        sfxManager.pause()
        countdownManager.pauseTimer()
    }

    fun moveToNext() = countdownManager.moveToNext()

    fun moveToPrevious() = countdownManager.moveToPrevious()

    init {
        viewModelScope.launch {
            try {
                workout = workoutRepository.workout(workoutId)
            } catch (_: Exception) {
                _uiState.value = TimerUiState.ERROR
                return@launch
            }
            try {
                managersReadyState(
                    exercises = workout.exercises,
                    scope = this,
                    managerPreferences = getManagerPreferences(),
                    onError = { _: Int?, _: String? -> _uiState.value = TimerUiState.ERROR },
                )
                    .distinctUntilChanged()
                    .first { it }
                    .apply {
                        _uiState.value = TimerUiState.SUCCESS
                        countdownManager.startTimer()
                        startTimestamp = unixTimeNow
                    }
            } catch (_: NoSuchElementException) {
                _uiState.value = TimerUiState.ERROR
            }
        }
    }

    private suspend fun getManagerPreferences(): ManagerPreferences {
        return userPreferencesRepository.userData.first().let {
            ManagerPreferences(
                tickSound = it.tickSound,
                completionSound = it.completionSound,
                speakExercise = it.speakExercise,
            )
        }.also {
            tickSound = it.tickSound
            completionSound = it.completionSound
        }
    }

    private fun managersReadyState(
        exercises: List<Exercise>,
        scope: CoroutineScope,
        managerPreferences: ManagerPreferences,
        onError: (Int?, String?) -> Unit,
    ): Flow<Boolean> {
        val countdownManagerReadyState =
            countdownManager.readyState(scope = scope, onError = onError) {
                countdownManager.setList(exercises)
            }

        return when {
            managerPreferences.speakExercise && (managerPreferences.tickSound || managerPreferences.completionSound) -> {
                val sfxManagerReadyState = sfxManager.readyState(scope = scope, onError = onError)
                val ttsManagerReadyState = ttsManager.readyState(scope = scope, onError = onError)
                combine(
                    ttsManagerReadyState,
                    sfxManagerReadyState,
                    countdownManagerReadyState,
                ) { ttsManager, sfxManager, countdownManager ->
                    ttsManager && sfxManager && countdownManager
                }
            }

            managerPreferences.speakExercise -> {
                val ttsManagerReadyState = ttsManager.readyState(scope = scope, onError = onError)
                combine(
                    ttsManagerReadyState,
                    countdownManagerReadyState,
                ) { ttsManager, countdownManager ->
                    ttsManager && countdownManager
                }
            }

            else -> {
                val sfxManagerReadyState = sfxManager.readyState(scope = scope, onError = onError)
                combine(
                    sfxManagerReadyState,
                    countdownManagerReadyState,
                ) { sfxManager, countdownManager ->
                    sfxManager && countdownManager
                }
            }
        }
    }

    private fun writeEvent(timeout: Duration = 10.seconds) {
        val now = unixTimeNow
        val calendarEvent = CalendarEvent(
            workout = workout,
            startTimestamp = startTimestamp,
            endTimestamp = now,
            duration = calculateDuration(
                start = startTimestamp,
                end = now
            ),
        )
        viewModelScope.launch {
            try {
                withTimeout(timeout) {
                    calendarEventRepository.insertCalendarEvent(calendarEvent)
                }
            } catch (_: TimeoutCancellationException) {
                _uiState.value = TimerUiState.ERROR
            } finally {
                _exitFlag.value = true
            }
        }
    }

    override fun onCleared() {
        ttsManager.release()
        sfxManager.release()
        countdownManager.release()
        super.onCleared()
    }
}

private data class ManagerPreferences(
    val tickSound: Boolean,
    val completionSound: Boolean,
    val speakExercise: Boolean,
)
