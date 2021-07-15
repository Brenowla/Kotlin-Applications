package com.example.aluraviagens.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.aluraviagens.R
import com.example.aluraviagens.model.Pacote
import com.example.aluraviagens.util.converteMoeda
import com.example.aluraviagens.util.drawable
import com.example.aluraviagens.util.formatdata
import java.math.BigDecimal

class ResumoCompraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo_compra)

        setTitle("Resumo da compra")

        if(intent.hasExtra("pacote")) {
            val pacote = intent.getSerializableExtra("pacote") as Pacote
            setFields(pacote)
        }
    }

    private fun setFields(pacote: Pacote) {
        val textCidade = findViewById<TextView>(R.id.resumocompra_textview_cidade)
        val textData = findViewById<TextView>(R.id.resumocompra_textview_data)
        val textPreco = findViewById<TextView>(R.id.resumocompra_textview_preco)

        setImage(pacote)

        textData.text = formatdata(pacote)

        textPreco.text = converteMoeda(pacote.preco)

        textCidade.text = pacote.local
    }

    private fun setImage(pacote: Pacote) {
        val imageCidade = findViewById<ImageView>(R.id.resumocompra_imageview_cidade)
        val drawable = drawable(pacote, this)
        imageCidade.setImageDrawable(drawable)
    }
}