package com.example.aluraviagens.dao

import com.example.aluraviagens.model.Pacote
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList


class PacoteDAO {
    fun lista(): List<Pacote> {
        return ArrayList(
            Arrays.asList(
                Pacote("São Paulo", "sao_paulo_sp", 2, BigDecimal(243.99)),
                Pacote("Belo Horizonte", "belo_horizonte_mg", 3, BigDecimal(421.50)),
                Pacote("Recife", "recife_pe", 4, BigDecimal(754.20)),
                Pacote("Rio de Janeiro", "rio_de_janeiro_rj", 6, BigDecimal(532.55)),
                Pacote("Salvador", "salvador_ba", 5, BigDecimal(899.99)),
                Pacote("Foz do Iguaçu", "foz_do_iguacu_pr", 1, BigDecimal(289.90))
            )
        )
    }
}