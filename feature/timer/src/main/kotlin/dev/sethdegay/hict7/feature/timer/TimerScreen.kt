package dev.sethdegay.hict7.feature.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.designsystem.component.CommonErrorScreen
import dev.sethdegay.hict7.core.designsystem.component.CommonLoadingScreen
import dev.sethdegay.hict7.core.designsystem.component.CountdownTimerText
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIcon
import dev.sethdegay.hict7.core.designsystem.util.asComposableIconButton
import dev.sethdegay.hict7.core.model.IntervalType
import dev.sethdegay.hict7.core.timer.CountdownManager
import dev.sethdegay.hict7.core.ui.WorkoutProgressIndicator
import dev.sethdegay.hict7.feature.timer.util.Caffeine
import dev.sethdegay.hict7.feature.timer.util.LifecycleEffect
import kotlin.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(navigateUp: () -> Unit, viewModel: TimerViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    val exercises by viewModel.exercises.collectAsState()
    val index by viewModel.index.collectAsState()
    val time by viewModel.time.collectAsState()
    val timerState by viewModel.timerState.collectAsState()
    val progress by viewModel.overallProgress.collectAsState()
    val exitFlag by viewModel.exitFlag.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val topAppBarExpanded by remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction == 0f
        }
    }

    if (timerState == CountdownManager.State.STARTED) {
        Caffeine()
    }

    LifecycleEffect { _, event ->
        if (event == Lifecycle.Event.ON_STOP) {
            viewModel.stop()
        }
    }

    LaunchedEffect(exitFlag) {
        if (exitFlag) {
            navigateUp.invoke()
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    if (uiState == TimerUiState.SUCCESS) {
                        Text(text = exercises[index!!].title)
                    } else {
                        Text(text = "")
                    }
                },
                navigationIcon = Hict7Icons.ArrowBack.asComposableIconButton(
                    onClick = navigateUp,
                    contentDescription = stringResource(string.common_navigate_up_content_description),
                ),
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                )
            )
        }
    ) { padding ->
        when (uiState) {
            TimerUiState.SUCCESS -> {
                TimerScreen(
                    modifier = Modifier.padding(padding),
                    isAtStart = index == 0,
                    isAtEnd = index == exercises.lastIndex,
                    time = time,
                    timerState = timerState,
                    moveToPrevious = { viewModel.moveToPrevious() },
                    toggleTimer = { viewModel.toggleTimer() },
                    moveToNext = { viewModel.moveToNext() },
                    progress = progress,
                    topAppBarExpanded = topAppBarExpanded,
                    intervalType = exercises[index!!].type,
                )
            }

            TimerUiState.LOADING -> CommonLoadingScreen(
                modifier = Modifier.padding(padding),
            )

            TimerUiState.ERROR -> CommonErrorScreen(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                onReturnClick = navigateUp,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TimerScreen(
    modifier: Modifier = Modifier,
    isAtStart: Boolean,
    isAtEnd: Boolean,
    time: Duration,
    timerState: CountdownManager.State,
    moveToPrevious: () -> Unit,
    toggleTimer: () -> Unit,
    moveToNext: () -> Unit,
    progress: Float,
    topAppBarExpanded: Boolean,
    intervalType: IntervalType,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        WorkoutProgressIndicator(
            progress = progress,
            expanded = topAppBarExpanded,
            intervalType = intervalType,
            timerState = timerState,
        )
        CountdownTimerText(
            modifier = Modifier.align(Alignment.Center),
            duration = time,
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val size = ButtonDefaults.MediumContainerHeight
                OutlinedButton(
                    modifier = Modifier
                        .heightIn(size)
                        .weight(1f),
                    onClick = moveToPrevious,
                    enabled = !isAtStart,
                    contentPadding = ButtonDefaults.contentPaddingFor(size),
                ) {
                    Hict7Icons.Previous.asComposableIcon(
                        modifier = Modifier.size(ButtonDefaults.iconSizeFor(ButtonDefaults.LargeContainerHeight)),
                    ).invoke()
                }
                Spacer(modifier = Modifier.size(ButtonDefaults.iconSpacingFor(size)))
                Button(
                    modifier = Modifier
                        .heightIn(size)
                        .weight(2f),
                    onClick = toggleTimer,
                    contentPadding = ButtonDefaults.contentPaddingFor(size),
                ) {
                    when (timerState) {
                        CountdownManager.State.STARTED -> {
                            Text(
                                text = stringResource(string.timer_pause_button_text),
                                style = ButtonDefaults.textStyleFor(size),
                            )
                        }

                        CountdownManager.State.PAUSED,
                        CountdownManager.State.STOPPED -> {
                            Text(
                                text = stringResource(string.timer_start_button_text),
                                style = ButtonDefaults.textStyleFor(size),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedButton(
                    modifier = Modifier
                        .heightIn(size)
                        .weight(1f),
                    onClick = moveToNext,
                    enabled = !isAtEnd,
                    contentPadding = ButtonDefaults.contentPaddingFor(size),
                ) {
                    Hict7Icons.Next.asComposableIcon(
                        modifier = Modifier.size(ButtonDefaults.iconSizeFor(ButtonDefaults.LargeContainerHeight)),
                    ).invoke()
                }
            }
        }
    }
}
