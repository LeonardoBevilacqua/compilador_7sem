package br.com.compilador.analisadores;

import java.io.EOFException;
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

		lexema = "";
		tokenType = null;

		try {
			do {
				c = fl.getNextChar();
			} while (Character.isWhitespace(c));			
		} catch (IOException e) {			
			return new Token(TokenType.EOF, "", fl.getLine(), fl.getColumn());
		}
		
		if (Character.isDigit(c)) {
			verifica_float_int(c);
		}else if (verifica_caracteres_simples(c)) {
			return obter_token();
		}
		
		return null;
	}

	private boolean verifica_caracteres_simples(char c) {
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

	private boolean verifica_float_int(char c) {
		if (!Character.isDigit(c) && c != 'E' && c != '.') {
			return false;
		}

		if (tokenType == null) {
			tokenType = TokenType.NUM_INT;
		}

		if (c == '.') {
			tokenType = TokenType.NUM_FLOAT;
		}
		lexema += c;

		return true;
	}

	private void verifica_relop() {

	}

	private Token obter_token() {
		// reavaliar linha e coluna
		return new Token(tokenType, lexema, fl.getLine(), fl.getColumn());
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
