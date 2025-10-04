package dev.sethdegay.hict7.feature.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIcon
import dev.sethdegay.hict7.core.designsystem.util.asComposableIconButton
import dev.sethdegay.hict7.core.ui.WorkoutInitBottomSheet
import dev.sethdegay.hict7.core.ui.WorkoutInitContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(navigateUp: () -> Unit, viewModel: EditorViewModel) {
    val workout by viewModel.workout.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val topAppBarExpanded by remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction == 0f
        }
    }
    var showWorkoutInitBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(text = workout?.title ?: "---")
                        if (topAppBarExpanded) {
                            workout?.description?.apply {
                                Text(
                                    text = this,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                    }
                },
                navigationIcon = Hict7Icons.ArrowBack.asComposableIconButton(
                    onClick = navigateUp,
                    contentDescription = stringResource(string.common_navigate_up_content_description),
                ),
                scrollBehavior = scrollBehavior,
                actions = {
                    workout?.apply {
                        Hict7Icons.Title.asComposableIconButton(
                            onClick = { showWorkoutInitBottomSheet = true },
                            contentDescription = stringResource(string.editor_title_edit_content_description),
                        ).invoke()
                        if (bookmarked) {
                            Hict7Icons.BookmarkedChecked.asComposableIconButton(
                                onClick = { viewModel.setBookmarked(false) },
                                contentDescription = stringResource(string.editor_bookmark_remove_content_description),
                            ).invoke()
                        } else {
                            Hict7Icons.BookmarkedUnchecked.asComposableIconButton(
                                onClick = { viewModel.setBookmarked(true) },
                                contentDescription = stringResource(string.editor_bookmark_add_content_description),
                            ).invoke()
                        }
                    }
                }
            )
        }
    ) {
        LazyColumn(Modifier.padding(it)) {
        }
    }
    if (showWorkoutInitBottomSheet) {
        WorkoutInitBottomSheet(
            workoutInitContent = workout.let {
                if (it != null) {
                    WorkoutInitContent(
                        title = it.title,
                        description = it.description,
                    )
                } else {
                    null
                }
            },
            mainButtonContent = {
                Hict7Icons.Save.asComposableIcon().invoke()
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(string.common_dialog_save))
            },
            onSaveClicked = {
                viewModel.setTitle(it.title)
                viewModel.setDescription(it.description)
                showWorkoutInitBottomSheet = false
            },
            onDismissRequest = { showWorkoutInitBottomSheet = false },
        )
    }
}