package com.augustodev.api_controle_financeiro.service;

import com.augustodev.api_controle_financeiro.dto.login.LoginRequest;
import com.augustodev.api_controle_financeiro.dto.login.LoginResponse;
import com.augustodev.api_controle_financeiro.models.Usuario;
import com.augustodev.api_controle_financeiro.repository.UsuarioRepository;
import com.augustodev.api_controle_financeiro.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest dados) {
        Usuario usuario = usuarioRepository.findByEmail(dados.getEmail());

        if (!passwordEncoder.matches(dados.getSenha(), usuario.getSenha())) {
            return new LoginResponse("not_match");
        }

        String token = jwtUtil.generateToken(usuario.getId(), usuario.getTipoUsuario().name());

        return new LoginResponse(token);
    }

}
