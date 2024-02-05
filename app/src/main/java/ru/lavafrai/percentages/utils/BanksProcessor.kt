package ru.lavafrai.percentages.utils

import android.content.Context
import pro.maximon.percentages.BankCalculator
import pro.maximon.percentages.utils.formatToSIString
import ru.lavafrai.percentages.model.BankData

fun banksProcessor(actualBanks: List<BankData>): Triple<Float, Float, Float> {
    val bankCalculator = BankCalculator();
    actualBanks.forEach {
        bankCalculator.calculateAnnualProfit(
            it.deposit!!.value.toDouble(),
            it.percents!!.value.toDouble()
        );
    }
    val profit : Float = bankCalculator.fullProfit.toFloat()
    val percent : Float = bankCalculator.percent.toFloat()
    val fullDeposit : Float = bankCalculator.fullDeposit.toFloat()

    return Triple(profit, percent, fullDeposit)
}