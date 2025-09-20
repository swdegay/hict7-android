package dev.sethdegay.hict7.core.designsystem.component

import android.icu.number.NumberFormatter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import java.util.Locale

@Composable
private fun Metrics(modifier: Modifier = Modifier, data: Long, title: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = NumberFormatter.withLocale(Locale.getDefault()).format(data).toString(),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = title,
            minLines = 2,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun MetricsGroup(modifier: Modifier = Modifier, vararg pairs: Pair<Long, String>) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        pairs.forEach { pair ->
            Metrics(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                data = pair.first,
                title = pair.second,
            )
        }
    }
}
