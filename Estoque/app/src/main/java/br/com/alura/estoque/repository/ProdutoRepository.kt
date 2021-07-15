package br.com.alura.estoque.repository

import br.com.alura.estoque.asynctask.BaseAsyncTask
import br.com.alura.estoque.database.dao.ProdutoDAO
import br.com.alura.estoque.model.Produto
import br.com.alura.estoque.retrofit.EstoqueRetrofit
import br.com.alura.estoque.retrofit.callback.BaseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdutoRepository(private val dao: ProdutoDAO) {

    private val produtoService = EstoqueRetrofit().getProdutoService()

    fun buscaProdutos(callback: BaseCallback.RespostaCallback<List<Produto>>) {
        buscaProdutosInterno(callback)
    }

    private fun buscaProdutosInterno(callback: BaseCallback.RespostaCallback<List<Produto>>) {
        BaseAsyncTask({ dao.buscaTodos() },
                { resultado: List<Produto> ->
                    callback.quandoSucesso(resultado)
                    buscaProdutosApi(callback)
                }).execute()
    }

    private fun buscaProdutosApi(callback: BaseCallback.RespostaCallback<List<Produto>>) {
        val call = produtoService.buscaTodos()
        call.enqueue(object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    val produtosNovos = response.body()
                    if (produtosNovos != null) {
                        BaseAsyncTask({
                            dao.salva(produtosNovos)
                            dao.buscaTodos()
                        }, { produtos: List<Produto> -> callback.quandoSucesso(produtos) }).execute()
                    }
                } else {
                    callback.quandoFalha("Resposta não sucedida!")
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                callback.quandoFalha("Falha de comunicação: ${t.message}")
            }

        })
    }

    fun salva(produto: Produto, callback: BaseCallback.RespostaCallback<Produto>) {
        salvaNaAPI(produto, callback)
    }

    private fun salvaNaAPI(produto: Produto, callback: BaseCallback.RespostaCallback<Produto>) {
        val call = produtoService.salva(produto)
        call.enqueue(BaseCallback<Produto>(object : BaseCallback.RespostaCallback<Produto> {
            override fun quandoSucesso(resultado: Produto) {
                salvaProdutoInterno(resultado, callback)
            }

            override fun quandoFalha(erro: String) {
                callback.quandoFalha(erro)
            }

        }))
    }

    private fun salvaProdutoInterno(produto: Produto, callback: BaseCallback.RespostaCallback<Produto>) {
        BaseAsyncTask({
            val id = dao.salva(produto)
            dao.buscaProduto(id)
        }, { produtoSalvo: Produto? -> callback.quandoSucesso(produtoSalvo!!) })
                .execute()
    }

    fun edita(produto: Produto, callback: BaseCallback.RespostaCallback<Produto>) {
        val call = produtoService.edita(produto.id, produto)
        call.enqueue(BaseCallback<Produto>(object : BaseCallback.RespostaCallback<Produto> {
            override fun quandoSucesso(resultado: Produto) {
                BaseAsyncTask({
                    dao.atualiza(produto)
                    produto
                }) { produtoEditado: Produto? -> callback.quandoSucesso(produto) }
                        .execute()
            }

            override fun quandoFalha(erro: String) {
                callback.quandoFalha(erro)
            }

        }))
    }

    fun remove(produtoRemovido: Produto, callback: BaseCallback.RespostaCallback<Unit?>) {
        val call = produtoService.remove(produtoRemovido.id)
        call.enqueue(BaseCallback(object : BaseCallback.RespostaCallback<Unit?> {
            override fun quandoSucesso(resultado: Unit?) {
                BaseAsyncTask(
                        {
                            dao.remove(produtoRemovido)
                            null
                        })
                { resultado -> callback.quandoSucesso(resultado) }
                        .execute()
            }

            override fun quandoFalha(erro: String) {
                callback.quandoFalha(erro)
            }

        }))

    }

}