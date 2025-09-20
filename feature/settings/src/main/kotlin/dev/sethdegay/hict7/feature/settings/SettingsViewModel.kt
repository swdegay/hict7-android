package dev.sethdegay.hict7.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sethdegay.hict7.core.data.repository.UserPreferencesRepository
import dev.sethdegay.hict7.core.model.ThemeConfig
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    val uiState: StateFlow<SettingsUiState> = userPreferencesRepository.userData.map {
        SettingsUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingsUiState.Loading,
    )

    fun setThemeConfig(themeConfig: ThemeConfig) {
        viewModelScope.launch {
            userPreferencesRepository.setThemeConfig(themeConfig)
        }
    }

    fun setDynamicColor(dynamicColor: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setDynamicColor(dynamicColor)
        }
    }

    fun setTickSound(tickSound: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setTickSound(tickSound)
        }
    }

    fun setCompletionSound(completionSound: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setCompletionSound(completionSound)
        }
    }

    fun setSpeakExercise(speakExercise: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setSpeakExercise(speakExercise)
        }
    }
}