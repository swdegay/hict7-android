package dev.sethdegay.hict7.feature.home

import dev.sethdegay.hict7.core.model.CalendarEvent

sealed class CalendarEventsUiState {

    data class Success(override val events: List<CalendarEvent>) : CalendarEventsUiState()

    object Loading : CalendarEventsUiState()

    fun showLoadingIndicator(): Boolean = this is Loading

    open val events: List<CalendarEvent> = emptyList()
}