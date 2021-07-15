package br.com.alura.financas.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.financas.model.Transacao;

public class TransacaoDAO {

    private static final List<Transacao> TRANSACOES = new ArrayList<>();

    public List<Transacao> getTransacoes() {
        return TRANSACOES;
    }

    public void adiciona(Transacao... transacoes) {
        for (Transacao transacao :
                transacoes) {
            TRANSACOES.add(transacao);
        }
    }

    public void remove(int position) {
        TRANSACOES.remove(position);
    }

    public void altera(Transacao transacao, int position) {
        TRANSACOES.set(position, transacao);
    }
}
