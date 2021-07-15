package br.com.alura.financas.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by alex on 18/08/17.
 */

public class MoedaUtil {

    public static String formataParaBrasileiro(BigDecimal valor) {
        NumberFormat moeda = DecimalFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return moeda.format(valor).replace("R$", "R$ ").replace("-R$ ", "R$ -");
    }

    public static BigDecimal validaMoeda(String valorEmTexto) {
        if (!valorEmTexto.isEmpty()) {
            return new BigDecimal(valorEmTexto);
        }
        return BigDecimal.ZERO;
    }
}
