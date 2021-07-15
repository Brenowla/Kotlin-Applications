package br.com.alura.financas.delegate;

import br.com.alura.financas.model.Transacao;

/**
 * Created by alex on 16/08/17.
 */

public interface TransacaoDelegate {
    void executa(Transacao transacao);
}
