package br.com.alura.estoque.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.alura.estoque.R;
import br.com.alura.estoque.asynctask.BaseAsyncTask;
import br.com.alura.estoque.database.EstoqueDatabase;
import br.com.alura.estoque.database.dao.ProdutoDAO;
import br.com.alura.estoque.model.Produto;
import br.com.alura.estoque.repository.ProdutoRepository;
import br.com.alura.estoque.retrofit.callback.BaseCallback;
import br.com.alura.estoque.ui.dialog.EditaProdutoDialog;
import br.com.alura.estoque.ui.dialog.SalvaProdutoDialog;
import br.com.alura.estoque.ui.recyclerview.adapter.ListaProdutosAdapter;
import kotlin.Unit;

public class ListaProdutosActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Lista de produtos";
    private ListaProdutosAdapter adapter;
    private ProdutoDAO dao;
    private ProdutoRepository produtoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        setTitle(TITULO_APPBAR);

        configuraListaProdutos();
        configuraFabSalvaProduto();

        EstoqueDatabase db = EstoqueDatabase.getInstance(this);
        dao = db.getProdutoDAO();

        produtoRepository = new ProdutoRepository(dao);
        produtoRepository.buscaProdutos(new BaseCallback.RespostaCallback<List<Produto>>() {
            @Override
            public void quandoSucesso(List<Produto> resultado) {
                adapter.atualiza(resultado);
            }

            @Override
            public void quandoFalha(@NotNull String erro) {
                Toast.makeText(ListaProdutosActivity.this, erro, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void configuraListaProdutos() {
        RecyclerView listaProdutos = findViewById(R.id.activity_lista_produtos_lista);
        adapter = new ListaProdutosAdapter(this, this::abreFormularioEditaProduto);
        listaProdutos.setAdapter(adapter);
        adapter.setOnItemClickRemoveContextMenuListener((posicao,produtoRemovido) -> {
            produtoRepository.remove(produtoRemovido, new BaseCallback.RespostaCallback<Unit>() {
                @Override
                public void quandoSucesso(Unit resultado) {
                    adapter.remove(posicao);
                }

                @Override
                public void quandoFalha(@NotNull String erro) {
                    Toast.makeText(ListaProdutosActivity.this, erro, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }


    private void configuraFabSalvaProduto() {
        FloatingActionButton fabAdicionaProduto = findViewById(R.id.activity_lista_produtos_fab_adiciona_produto);
        fabAdicionaProduto.setOnClickListener(v -> abreFormularioSalvaProduto());
    }

    private void abreFormularioSalvaProduto() {
        new SalvaProdutoDialog(this, produto -> produtoRepository.salva(produto, new BaseCallback.RespostaCallback<Produto>() {
            @Override
            public void quandoSucesso(Produto resultado) {
                adapter.adiciona(resultado);
            }

            @Override
            public void quandoFalha(@NotNull String erro) {
                Toast.makeText(ListaProdutosActivity.this, erro, Toast.LENGTH_SHORT).show();
            }
        })).mostra();
    }

    private void abreFormularioEditaProduto(int posicao, Produto produto) {
        new EditaProdutoDialog(this, produto,
                produtoEditado -> produtoRepository.edita(produtoEditado, new BaseCallback.RespostaCallback<Produto>() {
                    @Override
                    public void quandoFalha(@NotNull String erro) {
                        Toast.makeText(ListaProdutosActivity.this, erro, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void quandoSucesso(Produto resultado) {
                        adapter.edita(posicao, produtoEditado);
                    }
                })).mostra();
    }


}
