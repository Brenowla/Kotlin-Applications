package br.com.alura.financas.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Resumo {

    private final List<Transacao> transacoes;

    public Resumo() {
        transacoes = new ArrayList<>();
    }

    public Resumo(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public List<Transacao> getTransacoes() {
        return Collections.unmodifiableList(transacoes);
    }

    public BigDecimal getReceita() {
        return somaPor(Tipo.RECEITA);
    }

    public BigDecimal getDespesa() {
        return somaPor(Tipo.DESPESA);
    }

    private BigDecimal somaPor(Tipo tipo) {
        BigDecimal totalDeGasto = new BigDecimal(0.0);
        for (Transacao transacao :
                transacoes) {
            if (transacao.getTipo().equals(tipo)) {
                totalDeGasto = totalDeGasto.add(transacao.getValor());
            }
        }
        return totalDeGasto.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public BigDecimal getTotal() {
        return getReceita().subtract(getDespesa()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }




}
