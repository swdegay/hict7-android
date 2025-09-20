package dev.sethdegay.hict7.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.common.unixTimestampToShortTime
import dev.sethdegay.hict7.core.model.CalendarEvent

@ExperimentalMaterial3ExpressiveApi
@ExperimentalMaterial3Api
@Composable
fun CalendarEventsBottomSheet(
    modifier: Modifier = Modifier,
    loading: Boolean,
    events: List<CalendarEvent>,
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
                CalendarEventBottomSheetContent(
                    events = events,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun CalendarEventBottomSheetContent(events: List<CalendarEvent>) {
    events.forEachIndexed { i, event ->
        val start =
            remember(event.startTimestamp) { unixTimestampToShortTime(event.startTimestamp) }
        val end = remember(event.endTimestamp) { unixTimestampToShortTime(event.endTimestamp) }
        val (minutes, seconds) =
            event.duration.toComponents { minutes, seconds, _ -> Pair(minutes, seconds) }
        Column {
            Text(
                text = "$start - $end " +
                        "(${minutes}${stringResource(string.common_minutes_suffix)} " +
                        "${seconds}${stringResource(string.common_seconds_suffix)})",
                style = MaterialTheme.typography.labelSmall,
            )
            Text(
                text = event.workout.title,
                style = MaterialTheme.typography.bodyMediumEmphasized,
            )
        }
        if (i != events.lastIndex) {
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

