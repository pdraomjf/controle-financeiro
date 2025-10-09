package com.augustodev.api_controle_financeiro.dto.conta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChecaSaldoDTO {
    private BigDecimal saldo;
}
