package com.example.ceep.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ceep.R
import com.example.ceep.dao.NotaDAO
import com.example.ceep.model.Nota
import com.example.ceep.ui.recycler.adapter.ListaNotasAdapter
import com.example.ceep.ui.recycler.helper.callback.NotaItemTouchHelperCallback
import com.example.ceep.ui.recycler.listener.OnItemClickListener

class ListaNotasActivity : AppCompatActivity() {

    private val todasNotas = criaLista()
    private lateinit var adapter: ListaNotasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_notas)

        setTitle("Notas")

        configRecyclerView()
        //listaNotas.adapter= ListaNotasAdapter(this, todasNotas)

        val insereNota = findViewById<TextView>(R.id.lista_notas_insere_nota)
        insereNota.setOnClickListener {
            val intent = Intent(this, FormularioNotaActivity::class.java)
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1 && resultCode == 2 && data?.hasExtra("nota") != null){
            val nota = data.getSerializableExtra("nota") as Nota
            NotaDAO().insere(nota)
            adapter.adiciona(nota)
        }else if(requestCode == 2 && resultCode == 2 && data?.hasExtra("nota") != null){
            val nota = data.getSerializableExtra("nota") as Nota
            val position = data.getIntExtra("position", -1)
            if(position>=0) {
                NotaDAO().altera(position, nota)
                adapter.altera(position, nota)
            }else {
                Toast.makeText(this, "Erro ao alterar a tarefa!",Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()
    }

    private fun configRecyclerView() {
        val listaNotas = findViewById<RecyclerView>(R.id.recyclerView)
        configAdapter(listaNotas)
        val itemTouchHelper = ItemTouchHelper(NotaItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(listaNotas)
    }

    private fun configAdapter(listaNotas: RecyclerView) {
        adapter = ListaNotasAdapter(this, todasNotas as MutableList<Nota>)
        listaNotas.adapter = adapter
        adapter.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(nota: Nota, position: Int) {
                val abreFormularioComNota =
                    Intent(applicationContext, FormularioNotaActivity::class.java)
                abreFormularioComNota.putExtra("nota", nota)
                abreFormularioComNota.putExtra("posicao", position)
                startActivityForResult(abreFormularioComNota, 2)
            }
        }
    }

    private fun criaLista(): List<Nota> {
        val notaDAO = NotaDAO()
        for (i in 1..10) {
            notaDAO.insere(Nota("Titulo ${i}", "Descrição ${i}"))
        }
        return notaDAO.todos()
    }

}