package br.com.compilador.analisadores;

import br.com.compilador.TabSimbolos;
import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;
import br.com.compilador.utils.ErrorHandler;

public class Sintatico {
	
	private Lexico lex;
	private TabSimbolos tab;
	private ErrorHandler errorH;

	public Sintatico(String filename) {
		lex = new Lexico(filename);
		tab = TabSimbolos.getInstance();
		errorH = ErrorHandler.getInstance();
	}

	public void processa() {
		Token t;
		//imprime cabeçalho
		System.out.println("(X, Y)	|	Type	|	Lexema");
		do {
			t = lex.nextToken();
			t.print();
			
		} while (t.getTokenType() != TokenType.EOF);
		tab.printReport();
		errorH.geraRelatorio();
		
	}

}
