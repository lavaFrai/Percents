package pro.maximon.percentages.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import ru.lavafrai.percentages.R
import java.text.DecimalFormat


@Composable
fun Float.formatToSIString() : String {
    if (this / 1E3 < 1) { return DecimalFormat("#.##").format(this) };
    if (this / 1E6 < 1) { return DecimalFormat("#.##").format(this / 1E3) + " " + stringResource(id = R.string.kilo) };
    if (this / 1E9 < 1) { return DecimalFormat("#.##").format(this / 1E6) + " " + stringResource(id = R.string.mega) };
    if (this / 1E12 < 1) { return DecimalFormat("#.##").format(this / 1E9) + " " + stringResource(id = R.string.giga) };
    return stringResource(id = R.string.tomuch);
}