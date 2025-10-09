package com.augustodev.api_controle_financeiro.service;

import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoGetDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoPostDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoPutDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoSalvaAtualizadaDTO;
import com.augustodev.api_controle_financeiro.exception.ContaNaoEncontradaException;
import com.augustodev.api_controle_financeiro.exception.MovimentacaoNaoEncontradaException;
import com.augustodev.api_controle_financeiro.exception.UsuarioNaoEncontradoException;
import com.augustodev.api_controle_financeiro.models.Conta;
import com.augustodev.api_controle_financeiro.models.Movimentacao;
import com.augustodev.api_controle_financeiro.models.TipoMovimentacao;
import com.augustodev.api_controle_financeiro.models.Usuario;
import com.augustodev.api_controle_financeiro.repository.ContaRepository;
import com.augustodev.api_controle_financeiro.repository.MovimentacaoRepository;
import com.augustodev.api_controle_financeiro.repository.UsuarioRepository;
import com.augustodev.api_controle_financeiro.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticatedUser authenticatedUser;

    public MovimentacaoSalvaAtualizadaDTO criar(MovimentacaoPostDTO dados) {
        Movimentacao movimentacao = new Movimentacao();

        movimentacao.setNome(dados.getNome());
        movimentacao.setCategoria(dados.getCategoria());
        movimentacao.setValor(dados.getValor());
        movimentacao.setTipo(dados.getTipo());

        Conta conta = retornaConta();

        if (dados.getTipo().equals(TipoMovimentacao.ENTRADA)) {
            BigDecimal saldoAtual = conta.getSaldo();
            BigDecimal valorAdcionado = dados.getValor();

            BigDecimal valorAtualizado = saldoAtual.add(valorAdcionado);

            conta.setSaldo(valorAtualizado);
        } else {
            BigDecimal saldoAtual = conta.getSaldo();
            BigDecimal valorRetirado = dados.getValor();

            BigDecimal valorAtualizado = saldoAtual.subtract(valorRetirado);

            conta.setSaldo(valorAtualizado);
        }

        movimentacao.setConta(conta);

        Movimentacao salvo = movimentacaoRepository.save(movimentacao);

        return new MovimentacaoSalvaAtualizadaDTO(salvo.getNome(), salvo.getCategoria(), salvo.getValor(), salvo.getTipo());
    }

    public List<MovimentacaoGetDTO> buscarTodas() {
        Conta conta = retornaConta();
        List<Movimentacao> buscaMovimentacoes = conta.getMovimentacao();
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

    public MovimentacaoSalvaAtualizadaDTO alterar(MovimentacaoPutDTO dados) {
        Movimentacao movimentacao = movimentacaoRepository.findById(dados.getId())
                .orElseThrow(() -> new MovimentacaoNaoEncontradaException("Não foi possível obter a movimentação."));
        Conta conta = retornaConta();

        BigDecimal saldoAtual = conta.getSaldo();

        if (movimentacao.getTipo().equals(TipoMovimentacao.ENTRADA) && dados.getTipo().equals(TipoMovimentacao.ENTRADA)) {
            BigDecimal reajusteValor = saldoAtual.subtract(movimentacao.getValor());
            BigDecimal valorAtualizado = reajusteValor.add(dados.getValor());

            conta.setSaldo(valorAtualizado);
        } else if (movimentacao.getTipo().equals(TipoMovimentacao.SAIDA) && dados.getTipo().equals(TipoMovimentacao.SAIDA)) {
            BigDecimal reajusteValor = saldoAtual.add(movimentacao.getValor());
            BigDecimal valorAtualizado = reajusteValor.subtract(dados.getValor());

            conta.setSaldo(valorAtualizado);
        } else if (movimentacao.getTipo().equals(TipoMovimentacao.ENTRADA) && dados.getTipo().equals(TipoMovimentacao.SAIDA)) {
            BigDecimal reajusteValor = saldoAtual.subtract(movimentacao.getValor());
            BigDecimal valorAtualizado = reajusteValor.subtract(dados.getValor());

            conta.setSaldo(valorAtualizado);
        } else if (movimentacao.getTipo().equals(TipoMovimentacao.SAIDA) && dados.getTipo().equals(TipoMovimentacao.ENTRADA)) {
            BigDecimal reajusteValor = saldoAtual.add(movimentacao.getValor());
            BigDecimal valorAtualizado = reajusteValor.add(dados.getValor());

            conta.setSaldo(valorAtualizado);
        }

        movimentacao.setConta(conta);
        movimentacao.setValor(dados.getValor());
        movimentacao.setTipo(movimentacao.getTipo());
        movimentacao.setNome(dados.getNome());
        movimentacao.setCategoria(dados.getCategoria());

        Movimentacao atualizada = movimentacaoRepository.save(movimentacao);

        return new MovimentacaoSalvaAtualizadaDTO(atualizada.getNome(), atualizada.getCategoria()
                , atualizada.getValor(), atualizada.getTipo());
    }

    private Conta retornaConta() {
        Usuario user = usuarioRepository.findById(authenticatedUser.getId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Não foi possível encontrar o usuário."));

        return contaRepository.findById(user.getConta().getId())
                .orElseThrow(() -> new ContaNaoEncontradaException("Não foi possível encontrar a conta"));
    }

}
