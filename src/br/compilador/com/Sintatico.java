package br.compilador.com;

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
		do {
			t = lex.nextToken();
			t.print();
			
		} while (t.getTokenType() != TokenType.EOF);
		tab.printReport();
		errorH.geraRelatorio();
		
	}

}
