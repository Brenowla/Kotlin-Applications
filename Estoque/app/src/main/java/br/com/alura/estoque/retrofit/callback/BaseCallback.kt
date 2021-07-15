package br.com.alura.estoque.retrofit.callback

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BaseCallback<T>(private val respostaCallback: RespostaCallback<T>) : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if(response.isSuccessful){
            val resultado = response.body()
            if (resultado != null){
                respostaCallback.quandoSucesso(resultado)
            }
        }else {
            respostaCallback.quandoFalha("Resposta não sucedida!")
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        respostaCallback.quandoFalha("Resposta não sucedida! + ${t.message}")
    }

    interface RespostaCallback<T> {
        fun quandoSucesso(resultado: T)
        fun quandoFalha(erro: String)
    }

}