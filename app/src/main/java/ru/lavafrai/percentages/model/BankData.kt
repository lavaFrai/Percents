package ru.lavafrai.percentages.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


data class BankData(
    val name : String,
    var deposit : MutableState<Float>? = null,
    var percents : MutableState<Float>? = null,
    var valid : MutableState<Boolean>? = null,
    var removed : MutableState<Boolean>? = null
)


@Composable
fun BankData.initialize(): BankData {
    deposit = remember { mutableFloatStateOf(0f) }
    percents = remember { mutableFloatStateOf(0f) }
    valid = remember { mutableStateOf(false) }
    removed = remember { mutableStateOf(false) }
    return this;
}


@Composable
fun createBankData(name: String): BankData {
    val bankData = BankData(name)
    bankData.initialize()
    return bankData
}