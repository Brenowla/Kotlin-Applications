package br.com.alura.financas.ui.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;

import br.com.alura.financas.R;
import br.com.alura.financas.delegate.TransacaoDelegate;
import br.com.alura.financas.model.Tipo;
import br.com.alura.financas.model.Transacao;
import br.com.alura.financas.util.DataUtil;
import br.com.alura.financas.util.MoedaUtil;

/**
 * Created by alex on 16/08/17.
 */

abstract class FormularioTransacaoDialog {

    private final View viewCriada;
    protected final EditText valor;
    protected final Spinner categoria;
    protected final EditText data;
    protected Context context;

    FormularioTransacaoDialog(Context context, ViewGroup viewRoot) {
        this.context = context;
        this.viewCriada = LayoutInflater.from(context).inflate(R.layout.form_transacao, viewRoot, false);
        this.valor = viewCriada.findViewById(R.id.form_transacao_valor);
        this.categoria = viewCriada.findViewById(R.id.form_transacao_categoria);
        this.data = viewCriada.findViewById(R.id.form_transacao_data);
        adicionaCalendario();
    }

    protected void mostraDialog(final Tipo tipo,
                                final TransacaoDelegate delegate,
                                String titulo,
                                String tituloPositivo,
                                String tituloNegativo) {
        configuraSpinner(tipo);
        new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton(tituloPositivo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Transacao transacaoDevolvida = devolvePor(tipo);
                        delegate.executa(transacaoDevolvida);
                    }
                })
                .setNegativeButton(tituloNegativo, null).show();

    }

    private void configuraSpinner(Tipo tipo) {
        int categorias = devolveCategoria(tipo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                categorias, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adapter);
    }

    private int devolveCategoria(Tipo tipo) {
        int categorias = R.array.categorias_de_despesa;
        if (tipo.equals(Tipo.RECEITA)) {
            categorias = R.array.categorias_de_receita;
        }
        return categorias;
    }

    private Transacao devolvePor(Tipo tipo) {
        String categoria = this.categoria.getSelectedItem().toString();
        String valor = this.valor.getText().toString();
        String data = this.data.getText().toString();
        Calendar calendar = devolveData(data);

        BigDecimal valorReal = MoedaUtil.validaMoeda(valor);
        return new Transacao(valorReal, tipo, calendar, categoria);
    }

    private Calendar devolveData(String data) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar = DataUtil.converte(data);
        } catch (ParseException e) {
            Toast.makeText(context, "Falha ao inserir data especificada", Toast.LENGTH_SHORT);
            e.printStackTrace();
            return calendar;
        }
        return calendar;
    }

    private void adicionaCalendario() {
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaDatePicker(Calendar.getInstance());
            }
        });
    }

    private void chamaDatePicker(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(context, configuraDatePicker(), year, month, day).show();
    }

    @NonNull
    private DatePickerDialog.OnDateSetListener configuraDatePicker() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(ano, mes, dia);
                String formatoBrasileiro = DataUtil.formataParaBrasileiro(calendar);
                data.setText(formatoBrasileiro);
            }
        };
    }
}
