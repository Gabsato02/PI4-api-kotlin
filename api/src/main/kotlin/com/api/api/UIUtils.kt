package com.api.api

import java.text.SimpleDateFormat
import java.util.*

fun formatDateToTimestamp(date: Date): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return dateFormat.format(date)
}