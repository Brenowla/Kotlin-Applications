package br.com.alura.financas.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static br.com.alura.financas.model.Tipo.RECEITA;
import static br.com.alura.financas.model.Tipo.DESPESA;
import static org.junit.Assert.assertEquals;

public class TransacaoTest {

    @Test
    public void criaTransacaoDeDebito() {
        Transacao despesa = new Transacao(new BigDecimal(100.0), DESPESA);
        assertEquals(DESPESA, despesa.getTipo());
    }

    @Test
    public void criaTransacaoDeReceita() {
        Transacao receitas = new Transacao(new BigDecimal(100.0), RECEITA);
        assertEquals(RECEITA, receitas.getTipo());
    }

    @Test
    public void criaTransacaoDeDebitoSemCategoria() {
        Transacao despesa = new Transacao(new BigDecimal(100.0), DESPESA);
        assertEquals("Indefinida", despesa.getCategoria());
    }

    @Test
    public void criaTransacaoDeReceitaSemCategoria() {
        Transacao receita = new Transacao(new BigDecimal(100.0), RECEITA);
        assertEquals("Indefinida", receita.getCategoria());
    }

    @Test
    public void criaTransacaoDeReceitaComCategoria() {
        String categoriaDeExemplo = "Almoço";
        Transacao receita = new Transacao(new BigDecimal(100.0), RECEITA, "Almoço");
        assertEquals(categoriaDeExemplo, receita.getCategoria());
    }

    @Test
    public void criaTransacaoDeDebitoComCategoria() {
        String categoriaDeExemplo = "Almoço";
        Transacao despesa = new Transacao(new BigDecimal(100.0), DESPESA, "Almoço");
        assertEquals(categoriaDeExemplo, despesa.getCategoria());
    }

    @Test
    public void criaTransacaoDeReceitaComData() {
        Calendar data = Calendar.getInstance();
        data.set(2017, 10, 10);
        Transacao receita = new Transacao(new BigDecimal(100.0), RECEITA, data);
        assertEquals(data, receita.getData());
    }

    @Test
    public void criaTransacaoDeReceitaComDataECategoria() {
        Calendar data = Calendar.getInstance();
        data.set(2017, 10, 10);
        String categoria = "Almoço";
        Transacao receita = new Transacao(new BigDecimal(100.0), RECEITA, data, categoria);
        Calendar dataDevolvida = receita.getData();
        String categoriaDevolvida = receita.getCategoria();
        assertEquals(data, dataDevolvida);
        assertEquals(categoria, categoriaDevolvida);
    }


}