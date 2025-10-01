package com.augustodev.api_controle_financeiro.dto.movimentacao;

import com.augustodev.api_controle_financeiro.models.TipoMovimentacao;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoPostDTO {
    @NotBlank
    private String nome;

    @NotBlank
    private String categoria;

    @Digits(integer = 15, fraction = 2, message = "Valor deve ter no máximo 15 dígitos inteiros e 2 casas decimais")
    private BigDecimal valor;

    @NotBlank(message = "Tipo de movimentação obrigatório, ENTRADA ou SAIDA.")
    private TipoMovimentacao tipo;

    private UUID conta_id;
}
