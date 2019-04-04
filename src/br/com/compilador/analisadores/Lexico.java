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
		String lexema = "";
		int state = 0;
		
		try {
			// elimina brancos
			while(true) {
				c = fl.getNextChar();
				if(Character.isWhitespace(c)) {
					continue;
				}
				
				/* ESSA SEQUENCIA DE IFs PODE SER TRANFORMADA EM UMA FUNÇAO, QUE
				 * SERA RESPONSAVEL POR VERIFICAR MAQUINAS SIMPLES DE UM CARACTER
				 */
				if(c == '+' || c == '-') {
					return new Token(TokenType.ARIT_AS, Character.toString(c), fl.getLine(), fl.getColumn());
				}else if(c == '*' || c == '/') {
					return new Token(TokenType.ARIT_MD, Character.toString(c), fl.getLine(), fl.getColumn());
				}else if(c == ';') {
					return new Token(TokenType.TERM, Character.toString(c), fl.getLine(), fl.getColumn());
				}else if(c == '(') {
					return new Token(TokenType.L_PAR, Character.toString(c), fl.getLine(), fl.getColumn());
				}else if(c == ')') {
					return new Token(TokenType.R_PAR, Character.toString(c), fl.getLine(), fl.getColumn());
				}
				
				if(c == '$')
				{
					lexema += c;
					c = fl.getNextChar();
					if( c == 'l' | c == 'g' )
					{
						lexema += c;
						c = fl.getNextChar();
						if(c == 't' | c == 'e')
						{
							lexema += c;
							return new Token(TokenType.RELOP, lexema, fl.getLine(), fl.getColumn());
						}
						else
						{
							lexema += c;
							errorH.registraErro("lexema errado :"+ lexema);
							lexema = "";
						}
					}
					else if(c == 'e')
					{
						lexema += c;
						c = fl.getNextChar();
						if(c == 'q')
						{
							lexema += c;
							return new Token(TokenType.RELOP, lexema, fl.getLine(), fl.getColumn());
						}
						else
						{
							lexema += c;
							errorH.registraErro("lexema errado :"+ lexema);
							lexema = "";
						}
					}
					else if(c == 'd')
					{
						lexema += c;
						c = fl.getNextChar();
						if(c == 'f')
						{
							lexema += c;
							return new Token(TokenType.RELOP, lexema, fl.getLine(), fl.getColumn());
							
						}
						else
						{
							lexema += c;
							errorH.registraErro("lexema errado :"+ lexema);
							lexema = "";
						}
					}
					else
					{
						lexema += c;
						errorH.registraErro("lexema errado :"+ lexema);
						lexema = "";
					}
					
					
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
	
	private void verifica_relop() {
		
	}


	private Token obter_token() {
		// TODO Auto-generated method stub
		return null;
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
