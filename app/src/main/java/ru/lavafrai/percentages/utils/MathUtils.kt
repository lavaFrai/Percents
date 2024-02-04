package ru.lavafrai.percentages.utils

fun String.isValidFloat() : Boolean {
    return toFloatOrNull() != null
}