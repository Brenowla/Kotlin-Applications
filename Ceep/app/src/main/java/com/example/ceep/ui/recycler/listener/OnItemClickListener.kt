package com.example.ceep.ui.recycler.listener

import com.example.ceep.model.Nota

interface OnItemClickListener {

    fun onItemClick(nota: Nota, position: Int)
}