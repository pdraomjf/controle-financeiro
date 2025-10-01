package com.augustodev.api_controle_financeiro.controller;

import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoGetDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoPostDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoSalvaDTO;
import com.augustodev.api_controle_financeiro.service.MovimentacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movimentacao")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @PostMapping("/")
    public ResponseEntity<MovimentacaoSalvaDTO> criarMovimentacao(@Valid @RequestBody MovimentacaoPostDTO request) {
        MovimentacaoSalvaDTO movimentacao = movimentacaoService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MovimentacaoGetDTO>> buscarMovimentacoes(@PathVariable UUID id) {
        List<MovimentacaoGetDTO> movimentacoes = movimentacaoService.buscarTodas(id);

        return ResponseEntity.status(HttpStatus.OK).body(movimentacoes);
    }
}
