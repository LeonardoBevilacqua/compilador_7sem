package br.compilador.com;

public class Compilador {

	public static void main(String[] args) {
		if(args.length != 1) {
			ErrorHandler errorH = ErrorHandler.getInstance();
			errorH.registraErro("Arquivo invalido ou não existente");
			errorH.geraRelatorio();
			return;
		}
		String filename = args[0];
		Sintatico sint = new Sintatico(filename);
		sint.processa();

	}

}
