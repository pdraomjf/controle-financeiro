package com.augustodev.api_controle_financeiro.service;

import com.augustodev.api_controle_financeiro.dto.conta.ChecaSaldoDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaCriadaDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaGetDTO;
import com.augustodev.api_controle_financeiro.dto.conta.ContaPostDTO;
import com.augustodev.api_controle_financeiro.exception.ContaNaoEncontradaException;
import com.augustodev.api_controle_financeiro.exception.UsuarioNaoEncontradoException;
import com.augustodev.api_controle_financeiro.models.Conta;
import com.augustodev.api_controle_financeiro.models.Usuario;
import com.augustodev.api_controle_financeiro.repository.ContaRepository;
import com.augustodev.api_controle_financeiro.repository.UsuarioRepository;
import com.augustodev.api_controle_financeiro.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticatedUser authenticatedUser;

    public ContaCriadaDTO salvar(ContaPostDTO dados) {
        Usuario buscaUsuario = usuarioRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Não foi possível encontrar o usuário com id: " + authenticatedUser.getId()));

        Conta conta = new Conta();
        conta.setUsuario(buscaUsuario);
        conta.setSaldo(dados.getSaldo());

        Conta salvo = contaRepository.save(conta);

        return new ContaCriadaDTO(salvo.getUsuario().getNome(), salvo.getSaldo());
    }

    public ContaGetDTO buscar(UUID id) {
        Conta busca = contaRepository.findById(id)
                .orElseThrow(() -> new ContaNaoEncontradaException("Não foi possível encontrar a conta com id: " + id));
        ContaGetDTO conta = new ContaGetDTO();

        conta.setId(busca.getId());
        conta.setSaldo(busca.getSaldo());
        conta.setTitular(busca.getUsuario().getNome());

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
                novo.setTitular(conta.getUsuario().getNome());

                contas.add(novo);
            }
        }

        return contas;
    }

    public ChecaSaldoDTO checaSaldo() {
        Usuario user = usuarioRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Não foi possível encontrar o usuário."));
        Conta conta = contaRepository.findById(user.getConta().getId())
                .orElseThrow(() -> new ContaNaoEncontradaException("Não foi possível encontrar a conta com id"));

        return new ChecaSaldoDTO(conta.getSaldo());
    }

}
