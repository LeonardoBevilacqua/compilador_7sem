package br.compilador.com;

public class Compilador {

	public static void main(String[] args) {
		if(args.length != 1) {
			// Excessão
		}
		String filename = args[0];
		Sintatico sint = new Sintatico(filename);
		sint.processa();

	}

}
