package ru.lavafrai.percentages

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lavafrai.percentages.fragments.BankCard
import ru.lavafrai.percentages.fragments.BottomBar
import ru.lavafrai.percentages.model.BankData
import ru.lavafrai.percentages.model.sampleBanks
import ru.lavafrai.percentages.ui.theme.PercentagesTheme
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pro.maximon.percentages.BankCalculator
import ru.lavafrai.percentages.fragments.dialogs.InfoDialog
import ru.lavafrai.percentages.fragments.dialogs.InvalidDataDialog
import ru.lavafrai.percentages.fragments.dialogs.ResultsDialog
import ru.lavafrai.percentages.model.initialize
import ru.lavafrai.percentages.utils.banksProcessor
import kotlin.concurrent.thread


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ActivityMainView()
        }
    }
}


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ActivityMainView () {
    val banks: MutableList<BankData> = remember { mutableStateListOf<BankData>().apply { addAll(sampleBanks) }}
    val (invalidDataDialogShowed, setInvalidDataDialogShowed) = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val resultsState = rememberModalBottomSheetState()
    val (resultsShowed, setResultsShowed) = remember { mutableStateOf(true) }
    val resultDeposit = remember { mutableFloatStateOf(1000f) }
    val resultProfit = remember { mutableFloatStateOf(100f) }
    val resultPercents = remember { mutableFloatStateOf(10f) }

    for (bank in banks) { bank.initialize() }

    PercentagesTheme {
        when { invalidDataDialogShowed -> InvalidDataDialog {setInvalidDataDialogShowed(false)} }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column {
                Box (Modifier.height(LocalConfiguration.current.screenHeightDp.dp - 128.dp)) {
                    BanksList(banks)
                }
                BottomBar(onAddBank = {
                        banks.add(BankData(
                            context.getString(R.string.offer),
                            mutableStateOf(0f),
                            mutableStateOf(0f),
                            mutableStateOf(false),
                            mutableStateOf(false),
                        ))
                },
                    onCalculate = {
                        val valid = banks.all { it.valid!!.value || it.removed!!.value }

                        /*
                        if (valid) Toast.makeText(context, context.getString(R.string.calculating), Toast.LENGTH_SHORT).show()
                        else {
                            setInvalidDataDialogShowed(true);
                            return@BottomBar;
                        }
                         */

                        if (!valid) {
                            setInvalidDataDialogShowed(true);
                            return@BottomBar;
                        }

                        val actualBanks = banks.filter { it.valid!!.value };

                        val (fullProfit, fullPercent, fullDeposit) = banksProcessor(actualBanks)

                        resultProfit.floatValue = fullProfit
                        resultPercents.floatValue = fullPercent
                        resultDeposit.floatValue = fullDeposit

                        setResultsShowed(true)
                        scope.launch { resultsState.expand() }
                        /*
                        Toast.makeText(context, "${context.getString(R.string.out_profit)} ${fullProfit}, " +
                                "${context.getString(R.string.out_percent)} $fullPercent and " +
                                "${context.getString(R.string.out_full_deposit)} $fullDeposit",
                            Toast.LENGTH_LONG).show();
                         */
                })
            }
        }

        if (resultsShowed) {
            ResultsDialog(
                resultsState,
                resultDeposit.floatValue,
                resultProfit.floatValue,
                resultPercents.floatValue
            ) {
                scope.launch { resultsState.hide() }
                    .invokeOnCompletion { setResultsShowed(false) }
            }
        }
    }
}


@ExperimentalAnimationApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BanksList(banks : MutableList<BankData>) {
    val (infoDialogShowed, setInfoDialogShowed) = remember { mutableStateOf(false) }
    when { infoDialogShowed -> InfoDialog { setInfoDialogShowed(false) } }

    val context = LocalContext.current
    LazyColumn (
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxSize(),
    ) {
        items(banks) {  bank ->
            Column (modifier = if (bank.removed!!.value) Modifier
                .height(0.dp)
                .animateItemPlacement() else Modifier.animateItemPlacement()) {
                Spacer(modifier = Modifier.height(8.dp))
                BankCard(
                    bank,
                    onInfo = { setInfoDialogShowed(true) },
                    onClose = { bank.removed!!.value = true }
                )
            }
        }
    }
}