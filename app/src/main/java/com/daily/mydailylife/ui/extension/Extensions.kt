package com.daily.mydailylife.ui.extension

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import com.daily.mydailylife.data.model.FormattedDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Activity.transparentStatusBar() {
    window.clearFlags(
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
    )
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = Color.TRANSPARENT
}

fun getFormattedDateToday() : FormattedDate {
    val sdf = SimpleDateFormat("MMMM/yyyy/dd/EEEE", Locale.getDefault())
    val date = sdf.format(Calendar.getInstance().time)
    val dates = date.split("/")

    return FormattedDate(
        dates[0],
        dates[1],
        dates[2],
        dates[3]
    )
}

fun getDateToday(): String {
    val sdf = SimpleDateFormat("MMMM/yyyy/dd/EEEE", Locale.getDefault())

    return sdf.format(Calendar.getInstance().time)
}

fun parseDate(date: String) : FormattedDate {
    val dates = date.split("/")

    return FormattedDate(
        month = dates[0],
        year = dates[1],
        dayNum = dates[2],
        dayName = dates[3]
    )
}