package dev.sethdegay.hict7

import dev.sethdegay.hict7.core.model.ThemeConfig
import dev.sethdegay.hict7.core.model.UserData

sealed interface MainUiState {
    data object Loading : MainUiState

    data class Success(val userData: UserData) : MainUiState {
        override val useDynamicColor: Boolean = userData.dynamicColor

        override fun useDarkTheme(isSystemInDarkTheme: Boolean): Boolean =
            when (userData.themeConfig) {
                ThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme
                ThemeConfig.LIGHT -> false
                ThemeConfig.DARK -> true
            }
    }

    fun showSplashScreen(): Boolean = this is Loading

    val useDynamicColor: Boolean get() = true

    fun useDarkTheme(isSystemInDarkTheme: Boolean): Boolean = isSystemInDarkTheme
}
