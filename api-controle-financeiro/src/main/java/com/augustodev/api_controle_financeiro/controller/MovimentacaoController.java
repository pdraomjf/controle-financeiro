package com.augustodev.api_controle_financeiro.controller;

import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoGetDTO;
import com.augustodev.api_controle_financeiro.dto.movimentacao.MovimentacaoPostDTO;
import com.augustodev.api_controle_financeiro.models.Conta;
import com.augustodev.api_controle_financeiro.models.Movimentacao;
import com.augustodev.api_controle_financeiro.models.TipoMovimentacao;
import com.augustodev.api_controle_financeiro.repository.ContaRepository;
import com.augustodev.api_controle_financeiro.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/movimentacao")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ContaRepository contaRepository;

    @PostMapping("/")
    public ResponseEntity<String> criarMovimentacao(@RequestBody MovimentacaoPostDTO request) {
        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setNome(request.getNome());
        movimentacao.setCategoria(request.getCategoria());
        movimentacao.setValor(request.getValor());
        movimentacao.setTipo(request.getTipo());

        Optional<Conta> conta = contaRepository.findById(request.getConta_id());

        if (conta.isPresent()) {
            if (request.getTipo().equals(TipoMovimentacao.ENTRADA)) {
                BigDecimal saldoAtual = conta.get().getSaldo();
                BigDecimal valorAdcionado = request.getValor();

                BigDecimal valorAtualizado = saldoAtual.add(valorAdcionado);

                conta.get().setSaldo(valorAtualizado);
            } else {
                BigDecimal saldoAtual = conta.get().getSaldo();
                BigDecimal valorRetirado = request.getValor();
                BigDecimal valorAtualizado = new BigDecimal(0);

                if (saldoAtual.compareTo(valorRetirado) >= 0) {
                    valorAtualizado = saldoAtual.subtract(valorRetirado);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possível retirar um valor maior do que você possuí.");
                }

                conta.get().setSaldo(valorAtualizado);
            }

            movimentacao.setConta(conta.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não fornecida");
        }

        movimentacaoRepository.save(movimentacao);

        return ResponseEntity.status(HttpStatus.CREATED).body("Movimentacao cadastrada com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MovimentacaoGetDTO>> buscarMovimentacoes(@PathVariable UUID id) {
        Optional<Conta> buscaConta = contaRepository.findById(id);
        List<Movimentacao> buscaMovimentacoes = buscaConta.get().getMovimentacao();
        List<MovimentacaoGetDTO> movimentacoes = new ArrayList<>();

        for (Movimentacao movimentacao : buscaMovimentacoes) {
            MovimentacaoGetDTO salvar = new MovimentacaoGetDTO();
            salvar.setId(movimentacao.getId());
            salvar.setNome(movimentacao.getNome());
            salvar.setCategoria(movimentacao.getCategoria());
            salvar.setValor(movimentacao.getValor());
            salvar.setTipo(movimentacao.getTipo());
            salvar.setDataHoraMovimentacao(movimentacao.getDataHora());

            movimentacoes.add(salvar);
        }

        return ResponseEntity.status(HttpStatus.OK).body(movimentacoes);
    }
}
