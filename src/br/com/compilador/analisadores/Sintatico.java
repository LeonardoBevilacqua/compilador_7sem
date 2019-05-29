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
		boolean debug = false;
		if(debug) {
			debugLexico();
		}else {
			derivaS();
		}		
		
		errorH.gerarRelatorio();
	}

	private void debugLexico() 
	{
		Token tokenLido;
		 
		System.out.println("------------------------------------------------");
		System.out.println("( X , Y )|   Token    ||          Lexema       |");
		System.out.println("------------------------------------------------"); 
		do  { tokenLido = lexico.nextToken(); tokenLido.print(); } 
		while (tokenLido.getTokenType() != TokenType.EOF);
		System.out.println("------------------------------------------------");
		tabelaSimbolos.printReport(); 
		errorH.gerarRelatorio();
	}
	
	private void nextToken() {
		token = lexico.nextToken();
	}

	@Override
	public void derivaS()
	{
		nextToken();
		if(!token.getTokenType().equals(TokenType.PROGRAM)) 
		{
			gerarError();
		}
		
		nextToken();
		// ID
		if(!token.getTokenType().equals(TokenType.ID))
		{
			gerarError();
		}
		
		nextToken();
		// term
		if(!token.getTokenType().equals(TokenType.TERM))
		{
			gerarError();
		}
		
		nextToken();
		// Deriva BLOCO
		derivaBloco();
	
		nextToken();		
		// end_prog
		if(!token.getTokenType().equals(TokenType.END_PROG))
		{
			gerarError();
		}
		
		nextToken();
		// term
		if(!token.getTokenType().equals(TokenType.TERM))
		{
			gerarError();
		}

		nextToken();
		// EOF
		if(!token.getTokenType().equals(TokenType.EOF)) {
			gerarError();
		}

	}

	@Override
	public void derivaCmds()
	{		
		// Deriva DECL
		if(token.getTokenType().equals(TokenType.DECLARE))
		{
			derivaDecl();
		}
		// Deriva COND
		else if(token.getTokenType().equals(TokenType.IF))
		{
			derivaCond();
		}
		// Deriva REP
		else if(token.getTokenType().equals(TokenType.FOR) || token.getTokenType().equals(TokenType.WHILE))
		{
			
		}
		// Deriva ATRIB
		else if(token.getTokenType().equals(TokenType.ID))
		{
			derivaAtrib();
		}
		
		// $
		if(token.getTokenType().equals(TokenType.EOF))
		{
			gerarError();
		}
		// end		
		else if(!token.getTokenType().equals(TokenType.END)) 
		{
			nextToken();
			derivaCmds();
		}		
		lexico.saveBuffer(token);
	}

	@Override
	public void derivaCmd()
	{
		// Deriva DECL
		if(token.getTokenType().equals(TokenType.DECLARE))
		{
			derivaDecl();
		}
		// Deriva COND
		else if(token.getTokenType().equals(TokenType.IF)) 
		{
			derivaCond();
		}
		// Deriva REP
		else if(token.getTokenType().equals(TokenType.FOR) || token.getTokenType().equals(TokenType.WHILE))
		{

		}
		// Deriva ATRIB
		else if(token.getTokenType().equals(TokenType.ID)) 
		{
			derivaAtrib();
		}
		else 
		{
			gerarError();
		}
	}

	@Override
	public void derivaDecl()
	{
		// declare
		if (!token.getTokenType().equals(TokenType.DECLARE))
		{
			gerarError();
		}

		nextToken();
		// ID
		if (!token.getTokenType().equals(TokenType.ID))
		{
			gerarError();
		}
		else
		{
			//TODO: implementar essa logica
			/*if (token.foiDeclarado())
			{
				gerarError();
				//this.gravaErro(TipoDeErro.SEMANTICO, token, Mensagens.MSG_VARIAVEL_REDECLARADA, false);
			}*/
		}

		nextToken();
		// type
		if (!token.getTokenType().equals(TokenType.TYPE))
		{
			gerarError();
		}

		nextToken();
		// term
		if (!token.getTokenType().equals(TokenType.TERM))
		{
			gerarError();
		}
	}

	@Override
	public void derivaCond()
	{
		// if
		if(!token.getTokenType().equals(TokenType.IF))
		{
			gerarError();
		}
		
		nextToken();
		// l_par
		if(!token.getTokenType().equals(TokenType.L_PAR))
		{
			gerarError();
		}
		
		nextToken();
		// Deriva EXPLO
		derivaExplo();
		
		nextToken();
		// r_par
		if(!token.getTokenType().equals(TokenType.R_PAR))
		{
			gerarError();
		}
		
		nextToken();
		// then
		if(!token.getTokenType().equals(TokenType.THEN))
		{
			gerarError();
		}
		
		nextToken();
		// Deriva BLOCO
		derivaBloco();
		
		nextToken();
		// Deriva CNDB
		derivaCndb();		
	}	

	@Override
	public void derivaCndb()
	{
		if(!token.getTokenType().equals(TokenType.ELSE))
		{
			lexico.saveBuffer(token);
		}
		else
		{
			token = lexico.nextToken();
			// Deriva BLOCO
			derivaBloco();
			
		}
	}

	@Override
	public void derivaAtrib()
	{
		// id
		if(!token.getTokenType().equals(TokenType.ID))
		{
			gerarError();
		}
		else
		{
			// TODO: verificar se existe
		}
		
		nextToken();
		// assign
		if(!token.getTokenType().equals(TokenType.ASSIGN))
		{
			gerarError();
		}
		
		nextToken();
		// Deriva Exp
		derivaExp();

		nextToken();
		// term
		if(!token.getTokenType().equals(TokenType.TERM))
		{
			gerarError();
		}	
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
		// begin
		if(token.getTokenType().equals(TokenType.BEGIN))
		{
			nextToken();			
			// Deriva CMDS
			derivaCmds();
			
			nextToken();
			// end
			if(!token.getTokenType().equals(TokenType.END))
			{
				gerarError();
			}
		}
		else
		{
			// Deriva CMD
			derivaCmd();
		}		
	}

	private void gerarError()
	{
		Error error = new Error(ErrorType.SINTATICO, token.getLexema(), token.getLinha(), token.getColuna());
		errorH.registrarErro(error);
		nextToken();
	}
}
