package com.example.agenda.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

@Entity
class Aluno(var nome: String,var email: String):Serializable {

    var momentoDeCadastro = Calendar.getInstance()

    @PrimaryKey(autoGenerate = true)
    private var id: Int = 0

    fun setId(id: Int){
        this.id = id
    }

    fun getId(): Int{
        return id
    }

    fun getNomeCompleto(): String{
        return "$nome - ${dataFormatada()}"
    }

    override fun toString(): String {
        return "$nome -}"
    }

    fun dataFormatada(): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return simpleDateFormat.format(momentoDeCadastro.time)
    }
}
