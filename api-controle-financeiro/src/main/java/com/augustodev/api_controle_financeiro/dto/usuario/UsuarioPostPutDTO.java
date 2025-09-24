package com.augustodev.api_controle_financeiro.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioPostPutDTO {
    private String nome;
    private String email;
    private String senha;
}
