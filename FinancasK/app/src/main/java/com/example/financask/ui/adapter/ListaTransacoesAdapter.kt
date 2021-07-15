package com.example.financask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.financask.R
import com.example.financask.extension.formataParaBR
import com.example.financask.extension.limitaEmAte
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*


class ListaTransacoesAdapter(
    private val transacoes: List<Transacao>,
    private val context: Context
) : BaseAdapter() {

    private val LIMITE_CARACTERS = 14

    override fun getCount(): Int {
        return transacoes.size
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val newView = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        colorFromType(position, newView)
        setTextsForView(newView, position)

        return newView
    }

    private fun setTextsForView(newView: View, position: Int) {
        newView.transacao_valor.text = "R$ ${transacoes[position].valor}"
        newView.transacao_categoria.text =
            transacoes[position].categoria.limitaEmAte(LIMITE_CARACTERS)
        newView.transacao_data.text = transacoes[position].data.formataParaBR()
    }

    private fun colorFromType(position: Int, newView: View) {
        if (transacoes[position].tipo == Tipo.RECEITA) {
            newView.transacao_valor.setTextColor(ContextCompat.getColor(context, R.color.receita))
            newView.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_receita)
        } else {
            newView.transacao_valor.setTextColor(ContextCompat.getColor(context, R.color.despesa))
            newView.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_despesa)
        }
    }

}