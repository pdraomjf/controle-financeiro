package com.augustodev.api_controle_financeiro.exception;

public class MovimentacaoNaoEncontradaException extends RuntimeException {
    public MovimentacaoNaoEncontradaException(String message) {
        super(message);
    }
}
