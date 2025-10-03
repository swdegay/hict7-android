package dev.sethdegay.hict7.libraries

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults
import com.mikepenz.aboutlibraries.ui.compose.android.rememberLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.m3.LicenseDialogBody
import com.mikepenz.aboutlibraries.ui.compose.m3.libraryColors
import dev.sethdegay.hict7.R
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrariesScreen(navigateUp: () -> Unit) {
    val libraries by rememberLibraries(R.raw.aboutlibraries)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(string.settings_open_source_licenses_title))
                },
                navigationIcon = Hict7Icons.ArrowBack.asComposableIconButton(
                    onClick = navigateUp,
                    contentDescription = stringResource(string.common_navigate_up_content_description),
                ),
                scrollBehavior = scrollBehavior,
            )
        }
    ) { padding ->
        LibrariesContainer(
            modifier = Modifier.fillMaxSize(),
            libraries = libraries,
            contentPadding = padding,
            showAuthor = false,
            showVersion = false,
            showDescription = false,
            showFundingBadges = false,
            showLicenseBadges = false,
            licenseDialogBody = { library, modifier ->
                LicenseDialogBody(
                    library = library,
                    colors = LibraryDefaults.libraryColors(),
                    modifier = modifier.padding(16.dp),
                )
            },
            licenseDialogConfirmText = stringResource(string.common_dialog_close).uppercase(),
        )
    }
}
