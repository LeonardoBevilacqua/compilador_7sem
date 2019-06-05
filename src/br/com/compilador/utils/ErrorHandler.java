/**
* Leonardo Almeida Bevilacqua 1510033187
* Marcelo Costa 1510030281
* Marcio Macedo 1510029701
* Thiago Oliveira 1510028818
*/
package br.com.compilador.utils;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler
{

	private static ErrorHandler instance = new ErrorHandler();
	private List<Error> erros = new ArrayList<Error>();

	/**
	 * Metodo responsavel por registrar erros
	 */
	public void registrarErro(Error error)
	{
		erros.add(error);
	}

	public void gerarRelatorio()
	{
		cabecalhoRelatorioLexico();

		for (Error erro : erros)
		{
			System.out.println(erro.toString());
		}
	}

	/**
	 * Metodo responsavel por imprimir o cabeçalho de erros
	 */
	private void cabecalhoRelatorioLexico()
	{
		System.out.println("\n---------------------------------------");
		System.out.printf("|%25s %12s", "Erros", "|");
		System.out.println("\n---------------------------------------");
	}

	/**
	 * Metodo responsavel por retornar a instancia unica da classe
	 */
	public static ErrorHandler getInstance()
	{
		return instance;
	}
}
