package ru.lavafrai.percentages.fragments

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.lavafrai.percentages.R
import java.io.StringReader


@Preview
@Composable
fun InvalidDataDialog(onConfirm: () -> Unit = {}) {
    AlertDialog(
        icon = { Icon(Icons.Default.Warning, null) },
        title = { Text(text = stringResource(R.string.error)) },
        text = { Text(text = stringResource(R.string.invalid_data_error)) },
        onDismissRequest = {},
        confirmButton = { TextButton(onClick = onConfirm) {
            Text(text = stringResource(id = R.string.ok))
        } },

    )
}