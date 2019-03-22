package br.compilador.com;

import java.util.HashMap;
import java.util.Map;

public class TabSimbolos {

	private static TabSimbolos instance = new TabSimbolos();
	private Map<String, String> tab;
	
	private TabSimbolos() {
		tab = new HashMap<String, String>();
	}
	
	public static TabSimbolos getInstance() {
		return instance;
	}
	
	public void instalaToken() {
		
	}
	
	public void printReport() {
		
	}
}
