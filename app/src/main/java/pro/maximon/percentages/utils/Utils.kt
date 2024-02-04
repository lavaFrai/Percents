package pro.maximon.percentages.utils

import android.content.Context
import ru.lavafrai.percentages.R
import java.text.DecimalFormat

fun Float.formatTOSiString(context: Context) : String {
    if (this / 1E3 < 1) { return DecimalFormat("#.##").format(this) };
    if (this / 1E6 < 1) { return DecimalFormat("#.##").format(this / 1E3) + context.getString(R.string.kilo) };
    if (this / 1E9 < 1) { return DecimalFormat("#.##").format(this / 1E6) + context.getString(R.string.mega) };
    if (this / 1E12 < 1) { return DecimalFormat("#.##").format(this / 1E9) + context.getString(R.string.giga) };
    return context.getString(R.string.tomuch);
}