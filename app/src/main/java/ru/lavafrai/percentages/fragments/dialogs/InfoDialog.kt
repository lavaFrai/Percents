package ru.lavafrai.percentages.fragments.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.lavafrai.percentages.R


@Preview
@Composable
fun InfoDialog(onConfirm: () -> Unit = {}) {
    AlertDialog(
        icon = { Icon(Icons.Default.Info, null) },
        title = { Text(text = stringResource(R.string.info)) },
        text = { Text(text = stringResource(R.string.info_dialog)) },
        onDismissRequest = {},
        confirmButton = { TextButton(onClick = onConfirm) {
            Text(text = stringResource(id = R.string.ok))
        } },

        )
}