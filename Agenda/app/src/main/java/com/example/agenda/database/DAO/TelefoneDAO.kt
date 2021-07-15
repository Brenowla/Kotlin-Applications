package com.example.agenda.database.DAO

import androidx.room.*
import com.example.agenda.model.Telefone

@Dao
interface TelefoneDAO {

    @Query("SELECT t.* FROM Telefone t JOIN Aluno a ON t.alunoId = a.id WHERE t.alunoId = :alunoId LIMIT 1")
    fun buscaPrimeiroTelefoneAluno(alunoId: Int):Telefone

    @Insert
    fun salvar(vararg telefones: Telefone)

    @Query("SELECT t.* FROM Telefone t JOIN Aluno a ON t.alunoId = a.id WHERE t.alunoId = :id")
    fun buscaTelefonesDoAluno(id: Int): List<Telefone>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun atualiza(telefones: List<Telefone>)
}