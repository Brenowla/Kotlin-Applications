package com.example.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaBR(): String {
    val format = "dd/MM/yyyy"
    val simpleDateFormat = SimpleDateFormat(format)
    return simpleDateFormat.format(this.time)
}