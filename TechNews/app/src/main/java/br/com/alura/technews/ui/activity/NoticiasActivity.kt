package br.com.alura.technews.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.extensions.transacaoFragment
import br.com.alura.technews.ui.fragment.ListaNoticiasFragment
import br.com.alura.technews.ui.fragment.VisualizaNoticiaFragment
import kotlinx.android.synthetic.main.activity_noticias.*

private const val TAG_FRAGMENT_LISTA_NOTICIAS = "lista_noticias"
private const val TAG_FRAGMENT_VISUALIZA_NOTICIA = "visualiza_noticia"

class NoticiasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        if (savedInstanceState == null) {
            createListaFragment()
        } else {
            supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_VISUALIZA_NOTICIA)
                ?.let { fragment ->

                    val arguments = fragment.arguments
                    val visualizaNoticiaFragment = VisualizaNoticiaFragment()
                    visualizaNoticiaFragment.arguments = arguments
                    transacaoFragment {
                        remove(fragment)
                    }
                    supportFragmentManager.popBackStack()
                    transacaoFragment {
                        val container = if (activity_noticias_container_secundario != null) {
                            R.id.activity_noticias_container_secundario
                        } else {
                            addToBackStack(null)
                            R.id.activity_noticias_container_primario
                        }
                        replace(container, visualizaNoticiaFragment, TAG_FRAGMENT_VISUALIZA_NOTICIA)
                    }
                }
        }
    }

    private fun createListaFragment() {
        transacaoFragment {
            add(
                R.id.activity_noticias_container_primario,
                ListaNoticiasFragment(),
                TAG_FRAGMENT_LISTA_NOTICIAS
            )
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is ListaNoticiasFragment -> {
                configuraListaNoticias(fragment)
            }
            is VisualizaNoticiaFragment -> {
                configuraVisualizaNoticia(fragment)
            }
        }
    }

    private fun configuraVisualizaNoticia(fragment: VisualizaNoticiaFragment) {
        fragment.quandoFinalizaTela = {
            supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_VISUALIZA_NOTICIA)
                ?.let { fragment ->
                    transacaoFragment {
                        remove(fragment)
                    }
                    supportFragmentManager.popBackStack()
                }
        }
        fragment.quandoSelecionaMenuEdicao = { noticiaSelecionada ->
            abreFormularioEdicao(noticiaSelecionada)
        }
    }

    private fun configuraListaNoticias(fragment: ListaNoticiasFragment) {
        fragment.quandoNoticiaSelecionada = this::abreVisualizadorNoticia
        fragment.quandoFabSalvaNoticiaClicado = this::abreFormularioModoCriacao
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {
//        val fragmentEncontrado = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_LISTA_NOTICIAS)
        val transacao = supportFragmentManager.beginTransaction()
        val visualizaNoticiaFragment = VisualizaNoticiaFragment()
        val dados = Bundle()
        dados.putLong(NOTICIA_ID_CHAVE, noticia.id)
        visualizaNoticiaFragment.arguments = dados

//        fragmentEncontrado?.let {
//            transacao.remove(it)
//        }
        transacaoFragment {
            val container = if (activity_noticias_container_secundario != null) {
                R.id.activity_noticias_container_secundario
            } else {
                addToBackStack(null)
                R.id.activity_noticias_container_primario
            }
            replace(container, visualizaNoticiaFragment, TAG_FRAGMENT_VISUALIZA_NOTICIA)
        }
    }

}
