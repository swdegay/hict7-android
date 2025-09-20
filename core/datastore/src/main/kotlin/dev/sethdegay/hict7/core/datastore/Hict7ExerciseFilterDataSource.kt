package dev.sethdegay.hict7.core.datastore

import androidx.datastore.core.DataStore
import dev.sethdegay.hict7.core.model.ExerciseFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Hict7ExerciseFilterDataSource @Inject constructor(
    private val dataStore: DataStore<dev.sethdegay.hict7.core.datastore.ExerciseFilter>,
) {
    val exerciseFilter: Flow<ExerciseFilter> = dataStore.data.map {
        ExerciseFilter(
            showWarmUp = it.showWarmUp,
            showRest = it.showRest,
            showCoolDown = it.showCoolDown,
        )
    }

    suspend fun setShowWarmUp(showWarmUp: Boolean) {
        dataStore.updateData { it.copy { this.showWarmUp = showWarmUp } }
    }

    suspend fun setShowRest(showRest: Boolean) {
        dataStore.updateData { it.copy { this.showRest = showRest } }
    }

    suspend fun setShowCoolDown(showCoolDown: Boolean) {
        dataStore.updateData { it.copy { this.showCoolDown = showCoolDown } }
    }
}