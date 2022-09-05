package com.navi.lakshayclosed.util

import java.text.SimpleDateFormat
import java.util.*

fun String.fromServerFormatToUiFormat(): String {
    return try {
        val sourceFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val date = sourceFormatter.parse(this)
        val destinationFormatter = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
        destinationFormatter.format(date!!)
    } catch (e: Exception) {
        this
    }
}