/**
* Leonardo Almeida Bevilacqua 1510033187
* Marcelo Costa 1510030281
* Marcio Macedo 1510029701
* Thiago Oliveira 1510028818
*/
package br.com.compilador.utils;

/**
* Responsavel por exibir uma mensagem em caso de erro de leitura do arquivo
*/
public class ArquivoNaoEncontradoException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ArquivoNaoEncontradoException(String message) {  super(message); }
}
