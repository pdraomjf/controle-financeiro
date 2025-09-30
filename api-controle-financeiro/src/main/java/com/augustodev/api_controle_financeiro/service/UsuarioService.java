package com.augustodev.api_controle_financeiro.service;

import com.augustodev.api_controle_financeiro.dto.usuario.UsuarioGetDTO;
import com.augustodev.api_controle_financeiro.dto.usuario.UsuarioPostPutDTO;
import com.augustodev.api_controle_financeiro.dto.usuario.UsuarioRespostaDTO;
import com.augustodev.api_controle_financeiro.models.TipoUsuario;
import com.augustodev.api_controle_financeiro.models.Usuario;
import com.augustodev.api_controle_financeiro.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioRespostaDTO salvar(UsuarioPostPutDTO dados) {
        Usuario usuario = new Usuario();

        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(bCryptPasswordEncoder.encode(dados.getSenha()));
        usuario.setTipoUsuario(TipoUsuario.USUARIO);

        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioRespostaDTO(salvo.getNome(), salvo.getEmail());
    }

    public UsuarioRespostaDTO atualizar(UsuarioPostPutDTO dados, UUID id) {
        Usuario busca = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi possível encontrar o usuário."));

        busca.setNome(dados.getNome());
        busca.setEmail(dados.getEmail());
        busca.setSenha(bCryptPasswordEncoder.encode(dados.getSenha()));

        Usuario atualizado = usuarioRepository.save(busca);

        System.out.println(atualizado.getNome() + atualizado.getEmail());

        return new UsuarioRespostaDTO(atualizado.getNome(), atualizado.getEmail());
    }

    public List<UsuarioGetDTO> buscarTodos() {
        List<Usuario> busca = usuarioRepository.findAll();
        List<UsuarioGetDTO> usuarios = new ArrayList<>();

        if (busca.isEmpty()) {
            return usuarios;
        } else {
            for (Usuario usuario : busca) {
                UsuarioGetDTO novo = new UsuarioGetDTO(usuario.getId(), usuario.getNome(),
                        usuario.getEmail(), usuario.getDataCadastro());
                usuarios.add(novo);
            }
        }

        return usuarios;
    }

    public UsuarioGetDTO buscarPorId(UUID id) {
        Usuario busca = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi possível encontrar o usuário."));
        UsuarioGetDTO usuario = new UsuarioGetDTO();

        usuario.setId(busca.getId());
        usuario.setNome(busca.getNome());
        usuario.setEmail(busca.getEmail());
        usuario.setDataCadastro(busca.getDataCadastro());

        return usuario;
    }

    public void deletar(UUID id) {
        Usuario busca = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi possível encontrar o usuário."));

        usuarioRepository.delete(busca);
    }

}
