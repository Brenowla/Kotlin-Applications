package com.example.ceep.ui.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ceep.R
import com.example.ceep.model.Nota
import com.example.ceep.ui.recycler.listener.OnItemClickListener
import java.util.*

class ListaNotasAdapter(val context: Context, private val notas: MutableList<Nota>) : RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val newView = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(newView)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.bindField(nota)
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    fun adiciona(nota:Nota){
        notas.add(nota)
        notifyDataSetChanged()
    }

    fun altera(position: Int, nota: Nota) {
        notas.set(position,nota)
        notifyItemChanged(position)
    }

    fun remove(position: Int){
        notas.removeAt(position)
        notifyItemRemoved(position)
    }

    fun troca(adapterPosition: Int, adapterPosition1: Int) {
        Collections.swap(notas, adapterPosition, adapterPosition1)
        notifyItemMoved(adapterPosition, adapterPosition1)
    }

    inner class NotaViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {

        private lateinit var nota: Nota

        val titulo: TextView = itemView.findViewById<TextView>(R.id.item_nota_titulo)
        val descricao: TextView = itemView.findViewById<TextView>(R.id.item_nota_descricao)

        init {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(nota, adapterPosition)
            }
        }

        fun bindField(nota: Nota){
            this.nota = nota
            titulo.text = nota.titulo
            descricao.text = nota.descricao
        }
    }

}
