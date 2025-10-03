package dev.sethdegay.hict7.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.sethdegay.hict7.feature.editor.EditorScreen
import dev.sethdegay.hict7.feature.editor.EditorViewModel
import dev.sethdegay.hict7.feature.home.HomeScreen
import dev.sethdegay.hict7.feature.settings.SettingsScreen
import dev.sethdegay.hict7.feature.timer.TimerScreen
import dev.sethdegay.hict7.feature.timer.TimerViewModel
import dev.sethdegay.hict7.libraries.LibrariesScreen

@Composable
fun Hict7NavigationGraph(
    modifier: Modifier = Modifier,
    startDestination: NavKey = HomeRoute,
) {
    val backStack = rememberNavBackStack(startDestination)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
    ) { key ->
        when (key) {
            is EditorRoute -> NavEntry(key) {
                EditorScreen(
                    navigateUp = { backStack.removeLastOrNull() },
                    viewModel = hiltViewModel<EditorViewModel, EditorViewModel.Factory>(
                        creationCallback = { factory -> factory.create(key.workoutId) }
                    )
                )
            }

            is HomeRoute -> NavEntry(key) {
                HomeScreen(
                    navigateUp = { backStack.removeLastOrNull() },
                    navigateToEditor = { backStack.add(EditorRoute(it)) },
                    navigateToTimer = { backStack.add(TimerRoute(it)) },
                    navigateToSettings = { backStack.add(SettingsRoute) },
                    viewModel = hiltViewModel(),
                )
            }

            is LibrariesRoute -> NavEntry(key) {
                LibrariesScreen(
                    navigateUp = { backStack.removeLastOrNull() },
                )
            }

            is TimerRoute -> NavEntry(key) {
                TimerScreen(
                    navigateUp = { backStack.removeLastOrNull() },
                    viewModel = hiltViewModel<TimerViewModel, TimerViewModel.Factory>(
                        creationCallback = { factory -> factory.create(key.workoutId) }
                    ),
                )
            }

            is SettingsRoute -> NavEntry(key) {
                SettingsScreen(
                    navigateUp = { backStack.removeLastOrNull() },
                    navigateToOssLicensesActivity = { backStack.add(LibrariesRoute) },
                    viewModel = hiltViewModel(),
                )
            }

            else -> {
                error("Unknown key: $key")
            }
        }
    }
}
