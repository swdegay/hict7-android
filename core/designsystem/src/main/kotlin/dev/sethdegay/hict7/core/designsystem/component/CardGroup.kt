package dev.sethdegay.hict7.core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.designsystem.component.preference.Hict7PreferenceEntry

private val leadingItemPadding = PaddingValues(
    top = 16.dp,
    start = 16.dp,
    end = 16.dp,
    bottom = 0.dp,
)

private val middleItemPadding = PaddingValues(
    top = 0.dp,
    start = 16.dp,
    end = 16.dp,
    bottom = 0.dp,
)

private val trailingItemPadding = PaddingValues(
    top = 0.dp,
    start = 16.dp,
    end = 16.dp,
    bottom = 16.dp,
)

@Composable
private fun leadingItemShape(shape: CornerBasedShape = MaterialTheme.shapes.medium) =
    RoundedCornerShape(
        bottomEnd = CornerSize(0.dp),
        bottomStart = CornerSize(0.dp),
        topStart = shape.topStart,
        topEnd = shape.topEnd,
    )

@Composable
internal fun middleItemShape() = RoundedCornerShape(0.dp)

@Composable
internal fun trailingItemShape(shape: CornerBasedShape = MaterialTheme.shapes.medium) =
    RoundedCornerShape(
        bottomEnd = shape.bottomEnd,
        bottomStart = shape.bottomStart,
        topStart = CornerSize(0.dp),
        topEnd = CornerSize(0.dp),
    )

internal val contentPadding = PaddingValues(16.dp)

@Composable
fun CardGroupItemSpacer(size: Dp = 1.dp) {
    Spacer(modifier = Modifier.size(size))
}

fun LazyListScope.cardGroupLeadingItem(content: @Composable LazyItemScope.(PaddingValues) -> Unit) {
    item {
        Card(
            modifier = Modifier.padding(leadingItemPadding),
            shape = leadingItemShape(),
        ) {
            content(contentPadding)
        }
    }
    item {
        CardGroupItemSpacer()
    }
}

fun LazyListScope.cardGroupMiddleItem(content: @Composable LazyItemScope.(PaddingValues) -> Unit) {
    item {
        Card(
            modifier = Modifier.padding(middleItemPadding),
            shape = middleItemShape(),
        ) {
            content(contentPadding)
        }
    }
    item {
        CardGroupItemSpacer()
    }
}

fun LazyListScope.cardGroupTrailingItem(content: @Composable LazyItemScope.(PaddingValues) -> Unit) {
    item {
        Card(
            modifier = Modifier.padding(trailingItemPadding),
            shape = trailingItemShape(),
        ) {
            content(contentPadding)
        }
    }
}

@Composable
fun CardGroupTitle(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
) {
    Hict7PreferenceEntry(
        modifier = modifier,
        title = title,
        description = description,
    )
}
