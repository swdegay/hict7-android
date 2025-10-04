package dev.sethdegay.hict7.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sethdegay.hict7.core.common.unixTimeNow
import dev.sethdegay.hict7.core.data.repository.CalendarEventRepository
import dev.sethdegay.hict7.core.data.repository.UiStatePrefsRepository
import dev.sethdegay.hict7.core.data.repository.WorkoutRepository
import dev.sethdegay.hict7.core.model.ExerciseFilter
import dev.sethdegay.hict7.core.model.HeatMapLevel
import dev.sethdegay.hict7.core.model.IntervalType
import dev.sethdegay.hict7.core.model.Workout
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Year
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val calendarEventRepository: CalendarEventRepository,
    private val uiStatePrefsRepository: UiStatePrefsRepository,
) : ViewModel() {

    private val _heatMapStartDate = MutableStateFlow(Year.now().atMonth(1).atDay(1))
    val heatMapStartDate: StateFlow<LocalDate>
        get() = _heatMapStartDate

    private val _heatMapEndDate = MutableStateFlow(LocalDate.now())
    val heatMapEndDate: StateFlow<LocalDate>
        get() = _heatMapEndDate

    private var expandedIdJob: Job? = null
    private var calendarEventsJob: Job? = null

    private val _uiState = MutableStateFlow(HomeUiState.LOADING)
    val uiState: StateFlow<HomeUiState>
        get() = _uiState

    private val _calendarEventsUiState =
        MutableStateFlow<CalendarEventsUiState>(CalendarEventsUiState.Loading)
    val calendarEventsUiState: StateFlow<CalendarEventsUiState>
        get() = _calendarEventsUiState

    val heatMapData: StateFlow<Map<LocalDate, HeatMapLevel>> =
        calendarEventRepository.eventCountsForRange(
            startDate = heatMapStartDate.value,
            endDate = heatMapEndDate.value,
            zoneId = ZoneId.systemDefault(),
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyMap(),
        )

    val expandedId: StateFlow<Long?> = uiStatePrefsRepository.expandedId
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    private val _exerciseFilter = uiStatePrefsRepository.exerciseFilter
    val exerciseFilter: StateFlow<ExerciseFilter> = _exerciseFilter
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExerciseFilter(showWarmUp = true, showRest = true, showCoolDown = true),
        )

    val workouts: StateFlow<List<Workout>> = _exerciseFilter.map { filter ->
        workoutRepository.bookmarkedWorkouts(filter.allowedTypes())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    fun setVisibleCalendarEventDate(date: LocalDate?) {
        calendarEventsJob?.cancel()
        if (date == null) {
            _calendarEventsUiState.value = CalendarEventsUiState.Loading
            return
        }
        calendarEventsJob = viewModelScope.launch {
            val zone = ZoneId.systemDefault()
            _calendarEventsUiState.value = CalendarEventsUiState.Success(
                events = calendarEventRepository.calendarEventsRange(
                    start = date.atStartOfDay(zone).toEpochSecond(),
                    end = date.plusDays(1).atStartOfDay(zone).toEpochSecond(),
                )
            )
        }
    }

    fun setExpandedId(id: Long) {
        expandedIdJob?.cancel()
        expandedIdJob = viewModelScope.launch {
            if (id == expandedId.value) {
                uiStatePrefsRepository.setExpandedId(null)
            } else {
                uiStatePrefsRepository.setExpandedId(id)
            }
        }
    }

    fun saveWorkout(title: String, description: String?, onWorkoutSaved: (Long) -> Unit) {
        viewModelScope.launch {
            val now = unixTimeNow
            val id = workoutRepository.saveWorkout(
                workout = Workout(
                    title = title,
                    description = description,
                    bookmarked = false,
                    dateCreated = now,
                    dateModified = now,
                    exercises = emptyList(),
                )
            )
            onWorkoutSaved(id)
        }
    }

    fun setShowWarmUp(showWarmUp: Boolean) {
        viewModelScope.launch { uiStatePrefsRepository.setShowWarmUp(showWarmUp) }
    }

    fun setShowRest(showRest: Boolean) {
        viewModelScope.launch { uiStatePrefsRepository.setShowRest(showRest) }
    }

    fun setShowCoolDown(showCoolDown: Boolean) {
        viewModelScope.launch { uiStatePrefsRepository.setShowCoolDown(showCoolDown) }
    }

    init {
        _uiState.value = HomeUiState.SUCCESS
    }

    override fun onCleared() {
        expandedIdJob?.cancel()
        calendarEventsJob?.cancel()
        super.onCleared()
    }
}

private fun ExerciseFilter.allowedTypes(): List<IntervalType> {
    val out = arrayListOf<IntervalType>()
    out.add(IntervalType.NORMAL)
    if (showWarmUp) out.add(IntervalType.WARM_UP)
    if (showRest) out.add(IntervalType.REST)
    if (showCoolDown) out.add(IntervalType.COOL_DOWN)
    return out
}
