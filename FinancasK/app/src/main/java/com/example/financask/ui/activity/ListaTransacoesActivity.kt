package com.example.financask.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.financask.R
import com.example.financask.model.Tipo
import com.example.financask.model.Transacao
import com.example.financask.ui.ResumoView
import com.example.financask.ui.adapter.ListaTransacoesAdapter
import com.example.financask.ui.dialog.AdicionaTransacaoDialog
import com.example.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    //private lateinit var viewDaActivity: View
    //lazy faz com que a inicialização só ocorra quando a variável for utilizada
    private val viewDaActivity: View by lazy {
        window.decorView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        //viewDaActivity = window.decorView

        configuraResumo()

        configuraListaTransacoes()

        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogAddTransacao(Tipo.RECEITA)

        }

        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogAddTransacao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogAddTransacao(tipo: Tipo) {
        AdicionaTransacaoDialog(this, viewDaActivity as ViewGroup)
            .configuraDialog(tipo) { transacao ->
                transacoes.add(transacao)
                atualizaListaEView()
                lista_transacoes_adiciona_menu.close(true)
            }

    }


    private fun atualizaListaEView() {
        configuraListaTransacoes()
        configuraResumo()
    }

    private fun configuraResumo() {
        ResumoView(window.decorView, transacoes, this).configResumo()
    }

    private fun configuraListaTransacoes() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
        lista_transacoes_listview.setOnItemClickListener { parent, view, position, id ->
            val transacao = transacoes[position]
            AlteraTransacaoDialog(this, viewDaActivity as ViewGroup)
                .configuraDialog(transacao) { transacao: Transacao ->
                    transacoes[position] = transacao
                    atualizaListaEView()
                }
        }
        lista_transacoes_listview.setOnCreateContextMenuListener { menu,_, _ ->
            menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val position = adapterMenuInfo.position
            transacoes.removeAt(position)
            atualizaListaEView()
        }
        return super.onContextItemSelected(item)
    }

}