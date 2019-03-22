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
			errorH.registraErro();
		} 
	}
	
	public Token nextToken() {
		char c = ' ';
		try {
			c = fl.getNextChar();
		} catch (IOException e) {
			errorH.registraErro();
		}
		
		// elimina brancos
		switch (c) {
		case '&':
			
			break;		
		}
		return null;
	}

}
