package com.augustodev.api_controle_financeiro.exception;

public class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
