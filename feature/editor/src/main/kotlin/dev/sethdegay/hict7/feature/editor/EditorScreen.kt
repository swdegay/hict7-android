package dev.sethdegay.hict7.feature.editor

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(navigateUp: () -> Unit, viewModel: EditorViewModel) {
    val id by viewModel.id.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = "ID: $id")
                },
                navigationIcon = Hict7Icons.ArrowBack.asComposableIconButton(
                    onClick = navigateUp,
                    contentDescription = stringResource(string.common_navigate_up_content_description),
                ),
                scrollBehavior = scrollBehavior,
            )
        }
    ) {
        LazyColumn(Modifier.padding(it)) {
        }
    }
}