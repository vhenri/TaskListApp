package com.vhenri.android.tasklistapp.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

const val DATE_FORMAT_MMM_DD_YYYY = "MMM dd yyyy"
fun getFormattedDate(year: Int, month: Int, day: Int): String {
    val dateTimeFormatter =
            DateTimeFormatter.ofPattern(DATE_FORMAT_MMM_DD_YYYY, Locale.getDefault())
    return dateTimeFormatter.format(LocalDate.of(year, month, day))
}