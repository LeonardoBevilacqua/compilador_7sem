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
		if(token.getTokenType().equals(TokenType.LOGIC_VAL))
		{
			// Deriva FVALLOG
			derivaFValLog();
		}
		else if(token.getTokenType().equals(TokenType.ID))
		{
			nextToken();
			// Deriva FID
			derivaFID();
		}
		else if(token.getTokenType().equals(TokenType.NUM_INT))
		{
			// Deriva FNUMINT
			derivaFNUMInt();
		}
		else if(token.getTokenType().equals(TokenType.NUM_FLOAT))
		{
			// Deriva FNUMFLOAT
			derivaFNumFloat();
		}
		else if(token.getTokenType().equals(TokenType.L_PAR))
		{
			// Deriva FLPAR
			derivaFLPar();
		}
		else if(!token.getTokenType().equals(TokenType.LITERAL))
		{
			gerarError();
		}
	}

	@Override
	public void derivaFID()
	{
		if(token.getTokenType().equals(TokenType.LOGIC_OP))
		{
			// Deriva FVALLOG
			derivaFValLog();
		}
		else if(token.getTokenType().equals(TokenType.ARIT_AS) | token.getTokenType().equals(TokenType.ARIT_MD))
		{
			// Deriva OPNUM
			derivaOPnum();
			
			nextToken();
			// Deriva FOPNUM
			derivaFOPNum();
		}

	}

	@Override
	public void derivaFOPNum()
	{
		
		// Deriva EXPNUM
		derivaEXPNum();
		
		nextToken();
		if(token.getTokenType().equals(TokenType.RELOP))
		{
			// Deriva FEXPNUM_1
			derivaFEXPNum_1();
		}

	}

	@Override
	public void derivaFEXPNum_1()
	{
		// Deriva EXPNUM		
		derivaEXPNum();
	}

	@Override
	public void derivaFNUMInt()
	{
		if(token.getTokenType().equals(TokenType.ARIT_AS) || token.getTokenType().equals(TokenType.ARIT_MD))
		{
			// Deriva OPNUM
			derivaOPnum();
		}
		else
		{
			gerarError();
		}
		
		if(token.getTokenType().equals(TokenType.L_PAR))
		{
			// Deriva FOPNUM_1
			derivaFOPNum1();
		}
		else
		{
			gerarError();
		}
	}

	@Override
	public void derivaFOPNum1()
	{
		// Deriva EXPNUM
		derivaEXPNum();
		
		nextToken();
		
		if(token.getTokenType().equals(TokenType.RELOP)) 
		{
			// Deriva FEXPNUM_2
			derivaFEXPNum_2();
		}
	}

	@Override
	public void derivaFEXPNum_2()
	{
		// Deriva EXPNUM
		derivaEXPNum();
	}

	@Override
	public void derivaFNumFloat()
	{
		if(token.getTokenType().equals(TokenType.ARIT_AS) || token.getTokenType().equals(TokenType.ARIT_AS))
		{
			// Deriva OPNUM
			derivaOPnum();
			
			nextToken();
			// Deriva FOPNUM_2
			derivaFOPNum2();
		}
	}

	@Override
	public void derivaFOPNum2()
	{
		// Deriva EXPNUM
		derivaExp();
		
		nextToken();
		if(token.getTokenType().equals(TokenType.RELOP))
		{
			// Deriva FEXPNUM_3
			derivaFEXPNum_3();			
		}
	}

	@Override
	public void derivaFEXPNum_3()
	{
		// Deriva EXPNUM
		derivaEXPNum();

	}

	@Override
	public void derivaFLPar()
	{
		// Deriva EXPNUM
		derivaEXPNum();
		
		nextToken();
		
		// Deriva FEXPNUM
		derivaFEXPNum();
	}

	@Override
	public void derivaFEXPNum()
	{
		if(token.getTokenType().equals(TokenType.R_PAR))
		{
			nextToken();
			// Deriva FRPAR
			derivaFRPar();
		}
		else
		{
			gerarError();
		}
	}

	@Override
	public void derivaFRPar()
	{
		if(token.getTokenType().equals(TokenType.RELOP))
		{
			nextToken();
			// Deriva EXPNUM
			derivaEXPNum();
		}
	}

	@Override
	public void derivaFID_1()
	{
		if(token.getTokenType().equals(TokenType.RELOP))
		{
			// Deriva FVALLOG
			derivaFValLog();			
		}
		else if(token.getTokenType().equals(TokenType.RELOP))
		{
			nextToken();
			// Deriva EXPNUM
			derivaEXPNum();
		}		
		else if(token.getTokenType().equals(TokenType.ARIT_AS) || token.getTokenType().equals(TokenType.ARIT_MD))
		{
			nextToken();
			// Deriva OPNUM
			derivaOPnum();
			
			nextToken();
			
			// Deriva EXPNUM
			derivaEXPNum();
			
			nextToken();
			if(token.getTokenType().equals(TokenType.RELOP))
			{
				// Deriva EXPNUM
				derivaEXPNum();
			}
			else
			{
				gerarError();
			}
		}
		else
		{
			gerarError();
		}
	}

	@Override
	public void derivaFValLog()
	{
		nextToken();
		if(token.getTokenType().equals(TokenType.LOGIC_OP))
		{
			// Deriva EXPLO
			derivaExplo();
		}
		else
		{
			lexico.saveBuffer(token);
		}
	}

	@Override
	public void derivaXEXPNum()
	{
		if(token.getTokenType().equals(TokenType.ARIT_AS) ||
				token.getTokenType().equals(TokenType.ARIT_MD))
		{
			// Deriva OPNUM
			derivaOPnum();
			
			nextToken();
			// Deriva EXPNUM
			derivaEXPNum();
		}	
	}

	@Override
	public void derivaOPnum()
	{
		if(!token.getTokenType().equals(TokenType.ARIT_AS) &&
				!token.getTokenType().equals(TokenType.ARIT_MD))
		{
			gerarError();
		}
	}

	@Override
	public void derivaVal()
	{
		if(!token.getTokenType().equals(TokenType.ID) &&
				!token.getTokenType().equals(TokenType.NUM_INT) &&
				!token.getTokenType().equals(TokenType.NUM_FLOAT))
		{
			gerarError();
		}
	}

	@Override
	public void derivaRep()
	{
		if(token.getTokenType().equals(TokenType.FOR))
		{
			// Deriva REPF
			derivaRepf();
		}
		else if(token.getTokenType().equals(TokenType.WHILE)) 
		{
			// Deriva REPW
			derivaREPW();
		}
		else 
		{
			gerarError();
		}

	}

	@Override
	public void derivaRepf()
	{
		// FOR
		if(!token.getTokenType().equals(TokenType.FOR))
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
		// attrib
		if(!token.getTokenType().equals(TokenType.ASSIGN))
		{
			gerarError();
		}
		
		nextToken();
		// Deriva EXPNum
		derivaEXPNum();
		
		nextToken();
		// TO
		if(!token.getTokenType().equals(TokenType.TO))
		{
			gerarError();
		}
		
		nextToken();
		// Deriva EXPNum
		derivaEXPNum();
		
		nextToken();
		// Deriva BLOCO
		derivaBloco();

	}

	@Override
	public void derivaEXPNum()
	{
		// TODO Auto-generated method stub
		if(token.getTokenType().equals(TokenType.ID) |
				token.getTokenType().equals(TokenType.NUM_INT) |
				token.getTokenType().equals(TokenType.NUM_FLOAT))
		{
			// Deriva VAL
			derivaVal();
			
			nextToken();
			// Deriva XEMPNUM
			derivaXEXPNum();
		}
		else if(token.getTokenType().equals(TokenType.L_PAR))
		{
			nextToken();
			// Deriva EXPNUM
			derivaEXPNum();
			
			if(!token.getTokenType().equals(TokenType.R_PAR))
			{
				gerarError();
			}
		}
		else 
		{
			gerarError();
		}

	}

	@Override
	public void derivaREPW()
	{
		// While
		if(!token.getTokenType().equals(TokenType.WHILE))
		{
			gerarError();
		}
		
		nextToken();
		// l_par
		if(!token.getTokenType().equals(TokenType.L_PAR))
		{
			gerarError();
		}
		
		// Deriva Explo
		derivaExplo();
		
		nextToken();
		// r_par
		if(!token.getTokenType().equals(TokenType.R_PAR))
		{
			gerarError();
		}
		
		nextToken();
		// Deriva BLOCO
		derivaBloco();
	}

	@Override
	public void derivaExplo()
	{
		nextToken();
		if(token.getTokenType().equals(TokenType.LOGIC_VAL))
		{
			// Deriva FVALLOG
			derivaFValLog();
		}
		else if(token.getTokenType().equals(TokenType.ID))
		{
			nextToken();
			// Deriva FID_1
			derivaFID_1();
		}
		else if(token.getTokenType().equals(TokenType.NUM_INT) || token.getTokenType().equals(TokenType.NUM_FLOAT))
		{
			nextToken();	
			// Deriva OPNUM
			derivaOPnum();
			
			nextToken();
			// Deriva EXPNUM
			derivaEXPNum();
			
			if(token.getTokenType().equals(TokenType.RELOP))
			{
				nextToken();
				// Deriva EXPNUM
				derivaEXPNum();
			}
			else 
			{
				gerarError();
			}
		}
		else if(token.getTokenType().equals(TokenType.L_PAR))
		{
			nextToken();
			// Deriva EXPNUM
			derivaEXPNum();
			
			if(token.getTokenType().equals(TokenType.R_PAR))
			{
				nextToken();
				if(token.getTokenType().equals(TokenType.RELOP))
				{
					nextToken();
					// Deriva EXPNUM
					derivaEXPNum();
				}
				else
				{
					gerarError();
				}
			}
			else 
			{
				gerarError();
			}
		}
		else 
		{
			gerarError();
		}
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
