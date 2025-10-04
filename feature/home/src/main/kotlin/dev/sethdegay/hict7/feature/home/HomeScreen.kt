package dev.sethdegay.hict7.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MediumExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.designsystem.component.CommonErrorScreen
import dev.sethdegay.hict7.core.designsystem.component.CommonLoadingScreen
import dev.sethdegay.hict7.core.designsystem.component.Hict7TopAppBar
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIcon
import dev.sethdegay.hict7.core.designsystem.util.asComposableIconButton
import dev.sethdegay.hict7.core.model.ExerciseFilter
import dev.sethdegay.hict7.core.model.HeatMapLevel
import dev.sethdegay.hict7.core.model.Workout
import dev.sethdegay.hict7.core.ui.CalendarEventsBottomSheet
import dev.sethdegay.hict7.core.ui.ExerciseFilterBottomSheet
import dev.sethdegay.hict7.core.ui.HeatMapCalendar
import dev.sethdegay.hict7.core.ui.WorkoutCardGroup
import dev.sethdegay.hict7.core.ui.WorkoutInitBottomSheet
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    navigateUp: () -> Unit,
    navigateToEditor: (id: Long?) -> Unit,
    navigateToTimer: (id: Long) -> Unit,
    navigateToSettings: () -> Unit,
    viewModel: HomeViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val calendarEventsUiState by viewModel.calendarEventsUiState.collectAsState()
    val heatMapData by viewModel.heatMapData.collectAsState()
    val heatMapStartDate by viewModel.heatMapStartDate.collectAsState()
    val heatMapEndDate by viewModel.heatMapEndDate.collectAsState()
    val expandedId by viewModel.expandedId.collectAsState()
    val exerciseFilter by viewModel.exerciseFilter.collectAsState()
    val workouts by viewModel.workouts.collectAsState()

    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var showWorkoutInitBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Hict7TopAppBar(
                title = stringResource(string.home_top_app_bar_title),
                rightIcon = Hict7Icons.Settings.asComposableIconButton(
                    onClick = navigateToSettings,
                    contentDescription = stringResource(string.home_top_app_bar_right_icon_content_description),
                ),
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = expandedId == null) {
                MediumExtendedFloatingActionButton(
                    onClick = { showWorkoutInitBottomSheet = true },
                    containerColor = ButtonDefaults.buttonColors().containerColor,
                ) {
                    Hict7Icons.Add.asComposableIcon(modifier = Modifier.size(32.dp)).invoke()
                    Text("Tap to add timer")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { padding ->
        when (uiState) {
            HomeUiState.SUCCESS -> HomeScreen(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                heatMapData = heatMapData,
                heatMapStartDate = heatMapStartDate,
                heatMapEndDate = heatMapEndDate,
                calendarEventsUiState = calendarEventsUiState,
                onDateClicked = { date -> viewModel.setVisibleCalendarEventDate(date) },
                workouts = workouts,
                listState = listState,
                navigateToEditor = navigateToEditor,
                navigateToTimer = navigateToTimer,
                expandedId = expandedId,
                onExpandedIdChange = { id -> viewModel.setExpandedId(id) },
                exerciseFilter = exerciseFilter,
                onShowWarmUpChanged = { viewModel.setShowWarmUp(it) },
                onShowRestChanged = { viewModel.setShowRest(it) },
                onShowCoolDownChanged = { viewModel.setShowCoolDown(it) },
            )

            HomeUiState.LOADING -> CommonLoadingScreen(
                modifier = Modifier.padding(padding)
            )

            HomeUiState.ERROR -> CommonErrorScreen(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                onReturnClick = navigateUp,
            )
        }
    }

    if (showWorkoutInitBottomSheet) {
        var loading by remember { mutableStateOf(false) }
        WorkoutInitBottomSheet(
            loading = loading,
            mainButtonContent = {
                Text(text = stringResource(string.home_workout_init_bottom_sheet_main_button_text))
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Hict7Icons.ArrowForward.asComposableIcon().invoke()
            },
            onSaveClicked = {
                loading = true
                viewModel.saveWorkout(
                    title = it.title,
                    description = it.description,
                    onWorkoutSaved = { id ->
                        navigateToEditor(id)
                        showWorkoutInitBottomSheet = false
                    },
                )
            },
            onDismissRequest = { showWorkoutInitBottomSheet = false }
        )
    }
}

@ExperimentalMaterial3ExpressiveApi
@ExperimentalMaterial3Api
@Composable
private fun HomeScreen(
    modifier: Modifier,
    heatMapData: Map<LocalDate, HeatMapLevel>,
    heatMapStartDate: LocalDate,
    heatMapEndDate: LocalDate,
    calendarEventsUiState: CalendarEventsUiState,
    onDateClicked: (LocalDate?) -> Unit,
    workouts: List<Workout>,
    listState: LazyListState,
    navigateToEditor: (Long?) -> Unit,
    navigateToTimer: (id: Long) -> Unit,
    expandedId: Long?,
    onExpandedIdChange: (Long) -> Unit,
    exerciseFilter: ExerciseFilter,
    onShowWarmUpChanged: (Boolean) -> Unit,
    onShowRestChanged: (Boolean) -> Unit,
    onShowCoolDownChanged: (Boolean) -> Unit,
) {
    var showCalendarEventsBottomSheet by remember { mutableStateOf(false) }
    var showExerciseFilterBottomSheet by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = modifier,
        state = listState,
    ) {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                HeatMapCalendar(
                    data = heatMapData,
                    startDate = heatMapStartDate,
                    endDate = heatMapEndDate,
                    onDateClicked = { date ->
                        onDateClicked(date)
                        showCalendarEventsBottomSheet = true
                    },
                )
            }
        }
        item {
            WorkoutCardGroup(
                workouts = workouts,
                navigateToTimer = navigateToTimer,
                expandedId = expandedId,
                onExpandedIdChange = onExpandedIdChange,
                onLongClick = { navigateToEditor(it) },
                title = stringResource(string.home_workout_card_group_title),
                onFilterIconClicked = { showExerciseFilterBottomSheet = true }
            )
        }
        item {
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
    if (showCalendarEventsBottomSheet) {
        CalendarEventsBottomSheet(
            loading = calendarEventsUiState.showLoadingIndicator(),
            events = calendarEventsUiState.events,
            onDismissRequest = {
                showCalendarEventsBottomSheet = false
                onDateClicked(null)
            },
        )
    }
    if (showExerciseFilterBottomSheet) {
        ExerciseFilterBottomSheet(
            loading = false,
            exerciseFilter = exerciseFilter,
            onShowWarmUpChanged = onShowWarmUpChanged,
            onShowRestChanged = onShowRestChanged,
            onShowCoolDownChanged = onShowCoolDownChanged,
            onDismissRequest = { showExerciseFilterBottomSheet = false },
        )
    }
}
