package br.com.alura.technews.di.modules

import androidx.room.Room
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.database.dao.NoticiaDAO
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.retrofit.webclient.NoticiaWebClient
import br.com.alura.technews.ui.viewmodel.FormularioNoticiaViewModel
import br.com.alura.technews.ui.viewmodel.ListaNoticiasViewModel
import br.com.alura.technews.ui.viewmodel.VisualizaNoticiaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val NOME_BANCO_DE_DADOS = "news.db"

val appModules = module {
    // Modo factory sempre cria uma noa instância da depêndencia
    //factory {  }
    // Modo single segue o padrão singleton, criando apenas um objeto da classe quando criado
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }
    single<NoticiaDAO>{
        get<AppDatabase>().noticiaDAO
    }
    single<NoticiaWebClient> {
        NoticiaWebClient()
    }
    single<NoticiaRepository> {
        NoticiaRepository(get(),get())
    }
    viewModel<ListaNoticiasViewModel> {
        ListaNoticiasViewModel(get())
    }
    viewModel<VisualizaNoticiaViewModel> { (id: Long) ->
        VisualizaNoticiaViewModel(id,get())
    }
    viewModel<FormularioNoticiaViewModel> {
        FormularioNoticiaViewModel(get())
    }
}