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
		derivaS();
		errorH.gerarRelatorio();
	}

	@Override
	public void derivaS()
	{
		// program
		token = lexico.nextToken();
		if(!token.getTokenType().equals(TokenType.PROGRAM)) 
		{
			gerarError(ErrorType.SINTATICO, token);
		}
		else
		{
			token = lexico.nextToken();			
		}
		
		// ID
		if(!token.getTokenType().equals(TokenType.ID))
		{
			gerarError(ErrorType.SINTATICO, token);
		}
		else
		{
			token = lexico.nextToken();
		}
		
		// term
		if(!token.getTokenType().equals(TokenType.TERM))
		{
			gerarError(ErrorType.SINTATICO, token);
		}
		else
		{
			token = lexico.nextToken();
		}
		
		// Deriva BLOCO
		if(!derivaBloco())
		{
			gerarError(ErrorType.SINTATICO, token);
		}
		else
		{
			token = lexico.nextToken();
		}
		
		// end_prog
		if(!token.getTokenType().equals(TokenType.END_PROG))
		{
			gerarError(ErrorType.SINTATICO, token);
		}
		else
		{
			token = lexico.nextToken();
		}
		
		// term
		if(!token.getTokenType().equals(TokenType.TERM))
		{
			gerarError(ErrorType.SINTATICO, token);
		}
		else
		{
			token = lexico.nextToken();
		}

	}

	@Override
	public boolean derivaCmds()
	{		
		// Deriva DECL
		if(token.getTokenType().equals(TokenType.DECLARE))
		{
			if(!derivaDecl()) 
			{
				return false;
			}
		}
		// Deriva COND
		else if(token.getTokenType().equals(TokenType.IF))
		{
			if(!derivaCond())
			{
				return false;
			}
		}
		// Deriva REP
		else if(token.getTokenType().equals(TokenType.FOR) || token.getTokenType().equals(TokenType.WHILE))
		{
			
		}
		// Deriva ATRIB
		else if(token.getTokenType().equals(TokenType.ID))
		{
			
		}
		// $		
		if(!token.getTokenType().equals(TokenType.END)) 
		{
			token = lexico.nextToken();
			if(!derivaCmds())
			{
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean derivaCmd()
	{
		// Deriva DECL
		if(token.getTokenType().equals(TokenType.DECLARE))
		{
			if(!derivaDecl())
			{
				return false;
			}
		}
		// Deriva COND
		else if(token.getTokenType().equals(TokenType.IF)) 
		{
			if(!derivaCond())
			{
				return false;
			}
		}
		// Deriva REP
		else if(token.getTokenType().equals(TokenType.FOR) || token.getTokenType().equals(TokenType.WHILE))
		{

		}
		// Deriva ATRIB
		else if(token.getTokenType().equals(TokenType.ID)) 
		{

		}
		else 
		{
			return false;
		}
		
		return true;

	}

	@Override
	public boolean derivaDecl()
	{
		// declare
		if (!token.getTokenType().equals(TokenType.DECLARE))
		{
			return false;
		}
		else
		{
			token = lexico.nextToken();
		}

		// ID
		if (!token.getTokenType().equals(TokenType.ID))
		{
			return false;
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

		// type
		if (!token.getTokenType().equals(TokenType.TYPE))
		{
			return false;
		}
		else
		{
			token = lexico.nextToken();
		}

		// term
		if (!token.getTokenType().equals(TokenType.TERM))
		{
			return false;
		}
		
		return true;
	}

	@Override
	public boolean derivaCond()
	{
		// if
		if(!token.getTokenType().equals(TokenType.IF))
		{
			return false;
		}
		// TODO: finalizar
		else if(!derivaBloco())
		{
			return false;
		}
		else if(!derivaCndb())
		{
			return false;
		}
		

		return true;
	}

	@Override
	public boolean derivaCndb()
	{
		// else
		if(!token.getTokenType().equals(TokenType.ELSE))
		{
			return false;
		}
		// Verifica follow
		else 
		{
			Token bufferToken = token;
			token = lexico.nextToken();

		}
		return true;
	}

	@Override
	public boolean derivaAtrib()
	{
		// id
		if(!token.getTokenType().equals(TokenType.ID))
		{
			return false;
		}
		else
		{
			// TODO: verificar se existe
		}
		
		// assign
		token = lexico.nextToken();
		if(!token.getTokenType().equals(TokenType.ASSIGN))
		{
			return false;
		}
		// Deriva EXP
		token = lexico.nextToken();
		if(!derivaExp())
		{
			return false;
		}
		// term
		token = lexico.nextToken();
		if(!token.getTokenType().equals(TokenType.TERM))
		{
			return false;
		}
		
		return true;

	}

	@Override
	public boolean derivaExp()
	{
		// TODO Auto-generated method stub
		return true;

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
	public boolean derivaBloco()
	{
		// begin
		if(token.getTokenType().equals(TokenType.BEGIN))
		{
			token = lexico.nextToken();
			
			// Deriva CMDS
			if(!derivaCmds())
			{
				return false;
			}
			
			// end
			if(!token.getTokenType().equals(TokenType.END))
			{
				return false;
			}
		}
		// Deriva CMD
		else
		{
			if(!derivaCmd())
			{
				return false;
			}
		}
				
		
		return true;
	}

	private void gerarError(ErrorType erroType, Token token)
	{
		Error error = new Error(erroType, token.getLexema(), token.getLinha(), token.getColuna());
		errorH.registrarErro(error);
	}
}
