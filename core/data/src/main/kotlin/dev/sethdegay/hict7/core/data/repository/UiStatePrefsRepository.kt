package dev.sethdegay.hict7.core.data.repository

import dev.sethdegay.hict7.core.model.ExerciseFilter
import kotlinx.coroutines.flow.Flow

interface UiStatePrefsRepository {
    val expandedId: Flow<Long?>
    suspend fun setExpandedId(id: Long?)

    val exerciseFilter: Flow<ExerciseFilter>

    suspend fun setShowWarmUp(showWarmUp: Boolean)

    suspend fun setShowRest(showRest: Boolean)

    suspend fun setShowCoolDown(showCoolDown: Boolean)
}
