package com.augustodev.api_controle_financeiro.repository;

import com.augustodev.api_controle_financeiro.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
