package dev.sethdegay.hict7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.sethdegay.hict7.core.designsystem.theme.Hict7Theme
import dev.sethdegay.hict7.navigation.Hict7NavigationGraph
import dev.sethdegay.hict7.util.applyThemeSettings

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val themeSettings by applyThemeSettings(viewModel.uiState)
        setContent {
            Hict7Theme(
                darkTheme = themeSettings.darkTheme,
                dynamicColor = themeSettings.dynamicColor,
            ) {
                Hict7NavigationGraph()
            }
        }

        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.showSplashScreen() }
    }
}
