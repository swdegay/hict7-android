package dev.sethdegay.hict7.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class EditorRoute(val workoutId: Long?) : NavKey

@Serializable
data object HomeRoute : NavKey

@Serializable
data object LibrariesRoute : NavKey

@Serializable
data class TimerRoute(val workoutId: Long) : NavKey

@Serializable
data object SettingsRoute : NavKey
