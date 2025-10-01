package com.augustodev.api_controle_financeiro.controller;

import com.augustodev.api_controle_financeiro.dto.usuario.UsuarioGetDTO;
import com.augustodev.api_controle_financeiro.dto.usuario.UsuarioPostPutDTO;
import com.augustodev.api_controle_financeiro.dto.usuario.UsuarioRespostaDTO;
import com.augustodev.api_controle_financeiro.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<UsuarioRespostaDTO> criarUsuario(@Valid @RequestBody UsuarioPostPutDTO request) {
        UsuarioRespostaDTO usuarioSalvo = usuarioService.salvar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> atualizarUsuario(@ Valid @RequestBody UsuarioPostPutDTO request, @PathVariable UUID id) {
        UsuarioRespostaDTO usuarioAtualizado = usuarioService.atualizar(request, id);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioGetDTO>> listarUsuarios() {
        List<UsuarioGetDTO> lista = usuarioService.buscarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetDTO> buscarUsuario(@PathVariable UUID id) {
        UsuarioGetDTO usuario = usuarioService.buscarPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable UUID id) {
        usuarioService.deletar(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
