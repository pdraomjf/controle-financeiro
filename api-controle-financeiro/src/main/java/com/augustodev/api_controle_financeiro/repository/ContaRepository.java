package com.augustodev.api_controle_financeiro.repository;

import com.augustodev.api_controle_financeiro.models.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
}
