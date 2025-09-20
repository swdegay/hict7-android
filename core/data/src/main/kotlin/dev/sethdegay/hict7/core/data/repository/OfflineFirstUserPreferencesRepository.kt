package dev.sethdegay.hict7.core.data.repository

import dev.sethdegay.hict7.core.datastore.Hict7PreferencesDataSource
import dev.sethdegay.hict7.core.model.ThemeConfig
import dev.sethdegay.hict7.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserPreferencesRepository @Inject constructor(
    private val dataSource: Hict7PreferencesDataSource,
) : UserPreferencesRepository {

    override val userData: Flow<UserData> = dataSource.userData

    override suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        dataSource.setThemeConfig(themeConfig)
    }

    override suspend fun setDynamicColor(dynamicColor: Boolean) {
        dataSource.setDynamicColor(dynamicColor)
    }

    override suspend fun setTickSound(tickSound: Boolean) {
        dataSource.setTickSound(tickSound)
    }

    override suspend fun setCompletionSound(completionSound: Boolean) {
        dataSource.setCompletionSound(completionSound)
    }

    override suspend fun setSpeakExercise(speakExercise: Boolean) {
        dataSource.setSpeakExercise(speakExercise)
    }
}