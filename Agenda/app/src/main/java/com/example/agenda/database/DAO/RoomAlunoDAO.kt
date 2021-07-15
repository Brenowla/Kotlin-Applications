package com.example.agenda.database.DAO

import androidx.room.*
import com.example.agenda.model.Aluno

@Dao
interface RoomAlunoDAO {

    @Insert
    fun salva(aluno: Aluno): Long

    @Query("SELECT * FROM aluno")
    fun todos(): List<Aluno>

    @Delete
    fun remove(aluno: Aluno)

    @Update()
    fun edit(aluno: Aluno)

}