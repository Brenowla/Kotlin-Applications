package com.example.financask.ui

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.financask.R
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*

class ResumoView(private val view: View, private val transacoes: List<Transacao>, private val context: Context) {

    fun configResumo() {
        var totalReceita = 0.0
        var totalDespesa = 0.0

        totalReceita = somaPorTipo(Tipo.RECEITA)

        totalDespesa = somaPorTipo(Tipo.DESPESA)

        view.resumo_card_receita.setTextColor(ContextCompat.getColor(context,R.color.receita))
        view.resumo_card_receita.text = "R$ ${totalReceita.toString()}"
        view.resumo_card_despesa.setTextColor(ContextCompat.getColor(context,R.color.despesa))
        view.resumo_card_despesa.text = "R$ ${totalDespesa.toString()}"

        val total = totalReceita - totalDespesa
        if(total >= 0){
            view.resumo_card_total.setTextColor(ContextCompat.getColor(context,R.color.receita))
        }else {
            view.resumo_card_total.setTextColor(ContextCompat.getColor(context,R.color.despesa))
        }
        view.resumo_card_total.text = "R$ ${total.toString()}"

    }

    private fun somaPorTipo(tipo: Tipo) = transacoes
        .filter { transacao -> transacao.tipo == tipo }
        .sumByDouble { transacao -> transacao.valor }

}