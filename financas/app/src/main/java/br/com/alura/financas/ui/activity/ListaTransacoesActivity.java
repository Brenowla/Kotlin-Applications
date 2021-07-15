package br.com.alura.financas.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import br.com.alura.financas.R;
import br.com.alura.financas.dao.TransacaoDAO;
import br.com.alura.financas.delegate.TransacaoDelegate;
import br.com.alura.financas.model.Resumo;
import br.com.alura.financas.model.Tipo;
import br.com.alura.financas.model.Transacao;
import br.com.alura.financas.ui.ResumoView;
import br.com.alura.financas.ui.adapter.ListaTransacoesAdapter;
import br.com.alura.financas.ui.dialog.AdicionaTransacaoDialog;
import br.com.alura.financas.ui.dialog.AlteraTransacaoDialog;

public class ListaTransacoesActivity extends AppCompatActivity {

    private ListaTransacoesAdapter adapter;
    private List<Transacao> transacoes;
    private ViewGroup viewRoot;
    private ResumoView resumoView;
    private ListView listaTransacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_transacoes);
        viewRoot = (ViewGroup) findViewById(android.R.id.content);
        transacoes = new TransacaoDAO().getTransacoes();
        resumoView = new ResumoView(this, viewRoot);
        listaTransacoes = (ListView) findViewById(R.id.lista_transacoes_listview);
        configuraListaTransacoes();
        configuraFabMenu();
    }

    private void configuraFabMenu() {
        final FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.lista_transacoes_adiciona_menu);
        FloatingActionButton fabReceita = (FloatingActionButton) findViewById(R.id.lista_transacoes_adiciona_receita);
        FloatingActionButton fabDespesa = (FloatingActionButton) findViewById(R.id.lista_transacoes_adiciona_despesa);
        fabMenu.setClosedOnTouchOutside(true);
        configuraFab(fabMenu, Tipo.RECEITA, fabReceita);
        configuraFab(fabMenu, Tipo.DESPESA, fabDespesa);
    }

    private void configuraFab(final FloatingActionMenu fabMenu, final Tipo tipo, FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AdicionaTransacaoDialog(ListaTransacoesActivity.this, viewRoot)
                        .mostraFormulario(tipo, new TransacaoDelegate() {
                            @Override
                            public void executa(Transacao transacao) {
                                adiciona(transacao);
                            }
                        });
                fabMenu.close(true);
            }
        });
    }

    private void configuraListaTransacoes() {
        configuraAdapter();
        listaTransacoes.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(Menu.NONE, 1, Menu.NONE, "Remover");
            }
        });
        listaTransacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int posicao, long id) {
                Transacao transacaoDevolvida = transacoes.get(posicao);
                new AlteraTransacaoDialog(ListaTransacoesActivity.this, viewRoot)
                        .mostraFormulario(transacaoDevolvida, new TransacaoDelegate() {
                            @Override
                            public void executa(Transacao transacao) {
                                altera(transacao, posicao);
                            }
                        });
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        if (item.getItemId() == 1) {
            remove(position);
        }
        return super.onContextItemSelected(item);
    }

    private void atualizaTransacoes() {
        transacoes = new TransacaoDAO().getTransacoes();
        configuraAdapter();
    }

    private void configuraAdapter() {
        adapter = new ListaTransacoesAdapter(this, transacoes);
        listaTransacoes.setAdapter(adapter);
        atualizaResumo();
    }

    private void atualizaResumo() {
        Resumo resumo = new Resumo(transacoes);
        resumoView.atualiza(resumo);
    }

    private void adiciona(Transacao transacao) {
        new TransacaoDAO().adiciona(transacao);
        atualizaTransacoes();
    }

    private void remove(int position) {
        new TransacaoDAO().remove(position);
        atualizaTransacoes();
    }

    private void altera(Transacao transacao, int posicao) {
        new TransacaoDAO().altera(transacao, posicao);
        atualizaTransacoes();
    }
}