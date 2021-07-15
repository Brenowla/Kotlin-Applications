package br.com.alura.estoque.retrofit.service

import br.com.alura.estoque.model.Produto
import retrofit2.Call
import retrofit2.http.*

interface ProdutoService {

    @GET("produto")
    fun buscaTodos(): Call<List<Produto>>

    @POST("produto")
    fun salva(@Body produto: Produto): Call<Produto>

    @PUT("produto/{id}")
    fun edita(@Path("id") id: Long, @Body produto: Produto): Call<Produto>

    @DELETE("produto/{id}")
    fun remove(@Path("id") id: Long): Call<Unit?>



}