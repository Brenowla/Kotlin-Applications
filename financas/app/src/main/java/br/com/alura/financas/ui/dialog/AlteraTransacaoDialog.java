package br.com.alura.financas.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import br.com.alura.financas.R;
import br.com.alura.financas.delegate.TransacaoDelegate;
import br.com.alura.financas.model.Tipo;
import br.com.alura.financas.model.Transacao;

/**
 * Created by alex on 16/08/17.
 */

public class AlteraTransacaoDialog extends FormularioTransacaoDialog {

    public AlteraTransacaoDialog(Context context, ViewGroup viewRoot) {
        super(context, viewRoot);
    }

    public void mostraFormulario(final Transacao transacao, final TransacaoDelegate delegate) {
        final Tipo tipo = transacao.getTipo();

        String titulo = devolveTitulo(tipo);
        String[] categorias = devolveCategorias(tipo);

        adicionaValoresPadrao(transacao, categorias);

        mostraDialog(tipo, delegate, titulo, "Alterar", "Cancelar");
    }

    @NonNull
    private String[] devolveCategorias(Tipo tipo) {
        String[] categorias = context.getResources().getStringArray(R.array.categorias_de_despesa);
        if (tipo.equals(Tipo.RECEITA)) {
            categorias = context.getResources().getStringArray(R.array.categorias_de_receita);
        }
        return categorias;
    }

    @NonNull
    private String devolveTitulo(Tipo tipo) {
        String titulo = "Alterar despesa";
        if (tipo.equals(Tipo.RECEITA)) {
            titulo = "Alterar receita";
        }
        return titulo;
    }

    private void adicionaValoresPadrao(Transacao transacao, String[] categorias) {
        int posicaoPadraoCategoria = devolvePosicaoDaCategoria(transacao, categorias);
        valor.setText(transacao.getValorFormatado().toString());
        data.setText(transacao.getDataFormatada());
        categoria.setSelection(posicaoPadraoCategoria);
    }

    protected int devolvePosicaoDaCategoria(Transacao transacao, String[] categorias) {
        int primeiroItem = 0;
        int posicaoPadraoCategoria = primeiroItem;
        for (int i = 0; i < categorias.length; i++) {
            String categoria = categorias[i];
            if (categoria.equals(transacao.getCategoria())) {
                return i;
            }
        }
        return posicaoPadraoCategoria;
    }
}
