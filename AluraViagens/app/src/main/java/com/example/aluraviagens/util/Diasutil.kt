package com.example.aluraviagens.util

import com.example.aluraviagens.model.Pacote
import java.text.SimpleDateFormat
import java.util.*

fun diasEmTexto(qtdDias: Int): String {
    var diasEmTexto = ""
    if (qtdDias > 1) {
        diasEmTexto = qtdDias.toString() + " dias"
    } else {
        diasEmTexto = qtdDias.toString() + " dia"
    }
    return diasEmTexto
}

fun formatdata(pacote: Pacote): String {
    val dataIda = Calendar.getInstance()
    val dataVolta = Calendar.getInstance()
    dataVolta.add(Calendar.DATE, pacote.dias)
    val formatData = SimpleDateFormat("dd/MM/yy")
    val dataFormatadaIda = formatData.format(dataIda.time)
    val dataFormatadaVola = formatData.format(dataVolta.time)

    val dataFormatada =
        "${dataFormatadaIda} - ${dataFormatadaVola} de ${dataVolta.get(Calendar.YEAR)}"
    return dataFormatada
}