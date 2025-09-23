package com.augustodev.api_controle_financeiro.controller;

import com.augustodev.api_controle_financeiro.models.TipoUsuario;
import com.augustodev.api_controle_financeiro.models.Usuario;
import com.augustodev.api_controle_financeiro.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
        usuario.setTipo(TipoUsuario.USUARIO);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable UUID id) {
        Optional<Usuario> buscaUsuario = usuarioRepository.findById(id);

        if (buscaUsuario.isPresent()) {
            if (!usuario.getNome().isEmpty()) buscaUsuario.get().setNome(usuario.getNome());
            if (!usuario.getEmail().isEmpty()) buscaUsuario.get().setEmail(usuario.getEmail());
            if (!usuario.getSenha().isEmpty()) {
                buscaUsuario.get().setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
            }

            usuarioRepository.save(buscaUsuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(buscaUsuario.get());
    }

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (!usuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
