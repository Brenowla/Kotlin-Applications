package com.example.alurafood.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import br.com.caelum.stella.format.CPFFormatter
import br.com.caelum.stella.validation.CPFValidator
import br.com.caelum.stella.validation.InvalidStateException
import com.example.alurafood.R
import com.example.alurafood.validator.ValidaCPF
import com.example.alurafood.validator.ValidaTelefone
import com.example.alurafood.validator.ValidacaoPadrao
import com.google.android.material.textfield.TextInputLayout
import java.lang.IllegalArgumentException

class FormularioCadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_cadastro)
        inicializaCampos()
    }

    private fun inicializaCampos() {
        configNome()
        configCPF()
        configTelefone()
        configEmail()
        configSenha()
    }

    private fun configSenha() {
        val inputSenha = findViewById<TextInputLayout>(R.id.formulario_cadastro_campo_senha)
        adicionaValidacaoPadrao(inputSenha)
    }

    private fun configEmail() {
        val inputEmail = findViewById<TextInputLayout>(R.id.formulario_cadastro_campo_email)
        adicionaValidacaoPadrao(inputEmail)
    }

    private fun configTelefone() {
        val inputTelefone = findViewById<TextInputLayout>(R.id.formulario_cadastro_campo_telefone)
        val campoTelefone = inputTelefone.editText
        val telefone = campoTelefone!!.text
        val validaTelefone = ValidaTelefone(telefone, inputTelefone)
        campoTelefone?.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (!hasFocus) {
                    validaTelefone.estaValido()
                } else {
//                    campoTelefone.setText(telefone.toString()
//                        .replace("(", "")
//                        .replace(")", "")
//                        .replace(" ", "")
//                        .replace("-", ""))
                }
            }
        }
    }

    private fun configCPF() {
        val inputCPF = findViewById<TextInputLayout>(R.id.formulario_cadastro_campo_cpf)
        val campoCPF = inputCPF.editText
        val CPF = campoCPF!!.text
        val validaCPF = ValidaCPF(CPF, inputCPF)
        campoCPF?.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                val cpfFormatter = CPFFormatter()
                if (!hasFocus) {
                    validaCPF.estaValido()
                } else {
                    removeFormatacaoCPF(cpfFormatter, CPF, campoCPF)
                }
            }
        }
    }

    private fun removeFormatacaoCPF(cpfFormatter: CPFFormatter, CPF: Editable, campoCPF: EditText) {
        try {
            val unformatedCPF = cpfFormatter.unformat(CPF.toString())
            campoCPF.setText(unformatedCPF)
        } catch (e: IllegalArgumentException) {
            Log.e("Erro formatação!", "Erro formatação CPF")
        }
    }

    private fun configNome() {
        val inputNome = findViewById<TextInputLayout>(R.id.formulario_cadastro_campo_nome)
        adicionaValidacaoPadrao(inputNome)
    }

    private fun removeError(inputCPF: TextInputLayout) {
        inputCPF.error = null
        inputCPF.isErrorEnabled = false
    }

    private fun adicionaValidacaoPadrao(textinput: TextInputLayout) {
        val campo = textinput.editText
        val text = campo!!.text
        val validacaoPadrao = ValidacaoPadrao(text, textinput)
        campo.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                validacaoPadrao.estaValido()
            }
        })
    }


//    private fun adicionaValidacaoPadrao(campo: EditText?) {
//        campo?.onFocusChangeListener = object : View.OnFocusChangeListener {
//            override fun onFocusChange(v: View, hasFocus: Boolean) {
//                val text = campo?.text
//                if(!hasFocus) {
//                    if (text?.isEmpty() == true) {
//                        campo.error = "Campo obrigatório!"
//                    }
//                }
//            }
//        }
//    }
}