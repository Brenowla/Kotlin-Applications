package com.example.aluraviagens.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.aluraviagens.R
import com.example.aluraviagens.model.Pacote
import com.example.aluraviagens.util.converteMoeda
import com.example.aluraviagens.util.diasEmTexto
import com.example.aluraviagens.util.drawable
import com.example.aluraviagens.util.formatdata
import java.util.*

class ResumoPacoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo_pacote)

        setTitle("Resumo do Pacote")

        if(intent.hasExtra("pacote")){
            val pacote = intent.getSerializableExtra("pacote") as Pacote
            setFields(pacote)
            setBotao(pacote)
        }

    }

    private fun setBotao(pacote: Pacote) {
        val botao = findViewById<Button>(R.id.resumo_button_realizar_pagamento)
        botao.setOnClickListener {
            val intent = Intent(this, PagamentoActivity::class.java)
            intent.putExtra("pacote", pacote)
            startActivity(intent)
        }
    }

    private fun setFields(pacote: Pacote) {
        val textCidade = findViewById<TextView>(R.id.resumo_textview_cidade)
        val textPreco = findViewById<TextView>(R.id.resumo_textview_preco)
        val textDias = findViewById<TextView>(R.id.resumo_textview_dias)
        val textData = findViewById<TextView>(R.id.resumo_textview_data)
        val imageCidade = findViewById<ImageView>(R.id.resumo_imageview_cidade)
        val drawableDoPAcote = drawable(pacote, this)

        imageCidade.setImageDrawable(drawableDoPAcote)

        setPriceAndCidade(textCidade, pacote, textPreco, textDias)

        setData(pacote, textData)
    }

    private fun setData(
        pacote: Pacote,
        textData: TextView
    ) {
        val dataFormatada = formatdata(pacote)
        textData.text = dataFormatada

    }

    private fun setPriceAndCidade(
        textCidade: TextView,
        pacote: Pacote,
        textPreco: TextView,
        textDias: TextView
    ) {
        textCidade.text = pacote.local
        textPreco.text = converteMoeda(pacote.preco)
        textDias.text = diasEmTexto(pacote.dias)
    }
}