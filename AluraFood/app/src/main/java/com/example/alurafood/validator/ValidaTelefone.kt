package com.example.alurafood.validator

import android.text.Editable
import com.google.android.material.textfield.TextInputLayout
import java.lang.StringBuilder

class ValidaTelefone(private val text: Editable, private val textInput: TextInputLayout) {

    private val campo = textInput.editText

    fun estaValido(): Boolean {
        val validacaoPadrao = ValidacaoPadrao(text,textInput)
        if(!validacaoPadrao.estaValido()) return false
        if(!validaEntre10ou11()) return false
        adicionaFormatacao(text)
        textInput.error = null
        textInput.isErrorEnabled = false
        return true
    }

    private fun adicionaFormatacao(text: Editable) {
//        val stringBuilder = StringBuilder()
//        val total = text.length
//        for (i in 0..total-1){
//            if(i==0){
//                stringBuilder.append("(")
//            }
//            val digito = text[i]
//            stringBuilder.append(digito)
//            if(i==1){
//                stringBuilder.append(") ")
//            }
//            if(i==5 && total==10){
//                stringBuilder.append("-")
//            }
//            if(i==6 && total==11){
//                stringBuilder.append("-")
//            }
//        }
        val formatado = text.toString().replace(Regex("([0-9]{2})([0-9]{4,5})([0-9]{4})"), "($1) $2-$3")
        campo!!.setText(formatado)
    }

    private fun validaEntre10ou11() : Boolean{
        val digitos = text.length
        if(digitos<10 || digitos>11){
            textInput.error = "Telefone deve ter entre 10 a 11 dígitos"
            return false
        }
        return true
    }

}