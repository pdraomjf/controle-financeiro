package com.augustodev.api_controle_financeiro.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioGetDTO {
    private UUID id;
    private String nome;
    private String email;
    private LocalDateTime dataCadastro;
}
