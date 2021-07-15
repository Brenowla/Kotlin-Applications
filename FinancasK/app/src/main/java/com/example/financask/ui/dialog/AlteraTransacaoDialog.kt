package com.example.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.financask.R
import com.example.financask.extension.convertToCalendar
import com.example.financask.extension.formataParaBR
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.util.*

class AlteraTransacaoDialog(private val context: Context, private val viewGroup: ViewGroup) {

    private val viewAddTransacao = criaLayoult()

    fun configuraDialog(transacao: Transacao,delegate: (transacao: Transacao) -> Unit) {

        val tipo = transacao.tipo
        viewAddTransacao.form_transacao_valor.setText(transacao.valor.toString())


        configuraCampoData(transacao)

        configuraCampoCategoria(tipo,transacao)

        configuraFormulario(tipo,delegate)
    }

    private fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        val titulo = "Alterar Transação"
        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewAddTransacao)
            .setPositiveButton("Salvar") { _, _ ->
                val valorEmTexto = viewAddTransacao.form_transacao_valor.text.toString()
                val dataEmTexto = viewAddTransacao.form_transacao_data.text.toString()
                val categoriaEmText =
                    viewAddTransacao.form_transacao_categoria.selectedItem.toString()

                val transacao = Transacao(
                    tipo = tipo,
                    valor = valorEmTexto.toDouble(),
                    categoria = categoriaEmText,
                    data = dataEmTexto.convertToCalendar()
                )
                delegate(transacao)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


    private fun configuraCampoCategoria(tipo: Tipo, transacao: Transacao) {
        val categoriasRetornadas = categoriasPor(tipo)
        val posicaoCategoria = context.resources.getStringArray(categoriasPor(tipo)).indexOf(transacao.categoria)
        val adapter = ArrayAdapter.createFromResource(
            context,
            categoriasRetornadas,
            android.R.layout.simple_spinner_dropdown_item
        )
        viewAddTransacao.form_transacao_categoria.adapter = adapter
        viewAddTransacao.form_transacao_categoria.setSelection(posicaoCategoria,true)
    }

    private fun categoriasPor(tipo: Tipo) = if (tipo == Tipo.RECEITA) {
        R.array.categorias_de_receita
    } else {
        R.array.categorias_de_despesa
    }

    private fun configuraCampoData(transacao: Transacao) {
        val dataSelecionada = transacao.data.formataParaBR()
        viewAddTransacao.form_transacao_data.setText(dataSelecionada)
        val ano = dataSelecionada.get(Calendar.YEAR).toInt()
        val mes = dataSelecionada.get(Calendar.MONTH).toInt()
        val dia = dataSelecionada.get(Calendar.DAY_OF_MONTH).toInt()
        viewAddTransacao.form_transacao_data.setOnClickListener {
            DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(year, month, dayOfMonth)
                    viewAddTransacao.form_transacao_data.setText(dataSelecionada.formataParaBR())
                },
                ano, mes, dia
            )
                .show()
        }
    }

    private fun criaLayoult(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.form_transacao, viewGroup, false)
    }

}