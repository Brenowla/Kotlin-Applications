package com.example.agenda.database.converter

import androidx.room.TypeConverter
import com.example.agenda.model.TipoTelefone
import java.util.*

class Converters {

    @TypeConverter
    fun paraString(tipo: TipoTelefone): String{
        return tipo.name
    }

    @TypeConverter
    fun paraTipoTelefone(valor: String?): TipoTelefone {
        if(valor != null){
            return TipoTelefone.valueOf(valor)
        }
        return TipoTelefone.FIXO
    }

    @TypeConverter
    fun toLong(calendar: Calendar?) : Long? {
        if (calendar != null){
            return calendar.timeInMillis
        }
        return null
    }

    @TypeConverter
    fun toCalendar(valor: Long?): Calendar {
        val instance = Calendar.getInstance()
        if(valor != null){
            instance.timeInMillis = valor
        }
        return instance
    }
}