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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ru.lavafrai.percentages.fragments.InvalidDataDialog
import ru.lavafrai.percentages.model.initialize
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ActivityMainView()
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun ActivityMainView () {
    val banks: MutableList<BankData> = remember { mutableStateListOf<BankData>().apply { addAll(sampleBanks) }}
    val (invalidDataDialogShowed, setInvalidDataDialogShowed) = remember { mutableStateOf(false) }
    val (infoDialogShowed, setInfoDialogShowed) = remember { mutableStateOf(false) }
    val context = LocalContext.current


    for (bank in banks) { bank.initialize() }

    PercentagesTheme {
        when { invalidDataDialogShowed -> InvalidDataDialog {setInvalidDataDialogShowed(false)} }
        when { infoDialogShowed -> InvalidDataDialog {setInfoDialogShowed(false)} }

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
                            "Aboba",
                            mutableStateOf(0f),
                            mutableStateOf(0f),
                            mutableStateOf(false),
                            mutableStateOf(false),
                        ))
                },
                    onCalculate = {
                        val valid = banks.none { !it.removed!!.value && !it.valid!!.value }

                        if (valid) Toast.makeText(context, "Calculating...", Toast.LENGTH_SHORT).show()
                        else setInvalidDataDialogShowed(true)

                })
            }
        }
    }
}


@ExperimentalAnimationApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BanksList(banks : MutableList<BankData>) {
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
                BankCard(bank) { bank.removed!!.value = true }
            }
        }
    }
}