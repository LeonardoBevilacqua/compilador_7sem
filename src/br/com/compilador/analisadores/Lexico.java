package br.com.compilador.analisadores;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

import br.com.compilador.TabSimbolos;
import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;
import br.com.compilador.utils.ErrorHandler;
import br.com.compilador.utils.FileLoader;

public class Lexico {

	private FileLoader fl;
	private ErrorHandler errorH;

	private StringBuilder lexema = new StringBuilder();
	private TokenType tokenType = null;
	private char c = ' ';
	private long coluna_inicial;

	public Lexico(String filename) {
		errorH = ErrorHandler.getInstance();
		try {
			fl = new FileLoader(filename);
		} catch (FileNotFoundException e) {
			errorH.registraErro(e.getMessage());
		}
	}

	public Token nextToken() {
		lexema = new StringBuilder();
		c = ' ';
		tokenType = null;
		coluna_inicial = 1;

		try {
			
			do {
				c = fl.getNextChar();
				if(lexema == "") {
					coluna_inicial = fl.getColumn();
				}

				if (Character.isDigit(c)) {
					verifica_int_float();
					return obter_token();
					
				} else if (verifica_caracteres_simples()) {
					return obter_token();
				}

			} while (Character.isWhitespace(c));
		} catch (IOException e) {
			if (lexema != "") {
				try {
					fl.resetLastChar();
				} catch (IOException e1) {
					// erro
				}
				return obter_token();
			}
			return new Token(TokenType.EOF, "", fl.getLine(), fl.getColumn());
		}

		return null;
	}

	/**
	 * This function verifies what type of number will get
	 * and generate the save the token
	 * @throws EOFException
	 * @throws IOException
	 */
	private void verifica_int_float() throws EOFException, IOException {
		tokenType = TokenType.NUM_INT;

		do {			
			lexema += c;			
			c = fl.getNextChar();			
		} while (Character.isDigit(c));
		
		if (c == '.') {
			verifica_float();
		} else if (c == 'E' || c == 'e') {
			verifica_notacao();
		}
		

		fl.resetLastChar();
	}
	
	private void verifica_float() throws EOFException, IOException {
		tokenType = TokenType.NUM_FLOAT;
		do {			
			lexema += c;
			c = fl.getNextChar();
		} while (Character.isDigit(c));		
		
		if (c == 'E' || c == 'e') {
			verifica_notacao();
		}		
	}

	private void verifica_notacao() throws EOFException, IOException {
		do {
			lexema += c;
			c = fl.getNextChar();
		} while (Character.isDigit(c) || (c == '+' || c == '-'));
	}
	
	private boolean verifica_caracteres_simples() {
		if (c == '+' || c == '-') {
			tokenType = TokenType.ARIT_AS;
		} else if (c == '*' || c == '/') {
			tokenType = TokenType.ARIT_MD;
		} else if (c == ';') {
			tokenType = TokenType.TERM;
		} else if (c == '(') {
			tokenType = TokenType.L_PAR;
		} else if (c == ')') {
			tokenType = TokenType.R_PAR;
		} else {
			return false;
		}

		lexema += c;
		return true;
	}

	

	private void verifica_relop() {

	}

	private Token obter_token() {
		// reavaliar linha e coluna
		return new Token(tokenType, lexema, fl.getLine(), coluna_inicial);
	}

	private void instalarId() {
		// TODO Auto-generated method stub

	}

	private void retrair(int i) {
		// TODO Auto-generated method stub

	}

	/**
	 * Em caso de falha, deve retornar o estado para iniciar a analise da proxima
	 * maquina
	 * 
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
