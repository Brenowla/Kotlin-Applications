package com.example.aluraviagens.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.example.aluraviagens.R
import com.example.aluraviagens.dao.PacoteDAO
import com.example.aluraviagens.model.Pacote
import com.example.aluraviagens.ui.adapter.ListaPacotesAdapter
import java.math.BigDecimal

class ListaPacotesActivity : AppCompatActivity() {

    private val TITULO_APP_BAR = "Pacotes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pacotes)

        setTitle(TITULO_APP_BAR)

        configListaPacotes()

    }

    private fun configListaPacotes() {
        val listaDePacotes = findViewById<ListView>(R.id.listview_pacotes)
        val lista = PacoteDAO().lista()
        listaDePacotes.adapter = ListaPacotesAdapter(this, lista)
        listaDePacotes.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ResumoPacoteActivity::class.java)
            intent.putExtra("pacote", lista[position])
            startActivity(intent)
        }
    }
}