package com.example.agenda.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.agenda.R
import com.example.agenda.database.AgendaDatabase
import com.example.agenda.database.DAO.RoomAlunoDAO
import com.example.agenda.database.DAO.TelefoneDAO
import com.example.agenda.model.Aluno
import com.example.agenda.model.Telefone
import com.example.agenda.model.TipoTelefone

class FormularioAlunoActivity : AppCompatActivity() {

    private lateinit var aluno: Aluno
    private lateinit var dao: RoomAlunoDAO
    private lateinit var telefoneDAO: TelefoneDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_aluno)

        dao = AgendaDatabase.getInstance(this).getRoomAlunoDAO()
        telefoneDAO = AgendaDatabase.getInstance(this).getTelefoneDAO()

        val nome = findViewById<EditText>(R.id.activity_formulario_aluno_nome)
        val telefoneCelular = findViewById<EditText>(R.id.activity_formulario_aluno_telefone_celular)
        val telefoneFixo = findViewById<EditText>(R.id.activity_formulario_aluno_telefone_fixo)
        val email = findViewById<EditText>(R.id.activity_formulario_aluno_email)

        getDados(nome,telefoneCelular,telefoneFixo,email)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_formulario_aluno_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if(itemId == R.id.activity_formulario_aluno_menu_salvar){
            val nome = findViewById<EditText>(R.id.activity_formulario_aluno_nome)
            val telefoneCelular = findViewById<EditText>(R.id.activity_formulario_aluno_telefone_celular)
            val telefoneFixo = findViewById<EditText>(R.id.activity_formulario_aluno_telefone_fixo)
            val email = findViewById<EditText>(R.id.activity_formulario_aluno_email)
            finalizaFormulario(nome, telefoneCelular, telefoneFixo, email)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDados(nome: EditText, telefoneCelular: EditText, telefoneFixo: EditText, email: EditText){
        val dados = intent
        if(dados.hasExtra("aluno")) {
            title = "Editar Aluno"
            aluno = dados.getSerializableExtra("aluno") as Aluno
            nome.setText(aluno.nome)
            email.setText(aluno.email)
            val listaTelefones = telefoneDAO.buscaTelefonesDoAluno(aluno.getId())
            for (i in listaTelefones){
                if(i.tipo == TipoTelefone.FIXO){
                    telefoneFixo.setText(i.numero)
                }else {
                    telefoneCelular.setText(i.numero)
                }
            }
        }else {
            title = "Novo Aluno"
            aluno = Aluno("","")
        }
    }

    private fun finalizaFormulario(
        nome: EditText,
        telefoneCelular: EditText,
        telefoneFixo: EditText,
        email: EditText,
    ) {
        val (textNome, textTelefoneFixo, textEmail) = createAluno(nome, telefoneFixo, email)
        val textTelefoneCelular = telefoneCelular.text.toString()
        aluno.nome = textNome
        //aluno.telefoneCelular = textTelefoneCelular
        //aluno.telefoneFixo = textTelefoneFixo
        aluno.email = textEmail


        if (aluno.getId() == 0) {
            val id = dao.salva(aluno)
            val numerofixo = telefoneFixo.text.toString()
            val numeroCelular = telefoneCelular.text.toString()
            telefoneDAO.salvar(Telefone(numerofixo,TipoTelefone.FIXO, id.toInt()),Telefone(numeroCelular, TipoTelefone.CELULAR, id.toInt()))

        } else {
            val numerofixo = telefoneFixo.text.toString()
            val numeroCelular = telefoneCelular.text.toString()
            val telefone_Fixo = Telefone(numerofixo,TipoTelefone.FIXO, aluno.getId())
            val telefone_Celular = Telefone(numeroCelular, TipoTelefone.CELULAR, aluno.getId())
            val telefonesDoAluno = telefoneDAO.buscaTelefonesDoAluno(aluno.getId())
            for (i in telefonesDoAluno){
                if(i.tipo == TipoTelefone.FIXO){
                    telefone_Fixo.id = i.id
                }else {
                    telefone_Celular.id = i.id
                }
            }
            dao.edit(aluno)
            telefoneDAO.atualiza(listOf(telefone_Fixo, telefone_Celular))
        }
        finish()
    }

    private fun createAluno(
        nome: EditText,
        telefone: EditText,
        email: EditText
    ): Triple<String, String, String> {
        val textNome = nome.text.toString()
        val textTelefone = telefone.text.toString()
        val textEmail = email.text.toString()
        return Triple(textNome, textTelefone, textEmail)
    }
}