/**
* Leonardo Almeida Bevilacqua 1510033187
* Marcelo Costa 1510030281
* Marcio Macedo 1510029701
* Thiago Oliveira 1510028818
*/
package br.com.compilador.utils;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

	private static ErrorHandler instance = new ErrorHandler();
	private List<String> errosLexicos = new ArrayList<String>();
	
	/**
	* Metodo responsavel por registrar erros lexicos
	*/
	public void registrarErroLexico(ErrorType errorType, String lexema, long linha, long coluna)
	{
		String erroLexico = "Lexema errado: "+lexema+" | Tipo do Erro: "+errorType+" Linha: "+linha+" Coluna: "+coluna;
		errosLexicos.add(erroLexico);
	}
	
	public void gerarRelatorioLexico()
	{
		cabecalhoRelatorioLexico();
		
		for (String erroLexico : errosLexicos)
		{
			System.out.println(erroLexico);
		}
	}
	
	/**
	* Metodo responsavel por imprimir o cabeçalho de erros
	*/
	private void cabecalhoRelatorioLexico()
	{
		System.out.println("\n---------------------------------------");
		System.out.printf("|%25s %12s","Erros","|");
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
