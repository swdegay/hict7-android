package dev.sethdegay.hict7.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.designsystem.component.preference.Hict7PreferenceEntry

@Composable
private fun animatedLeadingItemShape(
    expanded: Boolean,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    /*
     * 12.dp to match the value of:
     * MaterialTheme.shapes.medium.bottomStart
     * MaterialTheme.shapes.medium.bottomEnd
     */
    size: Dp = 12.dp,
): RoundedCornerShape {
    val animatedCornerSize by animateDpAsState(
        targetValue = if (expanded) {
            0.dp
        } else {
            size
        }
    )
    return RoundedCornerShape(
        bottomEnd = CornerSize(animatedCornerSize),
        bottomStart = CornerSize(animatedCornerSize),
        topStart = shape.topStart,
        topEnd = shape.topEnd,
    )
}

@Composable
fun CardGroupItemAnimatedVisibility(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(300)
        ),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(150)
        ) + fadeOut(
            animationSpec = tween(150)
        ),
        content = content,
    )
}

@Composable
fun ExpandableCardGroupLeadingItem(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    shape: CornerBasedShape = MaterialTheme.shapes.medium,
    colors: CardColors = CardDefaults.cardColors(),
    content: @Composable (PaddingValues) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = animatedLeadingItemShape(
            expanded = expanded,
            shape = shape,
        ),
        colors = colors,
    ) {
        content(contentPadding)
    }
}

@Composable
fun ExpandableCardGroupMiddleItem(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(),
    content: @Composable (PaddingValues) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = middleItemShape(),
        colors = colors,
    ) {
        content(contentPadding)
    }
    CardGroupItemSpacer()
}

@Composable
fun ExpandableCardGroupTrailingItem(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(),
    content: @Composable (PaddingValues) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = trailingItemShape(),
        colors = colors,
    ) {
        content(contentPadding)
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpandableCardGroupLeadingItemPreview() {
    var expanded by remember { mutableStateOf(false) }
    ExpandableCardGroupLeadingItem(expanded = expanded) {
        Hict7PreferenceEntry(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(it),
            title = "Test",
            description = "This is a test",
        )
    }
}
