package br.com.alura.estoque.retrofit

import br.com.alura.estoque.retrofit.service.ProdutoService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EstoqueRetrofit {

    private val produtoService: ProdutoService

    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client= OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.2.11:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        produtoService = retrofit.create(ProdutoService::class.java)
    }

    fun getProdutoService(): ProdutoService {
        return produtoService
    }

}