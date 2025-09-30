package com.augustodev.api_controle_financeiro.dto.movimentacao;

import com.augustodev.api_controle_financeiro.models.TipoMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoSalvaDTO {
    private String nome;
    private String categoria;
    private BigDecimal valor;
    private TipoMovimentacao tipo;
}
