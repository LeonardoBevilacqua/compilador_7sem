package br.com.compilador.analisadores;

import java.io.FileNotFoundException;
import java.io.IOException;

import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;
import br.com.compilador.utils.ErrorHandler;
import br.com.compilador.utils.FileLoader;

public class Lexico {
	
	private FileLoader fl;
	private ErrorHandler errorH;
	private String lexema = "";
	private TokenType tokenType = null;
	
	public Lexico(String filename) {
		errorH = ErrorHandler.getInstance();
		try {
			fl = new FileLoader(filename);
		} catch (FileNotFoundException e) {
			errorH.registraErro(e.getMessage());
		} 
	}
	
	public Token nextToken() {
		char c = ' ';		
		int state = 0;
		
		lexema = "";
		tokenType = null;
		
		try {
			// elimina brancos
			while(true) {
				c = fl.getNextChar();
				if(Character.isWhitespace(c)) {
					continue;
				}
				
				if(verifica_caracteres_simples(c)) {
					return obter_token();
				}				
	
				
				switch (state) {
				case 0:
					if(c == '<') {
						state = 1;
					}else if(c == '=') {
						state = 5;
					}else {
						state = 6;
					}
					break;
				// ...
				case 9:
					c = fl.getNextChar();
					if(Character.isLetter(c)) {
						state = 10;
					}else {
						state = falhar(9);
					}
					break;
				case 10:
					c = fl.getNextChar();
					if(Character.isLetter(c) || Character.isDigit(c)) {
						state = 10;
					}else {
						state = 11;
					}
					break;
				case 11:
					retrair(1);	
					instalarId();
					return obter_token();
				default:
					break;
				}
			}
		} catch (IOException e) {
			//errorH.registraErro(e.getMessage());
			return new Token(TokenType.EOF, lexema, fl.getLine(), fl.getColumn());
		}
	}
	
	private boolean verifica_caracteres_simples(char c) {	
		if(c == '+' || c == '-') {
			tokenType = TokenType.ARIT_AS;
		}else if(c == '*' || c == '/') {
			tokenType = TokenType.ARIT_MD;
		}else if(c == ';') {
			tokenType = TokenType.TERM;
		}else if(c == '(') {
			tokenType = TokenType.L_PAR;
		}else if(c == ')') {
			tokenType = TokenType.R_PAR;		
		}else {
			return false;
		}
		
		lexema += c;
		return true;
	}
	
	private void verifica_relop() {
		
	}


	private Token obter_token() {
		return new Token(tokenType, lexema, fl.getLine(), fl.getColumn());
	}

	private void instalarId() {
		// TODO Auto-generated method stub
		
	}

	private void retrair(int i) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Em caso de falha, deve retornar o estado para iniciar a analise da proxima maquina
	 * @param partida
	 * @return O estado
	 */
	private int falhar(int partida) { 
		long pt_adiante = fl.getColumn();
		switch (partida) {
		case 12:
			partida = 20; 
			break;

		default:
			// erro lexico
			break;
		}
		return partida;
	}

}
