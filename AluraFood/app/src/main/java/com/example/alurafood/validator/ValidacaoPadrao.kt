package com.example.alurafood.validator

import android.text.Editable
import com.google.android.material.textfield.TextInputLayout

class ValidacaoPadrao(val text: Editable,val textinput: TextInputLayout) {

    fun estaValido(): Boolean{
        if(!validaCampoObrigatorio()) return false
        return true
    }

    private fun validaCampoObrigatorio(): Boolean {
        if (text?.isEmpty() == true) {
            textinput.error = "Campo obrigatório!"
            return false
        }
        return true
    }

}