package com.augustodev.api_controle_financeiro.dto.movimentacao;

import com.augustodev.api_controle_financeiro.models.TipoMovimentacao;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoPutDTO {
    @NotNull(message = "O ID da movimentação deve ser fornecido.")
    private UUID id;

    @NotBlank
    private String nome;

    @NotBlank
    private String categoria;

    @Digits(integer = 15, fraction = 2, message = "Valor deve ter no máximo 15 dígitos inteiros e 2 casas decimais")
    private BigDecimal valor;

    @NotNull(message = "Tipo de movimentação obrigatório, ENTRADA ou SAIDA.")
    private TipoMovimentacao tipo;
}
