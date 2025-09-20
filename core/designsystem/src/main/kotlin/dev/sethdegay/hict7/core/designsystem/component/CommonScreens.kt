package dev.sethdegay.hict7.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.sethdegay.hict7.core.designsystem.icon.Hict7Icons
import dev.sethdegay.hict7.core.designsystem.util.asComposableIcon

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CommonLoadingScreen(
    modifier: Modifier = Modifier,
    title: String? = null,
) {
    Box(modifier = modifier.fillMaxSize()) {
        LoadingIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(96.dp),
        )
        title?.apply {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                text = this,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

// TODO update screen contents
@Composable
fun CommonErrorScreen(
    modifier: Modifier = Modifier,
    title: String = "Oops! Something went wrong.",
    message: String = "Sorry, but it looks like an unexpected error occurred. " +
            "Please try again later; sometimes these issues are temporary.",
    returnButtonText: String = "Return Home",
    onReturnClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Hict7Icons.Error.asComposableIcon(modifier = Modifier.size(64.dp)).invoke()
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
        Button(onClick = onReturnClick) { Text(text = returnButtonText) }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonLoadingScreenPreview() {
    CommonLoadingScreen()
}

@Preview(showBackground = true)
@Composable
private fun CommonErrorScreenPreview() {
    CommonErrorScreen(
        modifier = Modifier,
        onReturnClick = {},
    )
}