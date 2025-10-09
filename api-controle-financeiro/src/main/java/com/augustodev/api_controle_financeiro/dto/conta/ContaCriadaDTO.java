package com.augustodev.api_controle_financeiro.dto.conta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaCriadaDTO {
    private String nome;
    private BigDecimal saldo;
}
