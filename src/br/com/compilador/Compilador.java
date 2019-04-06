package br.com.compilador;

import br.com.compilador.analisadores.Sintatico;
import br.com.compilador.utils.ErrorHandler;

public class Compilador {

	public static void main(String[] args) {
		if(args.length != 1) {
			ErrorHandler errorH = ErrorHandler.getInstance();
			errorH.registrarErroGenerico("Arquivo invalido ou não existente");
			errorH.gerarRelatorioGenerico();
			return;
		}
		String filename = args[0];
		Sintatico sintatico = new Sintatico(filename);
		sintatico.processar();

	}

}
