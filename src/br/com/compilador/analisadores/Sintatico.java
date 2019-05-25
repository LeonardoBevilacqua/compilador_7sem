/**
* Leonardo Almeida Bevilacqua 1510033187
* Marcelo Costa 1510030281
* Marcio Macedo 1510029701
* Thiago Oliveira 1510028818
*/
package br.com.compilador.analisadores;

import br.com.compilador.TabSimbolos;
import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;
import br.com.compilador.utils.Error;
import br.com.compilador.utils.ErrorHandler;
import br.com.compilador.utils.ErrorType;

public class Sintatico implements Isintatico
{

	private Lexico lexico;
	private TabSimbolos tabelaSimbolos;
	private ErrorHandler errorH;
	private Token token = null;

	public Sintatico(String filename) throws Exception
	{
		lexico = new Lexico(filename);
		tabelaSimbolos = TabSimbolos.getInstance();
		errorH = ErrorHandler.getInstance();
	}

	/**
	 * Metodo responsavel por iniciar o processo de analise sintatica realizando a
	 * leitura de tokens e exibindo-os.
	 */
	public void processar()
	{
		/*
		 * Token tokenLido;
		 * 
		 * System.out.println("------------------------------------------------");
		 * System.out.println("( X , Y )|   Token    ||          Lexema       |");
		 * System.out.println("------------------------------------------------"); do {
		 * 
		 * tokenLido = lexico.nextToken(); tokenLido.print();
		 * 
		 * } while (tokenLido.getTokenType() != TokenType.EOF);
		 * System.out.println("------------------------------------------------");
		 * tabelaSimbolos.printReport(); errorH.gerarRelatorio();
		 */
		derivaDecl();
	}

	@Override
	public void derivaS()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaCmds()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaCmd()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaDecl()
	{
		token = lexico.nextToken();
		if (!token.getTokenType().equals(TokenType.DECLARE))
		{
			gerarError(ErrorType.SINTATICO, token);
		}
		else
		{
			token = lexico.nextToken();
		}

		if (!token.getTokenType().equals(TokenType.ID))
		{
			gerarError(ErrorType.SINTATICO, token);
		}
		else
		{
			//implementar essa logica
			/*if (token.foiDeclarado())
			{
				gerarError(ErrorType.SINTATICO, token);
				//this.gravaErro(TipoDeErro.SEMANTICO, token, Mensagens.MSG_VARIAVEL_REDECLARADA, false);
			}*/

			token = lexico.nextToken();
			//token.setFoiDeclarado(true);
		}

		if (!token.getTokenType().equals(TokenType.TYPE))
		{
			gerarError(ErrorType.SINTATICO, token);
		}
		else
		{
			token = lexico.nextToken();
		}

		if (!token.getTokenType().equals(TokenType.TERM))
		{
			gerarError(ErrorType.SINTATICO, token);
		}
	}

	@Override
	public void derivaCond()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaCndb()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaAtrib()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaExp()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFID()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFOPNum()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFEXPNum_1()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFNUMInt()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFOPNum1()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFEXPNum_2()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFNumFloat()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFOPNum2()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFEXPNum_3()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFLPar()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFEXPNum()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFRPar()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFID_1()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaFValLog()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaXEXPNum()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaOPnum()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaVal()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaRep()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaRepf()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaEXPNum()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaREPW()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaExplo()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void derivaBloco()
	{
		// TODO Auto-generated method stub

	}

	private void gerarError(ErrorType erroType, Token token)
	{
		Error error = new Error(erroType, token.getLexema(), token.getLinha(), token.getColuna());
		errorH.registrarErro(error);
	}
}
