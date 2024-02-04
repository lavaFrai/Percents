package ru.lavafrai.percentages.fragments



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lavafrai.percentages.R
import ru.lavafrai.percentages.model.BankData
import ru.lavafrai.percentages.model.sampleBanks
import ru.lavafrai.percentages.utils.isValidFloat


@Composable
fun BankCard(bankData: BankData, onClose: () -> Unit, ) {
    val depositValid = remember { mutableStateOf(false) }
    val percentsValid = remember { mutableStateOf(false) }
    bankData.valid!!.value = depositValid.value && percentsValid.value && !bankData.removed!!.value

    Card (
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            BankCardControlsRow(bankData.name, onClose);
            BankCardInputRow(stringResource(R.string.deposit), depositValid, bankData.deposit!!) {};
            BankCardInputRow(stringResource(R.string.percents), percentsValid, bankData.percents!!) {};
        }
    }
}


@Composable
fun BankCardControlsRow(bankName: String, onClose: () -> Unit) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) { // Controls row
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
            )
        };

        Text(
            text = stringResource(id = R.string.offer)
        );

        IconButton(onClick = onClose) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.Red
            )
        };
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankCardInputRow(name: String, isValid: MutableState<Boolean>, textOutput: MutableState<Float>, onChanged: () -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
    ) { // Controls row
        val text = remember { mutableStateOf("") }
        textOutput.value = text.value.toFloatOrNull() ?: 0f;
        isValid.value = text.value.isValidFloat() && text.value.isNotEmpty()

        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it.replace(",", ".") },
            label = { Text(name) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            isError = !text.value.isValidFloat() && text.value.isNotEmpty()
        );
    }
}


@Preview
@Composable
fun BankCardPreview() {
    BankCard(bankData = sampleBanks[0]) {}
}