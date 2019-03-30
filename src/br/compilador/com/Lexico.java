package br.compilador.com;

import java.io.FileNotFoundException;
import java.io.IOException;

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
		int state = 0;
		
		try {
			// elimina brancos
			while(true) {
				switch (state) {
				case 0:
					c = fl.getNextChar();
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
			errorH.registraErro(e.getMessage());
		}
		
		
		return null;
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
