package com.augustodev.api_controle_financeiro.dto.conta;

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
public class ContaGetDTO {
    private UUID id;
    private BigDecimal saldo;
    private String titular;
}
