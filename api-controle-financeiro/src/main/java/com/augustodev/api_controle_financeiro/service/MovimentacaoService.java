package com.augustodev.api_controle_financeiro.service;

import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoGetDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoPostDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoSalvaDTO;
import com.augustodev.api_controle_financeiro.models.Conta;
import com.augustodev.api_controle_financeiro.models.Movimentacao;
import com.augustodev.api_controle_financeiro.models.TipoMovimentacao;
import com.augustodev.api_controle_financeiro.repository.ContaRepository;
import com.augustodev.api_controle_financeiro.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ContaRepository contaRepository;

    public MovimentacaoSalvaDTO criar(MovimentacaoPostDTO dados) {
        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setNome(dados.getNome());
        movimentacao.setCategoria(dados.getCategoria());
        movimentacao.setValor(dados.getValor());
        movimentacao.setTipo(dados.getTipo());

        Conta conta = contaRepository.findById(dados.getConta_id())
                .orElseThrow(() -> new RuntimeException("Não foi possível encontrar a conta"));

        if (dados.getTipo().equals(TipoMovimentacao.ENTRADA)) {
            BigDecimal saldoAtual = conta.getSaldo();
            BigDecimal valorAdcionado = dados.getValor();

            BigDecimal valorAtualizado = saldoAtual.add(valorAdcionado);

            conta.setSaldo(valorAtualizado);
        } else {
            BigDecimal saldoAtual = conta.getSaldo();
            BigDecimal valorRetirado = dados.getValor();

            BigDecimal valorAtualizado;

            if (saldoAtual.compareTo(valorRetirado) >= 0) {
                valorAtualizado = saldoAtual.subtract(valorRetirado);
            } else {
                throw new RuntimeException("Não é possível retirar um valor maior do que você possuí.");
            }

            conta.setSaldo(valorAtualizado);
        }

        movimentacao.setConta(conta);

        Movimentacao salvo = movimentacaoRepository.save(movimentacao);

        return new MovimentacaoSalvaDTO(salvo.getNome(), salvo.getCategoria(), salvo.getValor(), salvo.getTipo());
    }

    public List<MovimentacaoGetDTO> buscarTodas(UUID id) {
        Conta buscarConta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi possível encontrar a conta."));
        List<Movimentacao> buscaMovimentacoes = buscarConta.getMovimentacao();
        List<MovimentacaoGetDTO> movimentacoes = new ArrayList<>();

        for (Movimentacao movimentacao : buscaMovimentacoes) {
            MovimentacaoGetDTO novo = new MovimentacaoGetDTO();

            novo.setId(movimentacao.getId());
            novo.setNome(movimentacao.getNome());
            novo.setCategoria(movimentacao.getCategoria());
            novo.setValor(movimentacao.getValor());
            novo.setTipo(movimentacao.getTipo());
            novo.setDataHoraMovimentacao(movimentacao.getDataHora());

            movimentacoes.add(novo);
        }

        return movimentacoes;
    }

}
