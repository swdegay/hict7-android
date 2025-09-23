package dev.sethdegay.hict7.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.designsystem.component.preference.Hict7BooleanPreference
import dev.sethdegay.hict7.core.model.ExerciseFilter

@ExperimentalMaterial3Api
@ExperimentalMaterial3ExpressiveApi
@Composable
fun ExerciseFilterBottomSheet(
    modifier: Modifier = Modifier,
    loading: Boolean,
    exerciseFilter: ExerciseFilter,
    onShowWarmUpChanged: (Boolean) -> Unit,
    onShowRestChanged: (Boolean) -> Unit,
    onShowCoolDownChanged: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        if (loading) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(32.dp),
            ) {
                LoadingIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp),
                )
            }
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                Hict7BooleanPreference(
                    modifier = modifier.clip(CardDefaults.shape),
                    title = stringResource(string.home_exercise_filter_bottom_sheet_show_warm_up_title),
                    description = null,
                    checked = exerciseFilter.showWarmUp,
                    onCheckedChange = onShowWarmUpChanged,
                    icon = null,
                )
                Hict7BooleanPreference(
                    modifier = modifier.clip(CardDefaults.shape),
                    title = stringResource(string.home_exercise_filter_bottom_sheet_show_rest_title),
                    description = null,
                    checked = exerciseFilter.showRest,
                    onCheckedChange = onShowRestChanged,
                    icon = null,
                )
                Hict7BooleanPreference(
                    modifier = modifier.clip(CardDefaults.shape),
                    title = stringResource(string.home_exercise_filter_bottom_sheet_show_cool_down_title),
                    description = null,
                    checked = exerciseFilter.showCoolDown,
                    onCheckedChange = onShowCoolDownChanged,
                    icon = null,
                )
            }
        }
    }
}