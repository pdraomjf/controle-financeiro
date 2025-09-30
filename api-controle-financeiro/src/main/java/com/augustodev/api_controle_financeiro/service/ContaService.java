package com.augustodev.api_controle_financeiro.service;

import com.augustodev.api_controle_financeiro.dto.conta.ContaCriadaDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaGetDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaPostDTO;
import com.augustodev.api_controle_financeiro.models.Conta;
import com.augustodev.api_controle_financeiro.models.Usuario;
import com.augustodev.api_controle_financeiro.repository.ContaRepository;
import com.augustodev.api_controle_financeiro.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    public ContaCriadaDTO salvar(ContaPostDTO dados) {
        Usuario buscaUsuario = usuarioRepository.findById(dados.getUsuario_id())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Conta conta = new Conta();
        conta.setUsuario(buscaUsuario);
        conta.setSaldo(dados.getSaldo());

        Conta salvo = contaRepository.save(conta);

        return new ContaCriadaDTO(salvo.getUsuario().getNome(), salvo.getSaldo());
    }

    public ContaGetDTO buscar(UUID id) {
        Conta busca = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta inexistente"));
        ContaGetDTO conta = new ContaGetDTO();

        conta.setId(busca.getId());
        conta.setSaldo(busca.getSaldo());

        return conta;
    }

    public List<ContaGetDTO> buscarTodos() {
        List<Conta> busca = contaRepository.findAll();
        List<ContaGetDTO> contas = new ArrayList<>();

        if (busca.isEmpty()) {
            return contas;
        } else {
            for (Conta conta : busca) {
                ContaGetDTO novo = new ContaGetDTO();

                novo.setId(conta.getId());
                novo.setSaldo(conta.getSaldo());

                contas.add(novo);
            }
        }

        return contas;
    }


}
