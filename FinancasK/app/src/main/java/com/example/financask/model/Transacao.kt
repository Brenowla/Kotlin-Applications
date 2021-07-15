package com.example.financask.model

import java.util.Calendar

class Transacao(
    val valor: Double,
    val categoria: String = "Indefinida",
    val tipo: Tipo,
    val data: Calendar = Calendar.getInstance()
) {

}