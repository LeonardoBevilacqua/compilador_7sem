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
				if (lexema.length() == 0) {
					coluna_inicial = fl.getColumn();
				}

				if (Character.isDigit(c)) {
					if (verifica_int_float()) {
						break;
					}
				} else if (c == '$') {
					if (verificarRelop()) {
						break;
					}
				} else if (verifica_caracteres_simples()) {
					break;
				}

			} while (Character.isWhitespace(c));
		} catch (IOException e) {
			if (lexema.length() != 0) {
				try {
					fl.resetLastChar();
				} catch (IOException e1) {
					// erro
				}
				return obter_token();
			}
			return new Token(TokenType.EOF, "", fl.getLine(), fl.getColumn());
		}

		return obter_token();
	}

	/**
	 * This function verifies what type of number will get and generate the save the
	 * token
	 * 
	 * @throws EOFException
	 * @throws IOException
	 */
	private boolean verifica_int_float() throws EOFException, IOException {
		tokenType = TokenType.NUM_INT;

		do {
			next_character();
		} while (Character.isDigit(c));

		if (c == '.') {
			if (!verifica_float()) {
				return false;
			}
		} else if (c == 'E' || c == 'e') {
			if (!verifica_notacao()) {
				return false;
			}
		} else if (!Character.isWhitespace(c)) {
			return false;
		}
		fl.resetLastChar();

		return true;
	}

	private void next_character() throws EOFException, IOException {
		lexema.append(c);
		c = fl.getNextChar();
	}

	private boolean verifica_float() throws EOFException, IOException {
		tokenType = TokenType.NUM_FLOAT;
		do {
			next_character();
		} while (Character.isDigit(c));

		if (c == 'E' || c == 'e') {
			verifica_notacao();
		} else if (!Character.isWhitespace(c)) {
			return false;
		}

		return true;
	}

	private boolean verifica_notacao() throws EOFException, IOException {
		do {
			next_character();
		} while (Character.isDigit(c) || (c == '+' || c == '-'));
		if (!Character.isWhitespace(c)) {
			return false;
		}

		return true;
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

		lexema.append(c);
		return true;
	}

	private Token obter_token() {
		return new Token(tokenType, lexema.toString(), fl.getLine(), coluna_inicial);
	}

	private boolean verificarRelop() throws EOFException, IOException {
		next_character();
		if (c == 'l' | c == 'g') {
			next_character();
			if (c == 't' | c == 'e') {
				lexema.append(c);
				tokenType = TokenType.RELOP;
			} else {
				lexema.append(c);
				errorH.registraErro("lexema errado :" + lexema);
				lexema.setLength(0);
				return false;
			}
		} else if (c == 'e') {
			next_character();
			if (c == 'q') {
				lexema.append(c);
				tokenType = TokenType.RELOP;
			} else {
				lexema.append(c);
				errorH.registraErro("lexema errado :" + lexema);
				lexema.setLength(0);
				return false;
			}
		} else if (c == 'd') {
			next_character();
			if (c == 'f') {
				lexema.append(c);
				tokenType = TokenType.RELOP;

			} else {
				lexema.append(c);
				errorH.registraErro("lexema errado :" + lexema);
				lexema.setLength(0);
				return false;
			}
		} else {
			lexema.append(c);
			errorH.registraErro("lexema errado :" + lexema);
			lexema.setLength(0);
			fl.resetLastChar();
			return false;
		}

		return true;
	}

}
