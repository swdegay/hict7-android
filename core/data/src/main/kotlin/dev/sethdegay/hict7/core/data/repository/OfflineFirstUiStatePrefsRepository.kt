package dev.sethdegay.hict7.core.data.repository

import dev.sethdegay.hict7.core.datastore.Hict7ExerciseFilterDataSource
import dev.sethdegay.hict7.core.datastore.prefs.UiStatePrefsDataSource
import dev.sethdegay.hict7.core.model.ExerciseFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUiStatePrefsRepository @Inject constructor(
    private val uiStatePrefsDataSource: UiStatePrefsDataSource,
    private val dataSource: Hict7ExerciseFilterDataSource,
) : UiStatePrefsRepository {
    override val expandedId: Flow<Long?>
        get() = uiStatePrefsDataSource.expandedId

    override suspend fun setExpandedId(id: Long?) = uiStatePrefsDataSource.setExpandedId(id)

    override val exerciseFilter: Flow<ExerciseFilter> = dataSource.exerciseFilter

    override suspend fun setShowWarmUp(showWarmUp: Boolean) {
        dataSource.setShowWarmUp(showWarmUp)
    }

    override suspend fun setShowRest(showRest: Boolean) {
        dataSource.setShowRest(showRest)
    }

    override suspend fun setShowCoolDown(showCoolDown: Boolean) {
        dataSource.setShowCoolDown(showCoolDown)
    }
}