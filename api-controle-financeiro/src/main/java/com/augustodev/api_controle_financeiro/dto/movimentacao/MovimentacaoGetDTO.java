package com.augustodev.api_controle_financeiro.dto.movimentacao;

import com.augustodev.api_controle_financeiro.models.TipoMovimentacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoGetDTO {
    private UUID id;
    private String nome;
    private String categoria;
    private BigDecimal valor;
    private TipoMovimentacao tipo;
    private LocalDateTime dataHoraMovimentacao;
}
