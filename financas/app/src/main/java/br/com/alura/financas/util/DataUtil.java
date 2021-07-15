package br.com.alura.financas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by alex on 18/08/17.
 */

public class DataUtil {

    public static final SimpleDateFormat FORMATO_BRASILEIRO = new SimpleDateFormat("dd/MM/yyyy");

    public static String formataParaBrasileiro(Calendar calendar) {
        return FORMATO_BRASILEIRO.format(calendar.getTime());
    }

    public static Calendar converte(String dataEmTexto) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date date = FORMATO_BRASILEIRO.parse(dataEmTexto);
        calendar.setTime(date);
        return calendar;
    }

}
