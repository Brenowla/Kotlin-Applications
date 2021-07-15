package com.example.aluraviagens.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun converteMoeda(precoPacote: BigDecimal): String? {
    val currencyInstance = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    var moedaConvertida = currencyInstance.format(precoPacote)
    return moedaConvertida
}