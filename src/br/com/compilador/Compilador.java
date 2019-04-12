package br.com.compilador;

import br.com.compilador.analisadores.Sintatico;
import br.com.compilador.utils.ArquivoNaoEncontradoException;

public class Compilador {

	public static void main(String[] args) throws Exception {

		if(args.length != 1) {
			throw new ArquivoNaoEncontradoException("Arquivo nao foi passado como parametro");
		}
		String filename = args[0];
		Sintatico sintatico = new Sintatico(filename);
		sintatico.processar();

	}

}
