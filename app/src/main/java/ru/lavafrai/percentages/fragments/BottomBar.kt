package ru.lavafrai.percentages.fragments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.lavafrai.percentages.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BottomBar(onAddBank : () -> Unit = {}, onCalculate : () -> Unit = {}) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(8.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize()
        ) {

            Button(
                onClick = onAddBank,
                modifier = Modifier
                    .padding(8.dp, 0.dp)
                    .fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text(text = stringResource(id = R.string.add_bank))
            }

            Button(
                onClick = onCalculate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_calculate), contentDescription = null)
                Text(text = stringResource(id = R.string.button_calculate))
            }
        }
    }
}