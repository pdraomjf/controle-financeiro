package com.augustodev.api_controle_financeiro.controller;

import com.augustodev.api_controle_financeiro.dto.conta.ContaCriadaDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaPostDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaGetDTO;
import com.augustodev.api_controle_financeiro.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    @PostMapping("/")
    public ResponseEntity<ContaCriadaDTO> criarConta(@RequestBody ContaPostDTO request) {
        ContaCriadaDTO conta = contaService.salvar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaGetDTO> buscarConta(@PathVariable UUID id) {
        ContaGetDTO conta = contaService.buscar(id);

        return ResponseEntity.status(HttpStatus.OK).body(conta);
    }

    @GetMapping("/")
    public ResponseEntity<List<ContaGetDTO>> buscarTodasContas() {
        List<ContaGetDTO> contas = contaService.buscarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(contas);
    }

}
