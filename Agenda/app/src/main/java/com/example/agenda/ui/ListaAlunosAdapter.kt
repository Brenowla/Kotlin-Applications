package com.example.agenda.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.agenda.R
import com.example.agenda.database.AgendaDatabase
import com.example.agenda.model.Aluno

class ListaAlunosAdapter(private val context: Context) : BaseAdapter() {

    private val alunos: ArrayList<Aluno> = arrayListOf()
    private val telefoneDao = AgendaDatabase.getInstance(context).getTelefoneDAO()

    override fun getCount(): Int {
        return alunos.size
    }

    override fun getItem(position: Int): Aluno {
        return alunos.get(position)
    }

    override fun getItemId(position: Int): Long {
        return alunos.get(position).getId().toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val createdView = criaView(parent)
        vincula(createdView, position)
        return createdView
    }

    private fun vincula(createdView: View, position: Int) {
        createdView.findViewById<TextView>(R.id.item_aluno_nome).text = alunos.get(position).getNomeCompleto()
        val primeiroTelefone = telefoneDao.buscaPrimeiroTelefoneAluno(alunos.get(position).getId())
        createdView.findViewById<TextView>(R.id.item_aluno_telefone).text = primeiroTelefone.numero
    }

    private fun criaView(parent: ViewGroup?) = LayoutInflater.from(context)
        .inflate(R.layout.item_aluno, parent, false)

    private fun clear() {
        alunos.clear()
    }

    private fun addAll(alunos: ArrayList<Aluno>){
        this.alunos.addAll(alunos)
    }

    fun remove(aluno: Aluno) {
        alunos.remove(aluno)
        notifyDataSetChanged()
    }

    fun atualiza(alunos: ArrayList<Aluno>){
        clear()
        addAll(alunos)
        notifyDataSetChanged()
    }

}