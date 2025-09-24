package com.augustodev.api_controle_financeiro.controller;

import com.augustodev.api_controle_financeiro.dto.usuario.UsuarioGetDTO;
import com.augustodev.api_controle_financeiro.dto.usuario.UsuarioPostPutDTO;
import com.augustodev.api_controle_financeiro.models.TipoUsuario;
import com.augustodev.api_controle_financeiro.models.Usuario;
import com.augustodev.api_controle_financeiro.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<String> criarUsuario(@RequestBody UsuarioPostPutDTO request) {
        Usuario usuario = new Usuario();

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(bCryptPasswordEncoder.encode(request.getSenha()));
        usuario.setTipoUsuario(TipoUsuario.USUARIO);
        usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUsuario(@RequestBody UsuarioPostPutDTO request, @PathVariable UUID id) {
        Optional<Usuario> buscaUsuario = usuarioRepository.findById(id);

        buscaUsuario.get().setNome(request.getNome());
        buscaUsuario.get().setEmail(request.getEmail());
        buscaUsuario.get().setSenha(bCryptPasswordEncoder.encode(request.getSenha()));

        usuarioRepository.save(buscaUsuario.get());

        return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso.");
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioGetDTO>> listarUsuarios() {
        List<Usuario> busca = usuarioRepository.findAll();
        List<UsuarioGetDTO> listaUsuarios = new ArrayList<>();

        if (busca.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            for (Usuario usuario : busca) {
                UsuarioGetDTO novoUser = new UsuarioGetDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getDataCadastro());
                listaUsuarios.add(novoUser);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(listaUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetDTO> buscarUsuario(@PathVariable UUID id) {
        Optional<Usuario> busca = usuarioRepository.findById(id);
        UsuarioGetDTO usuario = new UsuarioGetDTO();

        if (busca.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            usuario.setId(busca.get().getId());
            usuario.setNome(busca.get().getNome());
            usuario.setEmail(busca.get().getEmail());
            usuario.setDataCadastro(busca.get().getDataCadastro());
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário deletado com sucesso.");
    }
}
