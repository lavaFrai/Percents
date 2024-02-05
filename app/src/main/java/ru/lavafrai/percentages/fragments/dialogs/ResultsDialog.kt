package ru.lavafrai.percentages.fragments.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.maximon.percentages.utils.formatToSIString
import pro.maximon.percentages.utils.nonScaledSp
import ru.lavafrai.percentages.R


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ResultsDialog(
    resultsShowed: SheetState = SheetState(true),
    resultDeposit: Float = 0f,
    resultProfit: Float = 0f,
    resultPercents: Float = 0f,
    onClose: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = resultsShowed
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(8.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = stringResource(id = R.string.calculations_results),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineSmall
            )
            
            ResultsDialogLine(name = stringResource(id = R.string.full_investment), value = resultDeposit.formatToSIString(), unit = stringResource(id = R.string.currency_unit))
            Spacer(modifier = Modifier.height(8.dp))
            ResultsDialogLine(name = stringResource(id = R.string.monthly_percents), value = (resultPercents / 12).formatToSIString(), unit = stringResource(id = R.string.percents_unit))
            ResultsDialogLine(name = stringResource(id = R.string.annual_percents), value = resultPercents.formatToSIString(), unit = stringResource(id = R.string.percents_unit))
            ResultsDialogLine(name = stringResource(id = R.string.monthly_profit), value = (resultProfit / 12).formatToSIString(), unit = stringResource(id = R.string.currency_unit))
            ResultsDialogLine(name = stringResource(id = R.string.annual_profit), value = resultProfit.formatToSIString(), unit = stringResource(id = R.string.currency_unit))
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colorScheme.onPrimaryContainer, thickness = 1.dp)
            ResultsDialogLine(name = stringResource(id = R.string.full_revenue), value = (resultDeposit + resultProfit).formatToSIString(), unit = stringResource(id = R.string.currency_unit))

            Button(onClick = onClose, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)) {
                Text(text = stringResource(id = R.string.close))
            }
        }
    }
}


@Composable
fun ResultsDialogLine(name: String, value: String, unit: String = "") {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        Text(text = name, fontSize = 20.nonScaledSp)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .align(Alignment.Bottom)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .align(Alignment.BottomCenter)
                    .padding(5.dp, 2.dp)
                    .background(
                        MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)
                    )
            )
        }
        Row {
            Text(text = value, fontSize = 20.nonScaledSp)
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = unit, fontSize = 20.nonScaledSp)
        }
    }
}
