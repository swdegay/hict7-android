package dev.sethdegay.hict7.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.common.res.R.string

data class WorkoutInitContent(
    val title: String,
    val description: String?,
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WorkoutInitBottomSheet(
    modifier: Modifier = Modifier,
    workoutInitContent: WorkoutInitContent? = null,
    onSaveClicked: (WorkoutInitContent) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val buttonSize = ButtonDefaults.MediumContainerHeight
    val titleState = rememberTextFieldState(initialText = workoutInitContent?.title ?: "")
    val descriptionState =
        rememberTextFieldState(initialText = workoutInitContent?.description ?: "")
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                state = titleState,
                placeholder = { Text(stringResource(string.common_workout_title_placeholder)) },
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                state = descriptionState,
                placeholder = { Text(stringResource(string.common_workout_description_placeholder)) },
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(buttonSize),
                onClick = {
                    onSaveClicked(
                        WorkoutInitContent(
                            title = titleState.text.toString(),
                            description = descriptionState.text.toString(),
                        )
                    )
                },
                contentPadding = ButtonDefaults.contentPaddingFor(buttonSize),
            ) {
                Text(stringResource(string.common_dialog_save))
            }
        }
    }
}