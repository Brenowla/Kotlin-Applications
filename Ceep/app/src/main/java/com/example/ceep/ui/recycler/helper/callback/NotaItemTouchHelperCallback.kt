package com.example.ceep.ui.recycler.helper.callback

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ceep.dao.NotaDAO
import com.example.ceep.ui.recycler.adapter.ListaNotasAdapter

class NotaItemTouchHelperCallback(private val adapter: ListaNotasAdapter) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        var marcacoesDeslize = ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        var marcacoesDrag = ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        return makeMovementFlags(marcacoesDrag, marcacoesDeslize)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        NotaDAO().troca(viewHolder.adapterPosition, target.adapterPosition)
        adapter.troca(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        NotaDAO().remove(viewHolder.adapterPosition)
        adapter.remove(viewHolder.adapterPosition)
    }

}
