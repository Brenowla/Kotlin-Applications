package com.example.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ceep.R
import com.example.ceep.model.Nota

class FormularioNotaActivity : AppCompatActivity() {

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_nota)

        setTitle("Inserir Nota")

        if(intent.hasExtra("nota") && intent.hasExtra("posicao")){
            setTitle("Alterar Nota")
            val notaRecebida = intent.getSerializableExtra("nota") as Nota
            position = intent.getIntExtra("posicao", -1)
            val titulo = findViewById<TextView>(R.id.formulario_nota_titulo)
            val descricao = findViewById<TextView>(R.id.formulario_nota_descricao)
            titulo.text = notaRecebida.titulo
            descricao.text = notaRecebida.descricao
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_formulario_nota_salva, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_formulario_nota_ic_salva){
            val titulo = findViewById<EditText>(R.id.formulario_nota_titulo)
            val descricao = findViewById<EditText>(R.id.formulario_nota_descricao)
            val nota = Nota(titulo.text.toString(), descricao.text.toString())
            val resultadoInsercao = Intent()
            resultadoInsercao.putExtra("nota", nota)
            resultadoInsercao.putExtra("posicao", position)
            setResult(2,resultadoInsercao)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}