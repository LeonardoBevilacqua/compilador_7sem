package br.com.compilador.utils;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

	private static ErrorHandler instance = new ErrorHandler();
	private List<String> errosGenericos = new ArrayList<String>();
	private List<String> errosLexicos = new ArrayList<String>();
	
	public void registrarErroGenerico(String erroGenerico)
	{
		errosGenericos.add(erroGenerico);
	}
	
	public void registrarErroLexico(String lexema, long linha, long coluna)
	{
		String erroLexico = "Lexema errado: "+lexema+" Linha: "+linha+" Coluna: "+coluna;
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
		System.out.printf("|%25s %12s","Erros Lexicos","|");
		System.out.println("\n---------------------------------------");
	}
	
	public void gerarRelatorioGenerico()
	{
		cabecalhoRelatorioGenerico();
		for (String erroGenerico : errosGenericos)
		{
			System.out.println(erroGenerico);
		}
		
	}
	
	private void cabecalhoRelatorioGenerico()
	{
		System.out.println("\n---------------------------------------");
		System.out.printf("|%27s %10s","Erros Genericos","|");
		System.out.println("\n---------------------------------------");
	}
	
	public static ErrorHandler getInstance() 
	{
		return instance;
	}
}
