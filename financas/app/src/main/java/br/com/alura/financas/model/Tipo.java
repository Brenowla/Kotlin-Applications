package br.com.alura.financas.model;

public enum Tipo {
    RECEITA {
        @Override
        public String toString() {
            return "Receita";
        }
    }, DESPESA {
        @Override
        public String toString() {
            return "Despesa";
        }
    }
}
