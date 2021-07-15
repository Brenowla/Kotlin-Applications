package com.example.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracters: Int): String{
    if(this.length > caracters){
        return "${this.substring(0, caracters)}..."
    }else{
        return this
    }
}

fun String.convertToCalendar(): Calendar {
    val formatBr = SimpleDateFormat("dd/MM/yyyy")
    val data = Calendar.getInstance()
    data.time = formatBr.parse(this)
    return data
}