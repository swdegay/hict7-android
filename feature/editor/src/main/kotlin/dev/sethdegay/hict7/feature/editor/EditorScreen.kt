package dev.sethdegay.hict7.feature.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIconButton

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
                        if (bookmarked) {
                            Hict7Icons.BookmarkedChecked.asComposableIconButton(
                                onClick = { viewModel.setBookmarked(false) },
                                contentDescription = stringResource(string.bookmark_remove_content_description),
                            ).invoke()
                        } else {
                            Hict7Icons.BookmarkedUnchecked.asComposableIconButton(
                                onClick = { viewModel.setBookmarked(true) },
                                contentDescription = stringResource(string.bookmark_add_content_description),
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
}