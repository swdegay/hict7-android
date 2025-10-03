package dev.sethdegay.hict7.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.sethdegay.hict7.core.common.res.R.string
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIcon
import kotlin.time.Duration

@Composable
private fun CountdownText(
    timeValue: Number,
    prefix: String? = null,
    suffix: String? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily? = FontFamily.Monospace,
    fontWeight: FontWeight? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = prefix?.let { "$it%02d".format(timeValue) }
                ?: "%02d".format(timeValue),
            fontSize = fontSize,
            fontFamily = fontFamily,
            fontWeight = fontWeight,
        )
        suffix?.apply {
            Text(
                text = suffix,
                fontSize = fontSize,
                fontFamily = fontFamily,
                fontWeight = fontWeight,
            )
        }
    }
}

@Composable
private fun CountdownTimerText(
    modifier: Modifier = Modifier,
    minutes: Number,
    seconds: Number,
    fontSize: TextUnit = 124.sp,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End,
    ) {
        CountdownText(
            timeValue = minutes,
            suffix = stringResource(string.common_minutes_suffix),
            fontSize = fontSize,
        )
        CountdownText(
            timeValue = seconds,
            suffix = stringResource(string.common_seconds_suffix),
            fontSize = fontSize,
        )
    }
}

@Composable
fun CountdownTimerText(modifier: Modifier = Modifier, duration: Duration) {
    val (minutes, seconds) =
        duration.toComponents { minutes, seconds, _ -> Pair(minutes, seconds) }
    CountdownTimerText(
        modifier = modifier,
        minutes = minutes,
        seconds = seconds,
    )
}

@Composable
fun CountdownDurationText(
    modifier: Modifier = Modifier,
    minutes: Number,
    seconds: Number,
    fontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (minutes != 0L) {
            CountdownText(
                timeValue = minutes,
                suffix = stringResource(string.common_minutes_suffix),
                fontSize = fontSize,
            )
        }
        if (seconds != 0) {
            CountdownText(
                timeValue = seconds,
                suffix = stringResource(string.common_seconds_suffix),
                fontSize = fontSize,
            )
        }
    }
}

@Composable
fun CountdownDurationText(modifier: Modifier = Modifier, duration: Duration) {
    val (minutes, seconds) =
        duration.toComponents { minutes, seconds, _ -> Pair(minutes, seconds) }
    CountdownDurationText(
        modifier = modifier,
        minutes = minutes,
        seconds = seconds,
    )
}

@Composable
fun CountdownDurationCard(
    duration: Duration,
    icon: @Composable () -> Unit,
) {
    OutlinedCard {
        Row(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon.invoke()
            CountdownDurationText(modifier = Modifier, duration = duration)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CountdownTimerTextPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CountdownTimerText(
            modifier = Modifier,
            duration = Duration.parse("10m 47s")
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CountdownDurationTextPreview() {
    Column {
        CountdownDurationText(duration = Duration.parse("10m 47s"))
        CountdownDurationText(duration = Duration.parse("4m 0s"))
        CountdownDurationText(duration = Duration.parse("30s"))
    }
}

@Preview(showBackground = true)
@Composable
private fun CountdownDurationCardPreview() {
    Column {
        CountdownDurationCard(
            duration = Duration.parse("10m 47s"),
            icon = Hict7Icons.Timer.asComposableIcon(modifier = Modifier.scale(0.6f)),
        )
        CountdownDurationCard(
            duration = Duration.parse("4m 0s"),
            icon = Hict7Icons.Timer.asComposableIcon(modifier = Modifier.scale(0.6f)),
        )
        CountdownDurationCard(
            duration = Duration.parse("30s"),
            icon = Hict7Icons.Timer.asComposableIcon(modifier = Modifier.scale(0.6f)),
        )
    }
}
