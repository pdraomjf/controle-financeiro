package com.augustodev.api_controle_financeiro.controller;

import com.augustodev.api_controle_financeiro.dto.conta.ContaPostDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaGetDTO;
import com.augustodev.api_controle_financeiro.models.Conta;
import com.augustodev.api_controle_financeiro.models.Usuario;
import com.augustodev.api_controle_financeiro.repository.ContaRepository;
import com.augustodev.api_controle_financeiro.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conta")
public class ContaController {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/")
    public ResponseEntity<String> criarConta(@RequestBody ContaPostDTO request) {
        Optional<Usuario> usuario = usuarioRepository.findById(request.getUsuario_id());

        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Conta conta = new Conta();
        conta.setUsuario(usuario.get());
        conta.setSaldo(request.getSaldo());
        contaRepository.save(conta);

        return ResponseEntity.status(HttpStatus.CREATED).body("Conta criada com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaGetDTO> buscarConta(@PathVariable UUID id) {
        Optional<Conta> conta = contaRepository.findById(id);
        ContaGetDTO contaGetDTO = new ContaGetDTO();

        if (conta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            contaGetDTO.setId(conta.get().getId());
            contaGetDTO.setSaldo(conta.get().getSaldo());
        }

        return ResponseEntity.status(HttpStatus.OK).body(contaGetDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<ContaGetDTO>> buscarTodasContas() {
        List<Conta> busca = contaRepository.findAll();
        List<ContaGetDTO> contas = new ArrayList<>();

        if (busca.isEmpty()) {
            List<ContaGetDTO> vazio = new ArrayList<>();
            return ResponseEntity.status(HttpStatus.OK).body(vazio);
        } else {
            for (Conta conta : busca) {
                ContaGetDTO contaGetDTO = new ContaGetDTO();
                contaGetDTO.setId(conta.getId());
                contaGetDTO.setSaldo(conta.getSaldo());
                contas.add(contaGetDTO);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(contas);
    }

}
