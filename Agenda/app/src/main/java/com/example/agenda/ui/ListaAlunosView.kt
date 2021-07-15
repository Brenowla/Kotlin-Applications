package com.example.agenda.ui

import android.app.AlertDialog
import android.content.Context
import com.example.agenda.database.AgendaDatabase
import com.example.agenda.database.DAO.RoomAlunoDAO
import com.example.agenda.model.Aluno

class ListaAlunosView(private val context: Context) {

    private lateinit var dao: RoomAlunoDAO
    var adapter = ListaAlunosAdapter(context)

    init {
        var database = AgendaDatabase.getInstance(context)
        dao = database.getRoomAlunoDAO()
    }

    fun confirmaRemocao(aluno: Aluno) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Removendo Aluno")
            .setMessage("Tem certeza que deseja remover o aluno!")
            .setPositiveButton("Sim"
            ) { dialog, which -> removeAluno(aluno) }
            .setNegativeButton("Não", null)
        dialog.show()
    }

    private fun removeAluno(alunoSelecionado: Aluno) {
        dao.remove(alunoSelecionado)
        adapter.remove(alunoSelecionado)
    }

    fun atualizaLista() {
        adapter.atualiza(dao.todos() as ArrayList<Aluno>)
    }

}