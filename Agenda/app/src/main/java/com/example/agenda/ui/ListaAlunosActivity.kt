package com.example.agenda.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.agenda.R
import com.example.agenda.model.Aluno
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaAlunosActivity : AppCompatActivity() {

    private lateinit var lista: ListView

    private lateinit var listaAlunosView: ListaAlunosView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)

        listaAlunosView = ListaAlunosView(this)
        title = "Lista de Alunos"
        configuraLista()

        configuraListnerClickPorItem()

        setClickFloatingButton()

    }

    private fun configuraLista() {
        lista = findViewById(R.id.activity_lista_alunos_lista)
        lista.adapter = listaAlunosView.adapter
        listaAlunosView.atualizaLista()
        registerForContextMenu(lista)
    }



    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
       menuInflater.inflate(
           R.menu.activity_lista_alunos, menu
       )
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val title = item.title
        if(title.equals("Remover")) {
            val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val aluno = listaAlunosView.adapter.getItem(menuInfo.position)
            if (aluno != null) {
                listaAlunosView.confirmaRemocao(aluno)
            }
        }
        return super.onContextItemSelected(item)
    }


//    private fun configuraListenerClickLongoPorItem() {
//        lista.setOnItemLongClickListener(object : AdapterView.OnItemLongClickListener {
//            override fun onItemLongClick(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ): Boolean {
//                val alunoSelecionado = parent!!.getItemAtPosition(position) as Aluno
//                removeAluno(alunoSelecionado)
//                return false
//            }
//
//        })
//    }


    private fun setClickFloatingButton() {
        val addButton = findViewById<FloatingActionButton>(R.id.floatingActionButton1)

        addButton.setOnClickListener {
            startActivity(Intent(this, FormularioAlunoActivity::class.java))
        }
    }

    private fun configuraListnerClickPorItem() {
        lista.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val alunoSelecionado = parent!!.getItemAtPosition(position) as Aluno
                val formulario = Intent(applicationContext, FormularioAlunoActivity::class.java)
                formulario.putExtra("aluno", alunoSelecionado)
                startActivity(formulario)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        listaAlunosView.atualizaLista()
    }


}