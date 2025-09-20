package dev.sethdegay.hict7.core.data.repository

import dev.sethdegay.hict7.core.model.ThemeConfig
import dev.sethdegay.hict7.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userData: Flow<UserData>

    suspend fun setThemeConfig(themeConfig: ThemeConfig)

    suspend fun setDynamicColor(dynamicColor: Boolean)

    suspend fun setTickSound(tickSound: Boolean)

    suspend fun setCompletionSound(completionSound: Boolean)

    suspend fun setSpeakExercise(speakExercise: Boolean)
}