package com.example.agenda.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Aluno::class, parentColumns = arrayOf("id"), childColumns = arrayOf("alunoId"), onUpdate = ForeignKey.CASCADE, onDelete = ForeignKey.CASCADE)))
class Telefone(var numero: String ="", var tipo: TipoTelefone = TipoTelefone.FIXO, var alunoId : Int = 0) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

    //@ForeignKey(entity = Aluno::class, parentColumns = arrayOf("id"), childColumns = arrayOf("alunoId"))
}