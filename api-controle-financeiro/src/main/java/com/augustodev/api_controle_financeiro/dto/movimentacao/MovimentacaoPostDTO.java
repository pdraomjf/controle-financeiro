package com.augustodev.api_controle_financeiro.dto.movimentacao;

import com.augustodev.api_controle_financeiro.models.TipoMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoPostDTO {
    private String nome;
    private String categoria;
    private BigDecimal valor;
    private TipoMovimentacao tipo;
    private UUID conta_id;
}
