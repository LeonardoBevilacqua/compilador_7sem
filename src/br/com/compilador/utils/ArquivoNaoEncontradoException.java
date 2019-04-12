package br.com.compilador.utils;

public class ArquivoNaoEncontradoException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ArquivoNaoEncontradoException(String message) {  super(message); }
}
