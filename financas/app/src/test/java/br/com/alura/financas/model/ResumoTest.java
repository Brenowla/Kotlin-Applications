package br.com.alura.financas.model;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.alura.financas.model.Tipo.RECEITA;
import static br.com.alura.financas.model.Tipo.DESPESA;
import static org.junit.Assert.*;

public class ResumoTest {

    @Test
    public void adicionaReceita() {
        Transacao receita = new Transacao(new BigDecimal(100.0), RECEITA);
        Resumo resumoDeTransacoes = new Resumo(Arrays.asList(receita));
        Transacao transacaoDevolvida = resumoDeTransacoes.getTransacoes().get(0);

        BigDecimal valor = transacaoDevolvida.getValor();
        assertEquals(new BigDecimal(100.0), valor);

        Tipo tipoDevolvido = transacaoDevolvida.getTipo();
        assertEquals(RECEITA, tipoDevolvido);

        assertEquals(receita, transacaoDevolvida);
    }

    @Test
    public void adicionaDebito() {
        Transacao despesa = new Transacao(new BigDecimal(100.0), DESPESA);
        Resumo resumoDeTransacoes = new Resumo(Arrays.asList(despesa));
        Transacao transacaoDevolvida = resumoDeTransacoes.getTransacoes().get(0);

        BigDecimal valor = transacaoDevolvida.getValor();
        assertEquals(new BigDecimal(100.0), valor);

        Tipo tipoDevolvido = transacaoDevolvida.getTipo();
        assertEquals(DESPESA, tipoDevolvido);

        assertEquals(despesa, transacaoDevolvida);
    }

    @Test
    public void adicionaTransacoes() {
        Resumo resumoDeTransacoes = new Resumo(getTransacoesDeExemplo());
        List<Transacao> transacoesDevolvidas = resumoDeTransacoes.getTransacoes();
        int totalTransacoes = transacoesDevolvidas.size();
        assertEquals(10, totalTransacoes);
    }

    @Test
    public void retornaTotalDoResumo() {
        Resumo ResumoDeTransacoes = new Resumo(getTransacoesDeExemplo());
        BigDecimal valorArredondadoEsperado = new BigDecimal(234.57).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal receita = ResumoDeTransacoes.getTotal();
        assertEquals(valorArredondadoEsperado, receita);
    }

    @Test
    public void retornaTotalDeReceita() {
        Resumo resumoDeTransacoes = new Resumo(getTransacoesDeExemplo());
        BigDecimal valorArredondadoEsperado = new BigDecimal(472.26).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal receita = resumoDeTransacoes.getReceita();
        assertEquals(valorArredondadoEsperado, receita);
    }

    @Test
    public void retornaTotalDeGasto() {
        Resumo resumoDeTransacoes = new Resumo(getTransacoesDeExemplo());
        BigDecimal despesa = resumoDeTransacoes.getDespesa();
        BigDecimal valorArredondadoEsperado = new BigDecimal(237.69).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        assertEquals(valorArredondadoEsperado, despesa);
    }

    @NonNull
    private List<Transacao> getTransacoesDeExemplo() {
        return new ArrayList<>(Arrays.asList(
                new Transacao(new BigDecimal(120.0), DESPESA),
                new Transacao(new BigDecimal(20.0), RECEITA),
                new Transacao(new BigDecimal(30.21), DESPESA),
                new Transacao(new BigDecimal(44.54), RECEITA),
                new Transacao(new BigDecimal(55.46), DESPESA),
                new Transacao(new BigDecimal(78.32), RECEITA),
                new Transacao(new BigDecimal(12.02), DESPESA),
                new Transacao(new BigDecimal(129.40), RECEITA),
                new Transacao(new BigDecimal(20.0), DESPESA),
                new Transacao(new BigDecimal(200.0), RECEITA)
        ));
    }

}