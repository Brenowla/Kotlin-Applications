package com.example.aluraviagens.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.aluraviagens.R
import com.example.aluraviagens.model.Pacote
import com.example.aluraviagens.util.converteMoeda
import java.math.BigDecimal

class PagamentoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagamento)

        setTitle("Pagamento")

        if(intent.hasExtra("pacote")) {
            val pacote = intent.getSerializableExtra("pacote") as Pacote
            setPreco(pacote)
            setBotao(pacote)
        }

    }

    private fun setBotao(pacote: Pacote) {
        val botao = findViewById<Button>(R.id.pagamento_button_finalizar)
        botao.setOnClickListener {
            val intent = Intent(this, ResumoCompraActivity::class.java)
            intent.putExtra("pacote", pacote)
            startActivity(intent)
        }
    }

    private fun setPreco(pacote: Pacote) {
        val textPreco = findViewById<TextView>(R.id.pagamento_textview_preco)
        val moedaBR = converteMoeda(pacote.preco)
        textPreco.text = moedaBR
    }
}