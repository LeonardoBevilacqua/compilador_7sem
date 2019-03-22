package br.compilador.com;

import java.io.FileNotFoundException;

public class Lexico {
	
	private FileLoader fl;
	
	public Lexico(String filename) {
		try {
			fl = new FileLoader(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void nextToken() {
		
	}

}
