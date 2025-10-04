package dev.sethdegay.hict7.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.designsystem.component.CardGroupItemAnimatedVisibility
import dev.sethdegay.hict7.core.designsystem.component.CardGroupItemSpacer
import dev.sethdegay.hict7.core.designsystem.component.CardGroupTitle
import dev.sethdegay.hict7.core.designsystem.component.CountdownDurationCard
import dev.sethdegay.hict7.core.designsystem.component.ExpandableCardGroupLeadingItem
import dev.sethdegay.hict7.core.designsystem.component.ExpandableCardGroupMiddleItem
import dev.sethdegay.hict7.core.designsystem.component.ExpandableCardGroupTrailingItem
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIcon
import dev.sethdegay.hict7.core.designsystem.util.asComposableIconButton
import dev.sethdegay.hict7.core.model.Exercise
import dev.sethdegay.hict7.core.model.Workout
import dev.sethdegay.hict7.core.ui.util.getIcon

private val workoutCardGroupPadding = 16.dp

@Composable
private fun workoutCardGroupItemColors() =
    CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest)

private val subtleInOutEasing = CubicBezierEasing(0.2f, 0.0f, 0.8f, 1.0f)
private const val hiddenItemAnimationDuration = 70
private const val containerAnimationDuration = 48

private fun <T> subtleInOutTweenSpec(durationMillis: Int) = tween<T>(
    durationMillis = durationMillis,
    easing = subtleInOutEasing,
)

private val horizontalEnterTransition =
    fadeIn(animationSpec = subtleInOutTweenSpec(hiddenItemAnimationDuration)) +
            expandHorizontally(animationSpec = subtleInOutTweenSpec(hiddenItemAnimationDuration))
private val horizontalExitTransition =
    fadeOut(animationSpec = subtleInOutTweenSpec(hiddenItemAnimationDuration)) +
            shrinkHorizontally(animationSpec = subtleInOutTweenSpec(hiddenItemAnimationDuration))

private val verticalEnterTransition =
    fadeIn(animationSpec = subtleInOutTweenSpec(hiddenItemAnimationDuration)) +
            expandVertically(animationSpec = subtleInOutTweenSpec(hiddenItemAnimationDuration))
private val verticalExitTransition =
    fadeOut(animationSpec = subtleInOutTweenSpec(hiddenItemAnimationDuration)) +
            shrinkVertically(animationSpec = subtleInOutTweenSpec(hiddenItemAnimationDuration))

/**
 * @see [dev.sethdegay.hict7.core.designsystem.component.preference.Hict7PreferenceEntry]
 */
@Suppress("SpellCheckingInspection")
@Composable
private fun WorkoutCardGroupHeader(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onLongClick: () -> Unit,
    title: String,
    description: String? = null,
    onFabClick: () -> Unit,
    colors: CardColors = CardDefaults.cardColors(),
) {
    ExpandableCardGroupLeadingItem(expanded = expanded, colors = colors) { padding ->
        Row(
            modifier = modifier
                .combinedClickable(
                    onClick = { onExpandedChange(!expanded) },
                    onLongClick = onLongClick,
                )
                .padding(padding),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = horizontalEnterTransition,
                exit = horizontalExitTransition,
            ) {
                FloatingActionButton(
                    onClick = onFabClick,
                    shape = FloatingActionButtonDefaults.shape,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                    ),
                    containerColor = ButtonDefaults.buttonColors().containerColor,
                ) {
                    Hict7Icons.Play.asComposableIcon().invoke()
                }
            }
            Column(
                modifier = Modifier.animateContentSize(
                    animationSpec = subtleInOutTweenSpec(containerAnimationDuration),
                ),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
                AnimatedVisibility(
                    visible = expanded && description != null,
                    enter = verticalEnterTransition,
                    exit = verticalExitTransition,
                ) {
                    Text(text = description!!)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkoutCardGroupHeaderPreview() {
    var expanded by remember { mutableStateOf(false) }
    WorkoutCardGroupHeader(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = it },
        onLongClick = {},
        title = "Test",
        description = "This is a test",
        onFabClick = {},
    )
}

@Composable
private fun WorkoutCardGroup(
    workout: Workout,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onLongClick: (id: Long) -> Unit,
    onFabClick: () -> Unit,
    colors: CardColors = CardDefaults.cardColors(),
) {
    Column {
        WorkoutCardGroupHeader(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = onExpandedChange,
            onLongClick = { workout.id?.let { onLongClick(it) } },
            title = workout.title,
            description = workout.description,
            onFabClick = onFabClick,
            colors = colors,
        )
        CardGroupItemSpacer()
        CardGroupItemAnimatedVisibility(visible = expanded) {
            Column {
                workout.exercises.forEachIndexed { index, exercise ->
                    if (workout.exercises.lastIndex == index) {
                        ExpandableCardGroupTrailingItem(colors = colors) {
                            ExerciseCardGroupItem(exercise, it)
                        }
                    } else {
                        ExpandableCardGroupMiddleItem(colors = colors) {
                            ExerciseCardGroupItem(exercise, it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutCardGroup(
    modifier: Modifier = Modifier,
    workouts: List<Workout>,
    navigateToTimer: (id: Long) -> Unit,
    expandedId: Long?,
    onExpandedIdChange: (Long) -> Unit,
    onLongClick: (id: Long) -> Unit,
    title: String,
    onFilterIconClicked: () -> Unit,
) {
    Column(modifier = modifier.padding(horizontal = workoutCardGroupPadding)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CardGroupTitle(
                title = title,
            )
            Hict7Icons.Filter.asComposableIconButton(
                onClick = onFilterIconClicked,
                contentDescription = stringResource(dev.sethdegay.hict7.core.common.res.R.string.common_filter_exercises_content_description),
            ).invoke()
        }
        Spacer(modifier = Modifier.size(8.dp))
        workouts.forEachIndexed { i, workout ->
            WorkoutCardGroup(
                workout = workout,
                expanded = expandedId == workout.id,
                onExpandedChange = { onExpandedIdChange.invoke(workout.id!!) },
                onLongClick = onLongClick,
                onFabClick = { navigateToTimer.invoke(workout.id!!) },
            )
            if (i != workouts.lastIndex) {
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun ExerciseCardGroupItem(exercise: Exercise, paddingValues: PaddingValues) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = exercise.title)
        CountdownDurationCard(
            duration = exercise.duration,
            icon = exercise.type.getIcon(),
        )
    }
}
