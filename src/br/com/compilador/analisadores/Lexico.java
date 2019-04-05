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
		c = ' ';
		lexema = "";
		tokenType = null;
		coluna_inicial = 1;

		try {
			do {
				c = fl.getNextChar();
				if (lexema == "") {
					coluna_inicial = fl.getColumn();
				}

				if (Character.isDigit(c)) {
					if (verifica_int_float()) {
						break;
					}
				} else if (c == '$') {
					if(verificarRelop()) {
						break;
					}
				} else if (verifica_caracteres_simples()) {
					break;
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
			lexema += c;
			c = fl.getNextChar();
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

	private boolean verifica_float() throws EOFException, IOException {
		tokenType = TokenType.NUM_FLOAT;
		do {
			lexema += c;
			c = fl.getNextChar();
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
			lexema += c;
			c = fl.getNextChar();
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

		lexema += c;
		return true;
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

	private boolean verificarRelop() throws EOFException, IOException {
		lexema += c;
		c = fl.getNextChar();
		if (c == 'l' | c == 'g') {
			lexema += c;
			c = fl.getNextChar();
			if (c == 't' | c == 'e') {
				lexema += c;
				tokenType = TokenType.RELOP;
			} else {
				lexema += c;
				errorH.registraErro("lexema errado :" + lexema);
				lexema = "";
				return false;
			}
		} else if (c == 'e') {
			lexema += c;
			c = fl.getNextChar();
			if (c == 'q') {
				lexema += c;
				tokenType = TokenType.RELOP;
			} else {
				lexema += c;
				errorH.registraErro("lexema errado :" + lexema);
				lexema = "";
				return false;
			}
		} else if (c == 'd') {
			lexema += c;
			c = fl.getNextChar();
			if (c == 'f') {
				lexema += c;
				tokenType = TokenType.RELOP;

			} else {
				lexema += c;
				errorH.registraErro("lexema errado :" + lexema);
				lexema = "";
				return false;
			}
		} else {
			lexema += c;
			errorH.registraErro("lexema errado :" + lexema);
			lexema = "";
			fl.resetLastChar();
			return false;
		}

		return true;
	}

}
