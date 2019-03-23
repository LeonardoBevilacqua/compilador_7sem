package br.compilador.com;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

	private static ErrorHandler instance = new ErrorHandler();
	private List<String> erros = new ArrayList<String>();
	
	
	public void registraErro(String erro) {
		erros.add(erro);
	}
	
	public void geraRelatorio() {
		for (String erro : erros) {
			System.out.println(erro);
		}
	}
	
	public static ErrorHandler getInstance() {
		return instance;
	}
}
