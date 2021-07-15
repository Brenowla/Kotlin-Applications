package com.example.aluraviagens.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.aluraviagens.R
import com.example.aluraviagens.model.Pacote
import com.example.aluraviagens.util.converteMoeda
import com.example.aluraviagens.util.diasEmTexto
import com.example.aluraviagens.util.drawable
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

class ListaPacotesAdapter(private val context: Context,private val lista: List<Pacote>) : BaseAdapter() {

    override fun getCount(): Int {
        return lista.size
    }

    override fun getItem(position: Int): Any {
        return lista[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pacote, parent, false)
        val pacote = lista[position]

        createLocal(view, pacote)
        createImage(view, pacote)
        createDias(view, pacote)
        createPreco(view, pacote)

        return view
    }

    private fun createPreco(view: View, pacote: Pacote) {
        val textPreco = view.findViewById<TextView>(R.id.textview_preco)
        val precoPacote = pacote.preco
        var moedaConvertida = converteMoeda(precoPacote)
        textPreco.text = "${moedaConvertida}"
    }


    private fun createDias(view: View, pacote: Pacote) {
        val textDias = view.findViewById<TextView>(R.id.textview_dias)
        val qtdDias = pacote.dias
        var diasEmTexto = diasEmTexto(qtdDias)
        textDias.text = diasEmTexto
    }


    private fun createImage(view: View, pacote: Pacote) {
        val imageCidade = view.findViewById<ImageView>(R.id.imageview_foto_cidade)
        val drawableImagem = drawable(pacote,context)
        imageCidade.setImageDrawable(drawableImagem)
    }


    private fun createLocal(view: View, pacote: Pacote) {
        val textLocal = view.findViewById<TextView>(R.id.textview_cidade)
        textLocal.text = pacote.local
    }


}
