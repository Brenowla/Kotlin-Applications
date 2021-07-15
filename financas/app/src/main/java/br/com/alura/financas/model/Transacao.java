package br.com.alura.financas.model;

import java.math.BigDecimal;
import java.util.Calendar;

import br.com.alura.financas.util.DataUtil;

import static br.com.alura.financas.model.Tipo.RECEITA;

public class Transacao {

    public static final String INDEFINIDA = "Indefinida";
    private final BigDecimal valor;
    private final Tipo tipo;
    private Calendar data;
    private String categoria;

    public Transacao(BigDecimal valor, Tipo tipo) {
        this.valor = valor;
        this.tipo = tipo;
        this.data = Calendar.getInstance();
        categoria = INDEFINIDA;
    }

    public Transacao(BigDecimal valor, Tipo tipo, String categoria) {
        this(valor, tipo);
        this.categoria = categoria;
    }

    public Transacao(BigDecimal valor, Tipo tipo, Calendar data) {
        this(valor, tipo);
        this.data = data;
    }

    public Transacao(BigDecimal valor, Tipo tipo, Calendar data, String categoria) {
        this(valor, tipo, data);
        this.categoria = categoria;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getValorFormatado() {
        return getValor().setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public String getDataFormatada() {
        return DataUtil.formataParaBrasileiro(data);
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public Calendar getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        if (valor != null ? !valor.equals(transacao.valor) : transacao.valor != null) return false;
        return tipo == transacao.tipo;

    }

    @Override
    public int hashCode() {
        int result = valor != null ? valor.hashCode() : 0;
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "valor=" + valor +
                ", tipo=" + tipo +
                ", data=" + data +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
