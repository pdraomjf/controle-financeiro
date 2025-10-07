package com.augustodev.api_controle_financeiro.controller;

import com.augustodev.api_controle_financeiro.dto.conta.ChecaSaldoDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaCriadaDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaPostDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaGetDTO;
import com.augustodev.api_controle_financeiro.service.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    @PostMapping("/")
    public ResponseEntity<ContaCriadaDTO> criarConta(@Valid @RequestBody ContaPostDTO request) {
        ContaCriadaDTO conta = contaService.salvar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ContaGetDTO> buscarConta(@PathVariable UUID id) {
        ContaGetDTO conta = contaService.buscar(id);

        return ResponseEntity.status(HttpStatus.OK).body(conta);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<ContaGetDTO>> buscarTodasContas() {
        List<ContaGetDTO> contas = contaService.buscarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(contas);
    }

    @GetMapping("/saldo")
    public ResponseEntity<ChecaSaldoDTO> checarSaldo() {
        ChecaSaldoDTO saldo = contaService.checaSaldo();

        return ResponseEntity.status(HttpStatus.OK).body(saldo);
    }

}
