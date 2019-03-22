package br.compilador.com;

import java.awt.List;

public class ErrorHandler {

	private static ErrorHandler instance = new ErrorHandler();
	private List erros;
	
	private ErrorHandler() {
		erros = new List();
	}
	
	public void registraErro() {
		
	}
	
	public void geraRelatorio() {
		
	}
	
	public static ErrorHandler getInstance() {
		return instance;
	}
}
