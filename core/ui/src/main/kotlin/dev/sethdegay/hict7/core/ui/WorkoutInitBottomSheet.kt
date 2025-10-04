package dev.sethdegay.hict7.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.common.res.R.string

data class WorkoutInitContent(
    val title: String,
    val description: String?,
)

private val titles = arrayOf(
    string.common_workout_title_placeholder_1,
    string.common_workout_title_placeholder_2,
    string.common_workout_title_placeholder_3,
    string.common_workout_title_placeholder_4,
    string.common_workout_title_placeholder_5,
    string.common_workout_title_placeholder_6,
    string.common_workout_title_placeholder_7,
)

private val descriptions = arrayOf(
    string.common_workout_description_placeholder_1,
    string.common_workout_description_placeholder_2,
    string.common_workout_description_placeholder_3,
    string.common_workout_description_placeholder_4,
    string.common_workout_description_placeholder_5,
    string.common_workout_description_placeholder_6,
    string.common_workout_description_placeholder_7,
    string.common_workout_description_placeholder_8,
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WorkoutInitBottomSheet(
    modifier: Modifier = Modifier,
    loading: Boolean,
    workoutInitContent: WorkoutInitContent? = null,
    mainButtonContent: @Composable RowScope.() -> Unit,
    onSaveClicked: (WorkoutInitContent) -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        if (loading) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(32.dp),
            ) {
                LoadingIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp),
                )
            }
        } else {
            WorkoutInitBottomSheetContent(
                workoutInitContent = workoutInitContent,
                mainButtonContent = mainButtonContent,
                onSaveClicked = onSaveClicked,
            )
        }
    }
}

@Composable
private fun WorkoutInitBottomSheetContent(
    modifier: Modifier = Modifier,
    workoutInitContent: WorkoutInitContent? = null,
    onSaveClicked: (WorkoutInitContent) -> Unit,
    mainButtonContent: @Composable RowScope.() -> Unit,
) {
    val titleState = rememberTextFieldState(initialText = workoutInitContent?.title ?: "")
    val descriptionState =
        rememberTextFieldState(initialText = workoutInitContent?.description ?: "")
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            state = titleState,
            label = { Text(stringResource(string.common_workout_title_label)) },
            labelPosition = TextFieldLabelPosition.Attached(),
            placeholder = { Text(stringResource(titles.random())) },
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            state = descriptionState,
            label = { Text(stringResource(string.common_workout_description_label)) },
            labelPosition = TextFieldLabelPosition.Attached(),
            placeholder = { Text(stringResource(descriptions.random())) },
        )
        WorkoutInitBottomSheetMainButton(
            mainButtonContent = mainButtonContent,
            onClick = {
                onSaveClicked(
                    WorkoutInitContent(
                        title = titleState.text.toString(),
                        description = descriptionState.text.toString(),
                    )
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun WorkoutInitBottomSheetMainButton(
    modifier: Modifier = Modifier,
    mainButtonContent: @Composable RowScope.() -> Unit,
    onClick: () -> Unit,
    buttonSize: Dp = ButtonDefaults.MediumContainerHeight
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(buttonSize),
        onClick = onClick,
        contentPadding = ButtonDefaults.contentPaddingFor(buttonSize),
        content = mainButtonContent,
    )
}