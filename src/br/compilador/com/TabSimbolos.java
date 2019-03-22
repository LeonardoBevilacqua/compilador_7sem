package br.compilador.com;

import java.util.HashMap;
import java.util.Map;

public class TabSimbolos {

	private static TabSimbolos instance = new TabSimbolos();
	private Map<String, Token> tab;
	
	private TabSimbolos() {
		tab = new HashMap<String, Token>();
		
	}
	
	public static TabSimbolos getInstance() {
		return instance;
	}
	
	public void instalaToken(Token token) {
		tab.put(token.getLexema(), token);
	}
	
	public void printReport() {
		
	}
}
