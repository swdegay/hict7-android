package dev.sethdegay.hict7.core.datastore

import androidx.datastore.core.DataStore
import dev.sethdegay.hict7.core.model.ThemeConfig
import dev.sethdegay.hict7.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Hict7PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData: Flow<UserData> = userPreferences.data.map {
        UserData(
            themeConfig = when (it.themeConfig) {
                null,
                ThemeConfigProto.THEME_CONFIG_UNSPECIFIED,
                ThemeConfigProto.UNRECOGNIZED,
                ThemeConfigProto.THEME_CONFIG_FOLLOW_SYSTEM -> ThemeConfig.FOLLOW_SYSTEM

                ThemeConfigProto.THEME_CONFIG_LIGHT -> ThemeConfig.LIGHT
                ThemeConfigProto.THEME_CONFIG_DARK -> ThemeConfig.DARK
            },
            dynamicColor = it.dynamicColor,
            tickSound = it.tickSound,
            completionSound = it.completionSound,
            speakExercise = it.speakExercise,
        )
    }

    suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.themeConfig = when (themeConfig) {
                    ThemeConfig.FOLLOW_SYSTEM -> ThemeConfigProto.THEME_CONFIG_FOLLOW_SYSTEM
                    ThemeConfig.LIGHT -> ThemeConfigProto.THEME_CONFIG_LIGHT
                    ThemeConfig.DARK -> ThemeConfigProto.THEME_CONFIG_DARK
                }
            }
        }
    }

    suspend fun setDynamicColor(dynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.dynamicColor = dynamicColor
            }
        }
    }

    suspend fun setTickSound(tickSound: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.tickSound = tickSound
            }
        }
    }

    suspend fun setCompletionSound(completionSound: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.completionSound = completionSound
            }
        }
    }

    suspend fun setSpeakExercise(speakExercise: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.speakExercise = speakExercise
            }
        }
    }
}
