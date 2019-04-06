package br.com.compilador;

import br.com.compilador.analisadores.Sintatico;
import br.com.compilador.utils.ErrorHandler;

public class Compilador {

	public static void main(String[] args) {

		Sintatico sintatico = new Sintatico("file.txt");
		sintatico.processar();

	}

}
