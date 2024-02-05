package ru.lavafrai.percentages.utils

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.toMutableStateList
import ru.lavafrai.percentages.model.BankData

fun bankDataSaver(): Saver<MutableList<BankData>, List<String>> = Saver(
        save = { list ->
            list.map { it.name }
        },
        restore = { list ->
            list.map { BankData(it) }.toMutableStateList()
        }
    )
