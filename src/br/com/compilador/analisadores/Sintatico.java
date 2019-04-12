/**
* Leonardo Almeida Bevilacqua 1510033187
* Marcelo Costa 1510030281
* Marcio Macedo 1510029701
* Thiago Oliveira 1510028818
*/
package br.com.compilador.analisadores;

import br.com.compilador.TabSimbolos;
import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;
import br.com.compilador.utils.ErrorHandler;

public class Sintatico {

	private Lexico lexico;
	private TabSimbolos tabelaSimbolos;
	private ErrorHandler errorH;

	public Sintatico(String filename) throws Exception {
		lexico = new Lexico(filename);
		tabelaSimbolos = TabSimbolos.getInstance();
		errorH = ErrorHandler.getInstance();
	}
	
	/**
	* Metodo responsavel por iniciar o processo de analise sintatica
	* realizando a leitura de tokens e exibindo-os. 
	*/
	public void processar() {
		Token tokenLido;
		
		System.out.println("------------------------------------------------");
		System.out.println("( X , Y )|   Token    ||          Lexema       |");
		System.out.println("------------------------------------------------");
		do {

			tokenLido = lexico.nextToken();
			tokenLido.print();

		} while (tokenLido.getTokenType() != TokenType.EOF);
		System.out.println("------------------------------------------------");
		tabelaSimbolos.printReport();
		errorH.gerarRelatorioLexico();
	}

}
