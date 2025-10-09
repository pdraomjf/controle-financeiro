package com.augustodev.api_controle_financeiro.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioPostPutDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3)
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "O nome deve conter apenas letras")
    private String nome;

    @Email(message = "Email inválido.")
    private String email;

    @Size(min = 6)
    private String senha;

}
