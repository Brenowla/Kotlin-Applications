package com.example.agenda

import android.app.Application
import com.example.agenda.database.AgendaDatabase

class AgendaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        criaAlunosTeste()
    }

    private fun criaAlunosTeste() {
        var database = AgendaDatabase.getInstance(this)
        val dao = database.getRoomAlunoDAO()
    }

}