package dev.sethdegay.hict7.core.designsystem.component.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Hict7PreferenceEntry(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            icon()
        }
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            if (description != null) {
                Text(text = description)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Hict7PreferenceEntryPreview_AllParams() {
    Hict7PreferenceEntry(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        title = "Sample",
        description = "Bacon ipsum dolor amet kielbasa tri-tip pork belly",
        icon = {
            Icon(
                imageVector = Icons.Default.Preview,
                contentDescription = null,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun Hict7PreferenceEntryPreview_NoIcon() {
    Hict7PreferenceEntry(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        title = "Sample",
        description = "Bacon ipsum dolor amet kielbasa tri-tip pork belly",
        icon = null,
    )
}

@Preview(showBackground = true)
@Composable
private fun Hict7PreferenceEntryPreview_NoDescription() {
    Hict7PreferenceEntry(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        title = "Sample",
        description = null,
        icon = null,
    )
}

@Preview(showBackground = true)
@Composable
private fun Hict7PreferenceEntryPreview_DescriptionOverflow1() {
    Hict7PreferenceEntry(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        title = "Sample",
        description = "Bacon ipsum dolor amet kielbasa tri-tip pork belly ham hock ball tip capicola andouille shank. Frankfurter porchetta tongue, boudin picanha leberkas biltong brisket cupim buffalo. Short ribs tri-tip filet mignon, cupim chuck tail picanha fatback. Sirloin tenderloin jerky frankfurter tail pork belly. Tenderloin ribeye meatloaf bresaola chislic, sirloin pork belly short loin porchetta biltong meatball. Strip steak jerky cow bresaola drumstick. Kielbasa pancetta drumstick sausage shankle meatloaf.",
        icon = {
            Icon(
                imageVector = Icons.Default.Preview,
                contentDescription = null,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun Hict7PreferenceEntryPreview_DescriptionOverflow2() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Hict7PreferenceEntry(
            modifier = Modifier
                .weight(1f)
                .padding(PaddingValues(end = 16.dp)),
            title = "Sample",
            description = "Bacon ipsum dolor amet kielbasa tri-tip pork belly ham hock ball tip capicola andouille shank. Frankfurter porchetta tongue, boudin picanha leberkas biltong brisket cupim buffalo. Short ribs tri-tip filet mignon, cupim chuck tail picanha fatback. Sirloin tenderloin jerky frankfurter tail pork belly. Tenderloin ribeye meatloaf bresaola chislic, sirloin pork belly short loin porchetta biltong meatball. Strip steak jerky cow bresaola drumstick. Kielbasa pancetta drumstick sausage shankle meatloaf.",
            icon = {
                Icon(
                    imageVector = Icons.Default.Preview,
                    contentDescription = null,
                )
            },
        )
        Switch(checked = true, onCheckedChange = null)
    }
}