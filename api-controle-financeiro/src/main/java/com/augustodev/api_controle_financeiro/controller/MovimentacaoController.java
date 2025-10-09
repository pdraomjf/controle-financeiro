package com.augustodev.api_controle_financeiro.controller;

import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoGetDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoPostDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoPutDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoSalvaAtualizadaDTO;
import com.augustodev.api_controle_financeiro.service.MovimentacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimentacao")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @PostMapping("/")
    public ResponseEntity<MovimentacaoSalvaAtualizadaDTO> criarMovimentacao(@Valid @RequestBody MovimentacaoPostDTO request) {
        MovimentacaoSalvaAtualizadaDTO movimentacao = movimentacaoService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
    }

    @GetMapping("/")
    public ResponseEntity<List<MovimentacaoGetDTO>> buscarMovimentacoes() {
        List<MovimentacaoGetDTO> movimentacoes = movimentacaoService.buscarTodas();

        return ResponseEntity.status(HttpStatus.OK).body(movimentacoes);
    }

    @PutMapping("/")
    public ResponseEntity<MovimentacaoSalvaAtualizadaDTO> alterarMovimentacao(@RequestBody MovimentacaoPutDTO request) {
        MovimentacaoSalvaAtualizadaDTO movimentacao = movimentacaoService.alterar(request);

        return ResponseEntity.status(HttpStatus.OK).body(movimentacao);
    }
}
