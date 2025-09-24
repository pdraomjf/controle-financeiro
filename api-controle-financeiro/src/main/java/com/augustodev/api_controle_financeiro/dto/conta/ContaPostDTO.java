package com.augustodev.api_controle_financeiro.dto.conta;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaPostDTO {
    private UUID usuario_id;
    private BigDecimal saldo;
}
