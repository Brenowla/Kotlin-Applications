package com.example.alurafood.validator

import android.text.Editable
import br.com.caelum.stella.format.CPFFormatter
import br.com.caelum.stella.validation.CPFValidator
import br.com.caelum.stella.validation.InvalidStateException
import com.google.android.material.textfield.TextInputLayout

class ValidaCPF(private val text: Editable,private val textinput: TextInputLayout) {

    private val cpfFormatter = CPFFormatter()

    fun estaValido(): Boolean{
        val validacaoPadrao = ValidacaoPadrao(text, textinput)
        if (!validacaoPadrao.estaValido()) return false
        if (!validaCompo11Digitos()) return false
        if (!validaCPF()) return false
        val formatedCPF = cpfFormatter.format(text.toString())
        val campo = textinput.editText
        campo!!.setText(formatedCPF)
        textinput.error = null
        textinput.isErrorEnabled = false
        return true
    }

    private fun validaCompo11Digitos(): Boolean {
        if (text.length != 11) {
            textinput.error = "O CPF precisa ter 11 dígitos!"
            return false
        }
        return true
    }

    private fun validaCPF(): Boolean {
        return try {
            val cpfValidator = CPFValidator()
            cpfValidator.assertValid(text.toString())
            true
        } catch (e: InvalidStateException) {
            textinput.error = "CPF inválido"
            false
        }
    }
}