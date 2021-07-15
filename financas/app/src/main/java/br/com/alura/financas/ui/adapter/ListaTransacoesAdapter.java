package br.com.alura.financas.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.financas.R;
import br.com.alura.financas.model.Tipo;
import br.com.alura.financas.model.Transacao;
import br.com.alura.financas.util.MoedaUtil;

public class ListaTransacoesAdapter extends BaseAdapter {

    public static final int LIMITE_DE_CATEGORIA = 14;
    private final Context context;
    private final List<Transacao> transacoes;

    public ListaTransacoesAdapter(Context context, List<Transacao> transacoes) {
        this.context = context;
        this.transacoes = transacoes;
    }

    @Override
    public int getCount() {
        return transacoes.size();
    }

    @Override
    public Object getItem(int position) {
        return transacoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.transacao_item, viewGroup, false);

        Transacao transacao = transacoes.get(position);

        TextView campoDescrico = view.findViewById(R.id.transacao_categoria);
        adicionaCategoria(transacao, campoDescrico);

        TextView campoData = view.findViewById(R.id.transacao_data);
        adicionaData(transacao, campoData);

        TextView campoValor = view.findViewById(R.id.transacao_valor);
        adicionaValor(transacao, campoValor);

        ImageView imagemDoIcone = view.findViewById(R.id.transacao_icone);
        adicionaIcone(transacao, imagemDoIcone);

        return view;
    }

    private void adicionaIcone(Transacao transacao, ImageView imageView) {
        int icone = iconePor(transacao.getTipo());
        imageView.setBackgroundResource(icone);
    }

    private void adicionaData(Transacao transacao, TextView campoData) {
        campoData.setText(transacao.getDataFormatada());
    }

    private void adicionaValor(Transacao transacao, TextView campoValor) {
        int cor = corPor(transacao.getTipo());
        campoValor.setTextColor(cor);
        campoValor.setText(MoedaUtil.formataParaBrasileiro(transacao.getValor()));
    }

    private int iconePor(Tipo tipo) {
        if (tipo == Tipo.RECEITA) {
            return R.drawable.icone_transacao_item_receita;
        } else {
            return R.drawable.icone_transacao_item_despesa;
        }
    }

    private int corPor(Tipo tipo) {
        if (tipo.equals(Tipo.RECEITA)) {
            return ContextCompat.getColor(context, R.color.receita);
        } else {
            return ContextCompat.getColor(context, R.color.despesa);
        }
    }

    private void adicionaCategoria(Transacao transacao, TextView campoCategoria) {
        String categoria = transacao.getCategoria();
        if (categoria.length() > LIMITE_DE_CATEGORIA)
            categoria = categoria.substring(0, 14) + "...";
        campoCategoria.setText(categoria);
    }
}
