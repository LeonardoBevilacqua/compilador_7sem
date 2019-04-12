package br.com.compilador.utils;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

	private static ErrorHandler instance = new ErrorHandler();
	private List<String> errosLexicos = new ArrayList<String>();
	
	public void registrarErroLexico(ErrorType errorType, String lexema, long linha, long coluna)
	{
		String erroLexico = "Lexema errado: "+lexema+" Tipo do Erro: "+errorType+" Linha: "+linha+" Coluna: "+coluna;
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
	
	private void cabecalhoRelatorioLexico()
	{
		System.out.println("\n---------------------------------------");
		System.out.printf("|%25s %12s","Erros","|");
		System.out.println("\n---------------------------------------");
	}

	public static ErrorHandler getInstance() 
	{
		return instance;
	}
}
