package dev.sethdegay.hict7.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIcon
import dev.sethdegay.hict7.core.model.IntervalType

fun IntervalType.getIcon(scale: Float = 0.6f): @Composable () -> Unit {
    return when (this) {
        IntervalType.WARM_UP -> Hict7Icons.WarmUp
        IntervalType.REST -> Hict7Icons.Rest
        IntervalType.COOL_DOWN -> Hict7Icons.CoolDown
        IntervalType.NORMAL -> Hict7Icons.Timer
    }.asComposableIcon(modifier = Modifier.scale(scale))
}
