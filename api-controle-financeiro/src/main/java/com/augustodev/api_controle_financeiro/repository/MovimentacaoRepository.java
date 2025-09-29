package com.augustodev.api_controle_financeiro.repository;

import com.augustodev.api_controle_financeiro.models.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, UUID> {
}
