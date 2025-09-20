package dev.sethdegay.hict7.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.WavyProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.model.IntervalType
import dev.sethdegay.hict7.core.timer.CountdownManager

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BoxScope.WorkoutProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    expanded: Boolean,
    intervalType: IntervalType,
    timerState: CountdownManager.State,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )
    val thickStrokeWidth = with(LocalDensity.current) { 8.dp.toPx() }
    val thickStroke = remember(thickStrokeWidth) {
        Stroke(
            width = thickStrokeWidth,
            cap = StrokeCap.Round,
        )
    }
    LinearWavyProgressIndicator(
        modifier = modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .height(if (expanded) 14.dp else 10.dp),
        progress = { animatedProgress },
        amplitude = when (timerState) {
            CountdownManager.State.STARTED -> {
                if (intervalType == IntervalType.REST || intervalType == IntervalType.COOL_DOWN) {
                    { 0.5f }
                } else {
                    { 1f }
                }
            }

            CountdownManager.State.PAUSED,
            CountdownManager.State.STOPPED -> {
                { 0f }
            }
        },
        stroke = if (expanded) thickStroke else WavyProgressIndicatorDefaults.linearIndicatorStroke,
        trackStroke = if (expanded) thickStroke else WavyProgressIndicatorDefaults.linearTrackStroke,
    )
}
