package dev.sethdegay.hict7.core.designsystem.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@ExperimentalMaterial3Api
@Composable
fun Hict7TopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    rightIcon: @Composable () -> Unit = {},
    leftIcon: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
            )
        },
        actions = { rightIcon.invoke() },
        navigationIcon = leftIcon,
        scrollBehavior = scrollBehavior,
        colors = colors,
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun Hict7TopAppBarPreview() {
    Hict7TopAppBar(title = "Test")
}
