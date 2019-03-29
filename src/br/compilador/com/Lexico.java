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
					break;
				// ...
				case 9:
					
				default:
					break;
				}
			}
		} catch (IOException e) {
			errorH.registraErro(e.getMessage());
		}
		
		
		return null;
	}

}
