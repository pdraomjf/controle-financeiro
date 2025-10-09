package com.augustodev.api_controle_financeiro.dto.conta;

import jakarta.validation.constraints.Digits;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaPostDTO {
    @Digits(integer = 15, fraction = 2, message = "Valor deve ter no máximo 15 dígitos inteiros e 2 casas decimais")
    private BigDecimal saldo;
}
