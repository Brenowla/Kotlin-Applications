package br.com.alura.financas.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;

import br.com.alura.financas.R;
import br.com.alura.financas.model.Resumo;
import br.com.alura.financas.util.MoedaUtil;

/**
 * Created by alex on 16/08/17.
 */

public class ResumoView {

    private final Context context;
    private final ViewGroup viewRoot;
    private final int COR_RECEITA;
    private final int COR_DESPESA;

    public ResumoView(Context context, ViewGroup viewRoot) {
        this.context = context;
        this.viewRoot = viewRoot;
        COR_RECEITA = ContextCompat.getColor(context, R.color.receita);
        COR_DESPESA = ContextCompat.getColor(context, R.color.despesa);
    }

    public void atualiza(Resumo resumo) {
        adicionaReceita(resumo);
        adicionaDespesa(resumo);
        adicionaTotal(resumo);
    }

    private void adicionaTotal(Resumo resumo) {
        BigDecimal total = resumo.getTotal();
        TextView campoTotal = viewRoot.findViewById(R.id.resumo_card_total);
        campoTotal.setText(MoedaUtil.formataParaBrasileiro(total));
        campoTotal.setTextColor(devolveCorPor(total));
    }

    private void adicionaReceita(Resumo resumo) {
        String text = MoedaUtil.formataParaBrasileiro(resumo.getReceita());
        TextView campoReceita = viewRoot.findViewById(R.id.resumo_card_receita);
        campoReceita.setText(text);
        campoReceita.setTextColor(COR_RECEITA);
    }

    private void adicionaDespesa(Resumo resumo) {
        String totalDespesa = MoedaUtil.formataParaBrasileiro(resumo.getDespesa());
        TextView campoDespesa = viewRoot.findViewById(R.id.resumo_card_despesa);
        campoDespesa.setText(totalDespesa);
        campoDespesa.setTextColor(COR_DESPESA);
    }

    private int devolveCorPor(BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) >= 0)
            return COR_RECEITA;
        else
            return COR_DESPESA;
    }
}
