package br.com.compilador.analisadores;

import br.com.compilador.TabSimbolos;
import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;
import br.com.compilador.utils.ErrorHandler;

public class Sintatico {

	private Lexico lexico;
	private TabSimbolos tabelaSimbolos;
	private ErrorHandler errorH;

	public Sintatico(String filename) {
		lexico = new Lexico(filename);
		tabelaSimbolos = TabSimbolos.getInstance();
		errorH = ErrorHandler.getInstance();
	}

	public void processar() {
		Token tokenLido;
		// imprime cabeçalho
		System.out.println("(X, Y)	|	Type	|	Lexema");
		System.out.println("---------------------------------------");
		do {
			tokenLido = lexico.nextToken();
			tokenLido.print();

		} while (tokenLido.getTokenType() != TokenType.EOF);
		tabelaSimbolos.printReport();
		errorH.geraRelatorio();

	}

}
