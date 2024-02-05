package ru.lavafrai.percentages.fragments


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lavafrai.percentages.R
import ru.lavafrai.percentages.model.BankData
import ru.lavafrai.percentages.model.createBankData
import ru.lavafrai.percentages.utils.isValidFloat


@Composable
fun BankCard (
    bankData: BankData = createBankData(name = "Random Bank"),
    onInfo: () -> Unit = {},
    onClose: () -> Unit = {}
) {
    val depositValid = rememberSaveable { mutableStateOf(false) }
    val percentsValid = rememberSaveable { mutableStateOf(false) }
    val currencyScale = rememberSaveable { mutableIntStateOf(1) }
    val uncaledDeposit = rememberSaveable { mutableFloatStateOf(1f) }
    bankData.valid!!.value = depositValid.value && percentsValid.value && !bankData.removed!!.value
    bankData.deposit!!.value = uncaledDeposit.floatValue * currencyScale.intValue

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            BankCardControlsRow(bankData.name, onInfo, onClose);
            BankCardInputRow(
                stringResource(R.string.layout_deposit),
                depositValid, uncaledDeposit,
                stringResource(id = R.string.currency_unit),
            ) {};
            BankCardUnitScaleRow(currencyScale);
            BankCardInputRow(
                stringResource(R.string.layout_percents),
                percentsValid,
                bankData.percents!!,
                stringResource(id = R.string.percents_unit),
            ) {};
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun BankCardControlsRow(bankName: String, onInfo: () -> Unit, onClose: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) { // Controls row
        IconButton(onClick = onInfo) {
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
fun BankCardInputRow(
    name: String,
    isValid: MutableState<Boolean>,
    textOutput: MutableState<Float>,
    unit: String = "",
    onChanged: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) { // Controls row
        val text = rememberSaveable { mutableStateOf("") }
        textOutput.value = text.value.toFloatOrNull() ?: 0f;
        isValid.value = text.value.isValidFloat() && text.value.isNotEmpty()

        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it.replace(",", ".") },
            label = { Text(name) },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            isError = !text.value.isValidFloat() && text.value.isNotEmpty()
        );
        if (unit.isNotEmpty()) {
            Text(
                text = unit,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp),
                style = MaterialTheme.typography.titleLarge
            );
        }
    }
}


@Composable
fun BankCardUnitScaleRow(unitScale: MutableState<Int>) {
    val state = rememberScrollState()

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp)
            .horizontalScroll(state)
    ) {
        val (kiloSelected, setKiloSelected) = rememberSaveable { mutableStateOf(false) }
        val (megaSelected, setMegaSelected) = rememberSaveable { mutableStateOf(false) }
        val (gigaSelected, setGigaSelected) = rememberSaveable { mutableStateOf(false) }

        unitScale.value = when {
            kiloSelected -> 1000
            megaSelected -> 1000000
            gigaSelected -> 1000000000
            else -> 1
        }

        UnitScaleChip(
            selected = kiloSelected,
            onClick = {
                setKiloSelected(!kiloSelected)
                setMegaSelected(false)
                setGigaSelected(false)
            },
            label = { Text(text = stringResource(id = R.string.scale_kilo)) },
        )
        UnitScaleChip(
            selected = megaSelected,
            onClick = {
                setKiloSelected(false)
                setMegaSelected(!megaSelected)
                setGigaSelected(false)
            },
            label = { Text(text = stringResource(id = R.string.scale_mega)) }
        )
        UnitScaleChip(
            selected = gigaSelected,
            onClick = {
                setKiloSelected(false)
                setMegaSelected(false)
                setGigaSelected(!gigaSelected)
            },
            label = { Text(text = stringResource(id = R.string.scale_giga)) }
        )
    }
}


@Preview()
@Composable
fun BankCardPreview() {
    BankCard()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitScaleChip(selected: Boolean, onClick: () -> Unit, label: @Composable () -> Unit) {
    ElevatedFilterChip(
        selected = selected,
        onClick = onClick,
        label = label,
        trailingIcon = {
            if (selected) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    Modifier.size(InputChipDefaults.AvatarSize),
                )
            }
        },
    )
}