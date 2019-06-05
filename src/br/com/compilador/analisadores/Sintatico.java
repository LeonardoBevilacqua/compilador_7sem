/**
* Leonardo Almeida Bevilacqua 1510033187
* Marcelo Costa 1510030281
* Marcio Macedo 1510029701
* Thiago Oliveira 1510028818
*/
package br.com.compilador.analisadores;

import br.com.compilador.FirstFollowTables;
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
	private FirstFollowTables firstFollow = FirstFollowTables.getInstance();	

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
		
		if(!firstFollow.getFirst().get("S").contains(TokenType.PROGRAM)) 
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
		nextToken();	
		if(firstFollow.getFirst().get("cmds").contains(token.getTokenType())) 
		{
			switch (token.getTokenType()) {
			case DECLARE:
				// Deriva DECL
				derivaDecl();
				break;
			case IF:
				// Deriva COND
				derivaCond();
				break;
			case FOR:
				// Deriva REPF
				derivaRepf();
				break;
			case WHILE:
				// Deriva REPW
				derivaREPW();
				break;
			case ID:
				// Deriva ATRIB
				derivaAtrib();
				break;
			default:
				gerarError();
				break;
			}			
		}		
		
		// $
		if(token.getTokenType().equals(TokenType.EOF))
		{
			gerarError();
		}
		// end		
		else if(!token.getTokenType().equals(TokenType.END)) 
		{
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
		else if(firstFollow.getFirst().get("rep").contains(token.getTokenType()))
		{
			derivaRep();
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
		
		// Deriva BLOCO
		derivaBloco();
		
		// Deriva CNDB
		derivaCndb();		
	}	

	@Override
	public void derivaCndb()
	{
		nextToken();
		if(!token.getTokenType().equals(TokenType.ELSE))
		{
			lexico.saveBuffer(token);
		}
		else
		{
			// Deriva BLOCO
			derivaBloco();
			
		}
	}

	@Override
	public void derivaAtrib()
	{
		// TODO: verificar se ID existe
		
		nextToken();
		// assign
		if(!token.getTokenType().equals(TokenType.ASSIGN))
		{
			gerarError();
		}
				
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
		nextToken();
		if(firstFollow.getFirst().get("exp").contains(token.getTokenType())) 
		{
			if(!token.getTokenType().equals(TokenType.LITERAL))
			{
				switch (token.getTokenType()) {
				case LOGIC_VAL:
					// Deriva FVALLOG
					derivaFValLog();
					break;
				case ID:
					// Deriva FID
					derivaFID();
					break;
				case NUM_INT:
					// Deriva FNUMINT
					derivaFNUMInt();
					break;
				case NUM_FLOAT:
					// Deriva FNUMFLOAT
					derivaFNumFloat();
					break;
				case L_PAR:
					// Deriva FLPAR
					derivaFLPar();
					break;
				default:
					gerarError();
					break;
				}
			}
		}
		else
		{
			gerarError();
		}
	}

	@Override
	public void derivaFID()
	{
		nextToken();
		if(firstFollow.getFirst().get("fid").contains(token.getTokenType())) {
			if(token.getTokenType().equals(TokenType.LOGIC_OP))
			{
				// Deriva FVALLOG
				derivaFValLog();
			}
			else if(token.getTokenType().equals(TokenType.ARIT_AS) || token.getTokenType().equals(TokenType.ARIT_MD))
			{
				// Deriva OPNUM
				derivaOPnum();
								
				// Deriva FOPNUM
				derivaFOPNum();
			}
		}
		else 
		{
			gerarError();
		}
		

	}

	@Override
	public void derivaFOPNum()
	{
		nextToken();
		if(firstFollow.getFirst().get("fopnum").contains(token.getTokenType())) 
		{
			// Deriva EXPNUM
			derivaEXPNum();
			
			// Deriva FEXPNUM_1
			derivaFEXPNum_1();			
		}
		else
		{
			gerarError();
		}
	}

	@Override
	public void derivaFEXPNum_1()
	{
		nextToken();
		if(firstFollow.getFirst().get("fexpnum1").contains(token.getTokenType())) 
		{
			// Deriva EXPNUM		
			derivaEXPNum();			
		}
	}

	@Override
	public void derivaFNUMInt()
	{
		nextToken();
		if(firstFollow.getFirst().get("opnum").contains(token.getTokenType()))
		{
			// Deriva OPNUM
			derivaOPnum();
			
			// Deriva FOPNUM_1
			derivaFOPNum1();
		}				
	}

	@Override
	public void derivaFOPNum1()
	{
		nextToken();
		if(firstFollow.getFirst().get("fopnum1").contains(token.getTokenType()))
		{
			// Deriva EXPNUM
			derivaEXPNum();
			
			// Deriva FEXPNUM_2
			derivaFEXPNum_2();			
		}
		else
		{
			gerarError();
		}
	}

	@Override
	public void derivaFEXPNum_2()
	{
		nextToken();
		if(firstFollow.getFirst().get("fexpnum2").contains(token.getTokenType()))
		{
			// Deriva EXPNUM
			derivaEXPNum();
		}
	}

	@Override
	public void derivaFNumFloat()
	{
		nextToken();
		if(firstFollow.getFirst().get("opnum").contains(token.getTokenType()))
		{
			// Deriva OPNUM
			derivaOPnum();
			
			// Deriva FOPNUM_2
			derivaFOPNum2();
		}
	}

	@Override
	public void derivaFOPNum2()
	{
		nextToken();
		if(firstFollow.getFirst().get("fopnum2").contains(token.getTokenType()))
		{
			// Deriva EXPNUM
			derivaEXPNum();
			
			// Deriva FEXPNUM_3
			derivaFEXPNum_3();
		}
		else 
		{
			gerarError();
		}
	}

	@Override
	public void derivaFEXPNum_3()
	{
		nextToken();
		if(firstFollow.getFirst().get("fexpnum3").contains(token.getTokenType()))
		{
			// Deriva EXPNUM
			derivaEXPNum();			
		}
	}

	@Override
	public void derivaFLPar()
	{
		// Deriva EXPNUM
		derivaEXPNum();		
		
		// Deriva FEXPNUM
		derivaFEXPNum();
	}

	@Override
	public void derivaFEXPNum()
	{
		nextToken();
		if(firstFollow.getFirst().get("fexpnum").contains(token.getTokenType()))
		{
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
		nextToken();
		if(firstFollow.getFirst().get("frpar").contains(token.getTokenType()))
		{
			// Deriva EXPNUM
			derivaEXPNum();			
		}
	}

	@Override
	public void derivaFID_1()
	{
		nextToken();
		if(firstFollow.getFirst().get("fid1").contains(token.getTokenType()))
		{
			if(firstFollow.getFirst().get("fvallog").contains(token.getTokenType()))
			{
				// Deriva FVALLOG
				derivaFValLog();
			}
			else if(firstFollow.getFirst().get("opnum").contains(token.getTokenType()))
			{
				// Deriva OPNUM
				derivaOPnum();
				
				// Deriva EXPNUM
				derivaEXPNum();
				
				nextToken();
				if(!token.getTokenType().equals(TokenType.RELOP))
				{
					gerarError();
				}
				// Deriva EXPNUM
				derivaEXPNum();
			}
			else if(token.getTokenType().equals(TokenType.RELOP))
			{
				// Deriva EXPNUM
				derivaEXPNum();
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
		nextToken();
		if(firstFollow.getFirst().get("xexpnum").contains(token.getTokenType()))
		{
			// Deriva OPNUM
			derivaOPnum();
			
			// Deriva EXPNUM
			derivaEXPNum();
		}
		else
		{
			lexico.saveBuffer(token);
		}
	}

	@Override
	public void derivaOPnum()
	{
		nextToken();
		if(!firstFollow.getFirst().get("opnum").contains(token.getTokenType()))
		{
			gerarError();
		}
	}

	@Override
	public void derivaVal()
	{
		if(!firstFollow.getFirst().get("val").contains(token.getTokenType()))
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
				
		// Deriva EXPNum
		derivaEXPNum();
		
		nextToken();
		// TO
		if(!token.getTokenType().equals(TokenType.TO))
		{
			gerarError();
		}
		
		// Deriva EXPNum
		derivaEXPNum();
		
		// Deriva BLOCO
		derivaBloco();
	}

	@Override
	public void derivaEXPNum()
	{
		nextToken();
		if(firstFollow.getFirst().get("expnum").contains(token.getTokenType()))
		{
			if(firstFollow.getFirst().get("val").contains(token.getTokenType()))
			{
				// Deriva VAL
				derivaVal();	
				
				// Deriva XEMPNUM
				derivaXEXPNum();
			}
			else if(token.getTokenType().equals(TokenType.L_PAR))
			{
				// Deriva EXPNUM
				derivaEXPNum();
				
				nextToken();
				if(!token.getTokenType().equals(TokenType.R_PAR))
				{
					gerarError();
				}
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
		
		// Deriva BLOCO
		derivaBloco();
	}

	@Override
	public void derivaExplo()
	{
		nextToken();
		if(firstFollow.getFirst().get("explo").contains(token.getTokenType()))
		{
			switch (token.getTokenType()) {
			case LOGIC_VAL:
				// Deriva FVALLOG
				derivaFValLog();
				break;
			case ID:
				// Deriva FID_1
				derivaFID_1();
				break;
			case NUM_INT:
				// Deriva OPNUM
				derivaOPnum();
				
				// Deriva EXPNUM
				derivaEXPNum();
				
				nextToken();
				if(!token.getTokenType().equals(TokenType.RELOP))
				{
					gerarError();
				}
				// Deriva EXPNUM
				derivaEXPNum();
				break;
			case NUM_FLOAT:
				// Deriva OPNUM
				derivaOPnum();
				
				// Deriva EXPNUM
				derivaEXPNum();
				
				nextToken();
				if(!token.getTokenType().equals(TokenType.RELOP))
				{
					gerarError();
				}
				// Deriva EXPNUM
				derivaEXPNum();
				break;
			case L_PAR:
				// Deriva EXPNUM
				derivaEXPNum();
				
				nextToken();
				if(!token.getTokenType().equals(TokenType.R_PAR))
				{			
					gerarError();
				}
				
				nextToken();
				if(!token.getTokenType().equals(TokenType.RELOP))
				{
					gerarError();
				}
				
				// Deriva EXPNUM
				derivaEXPNum();
				break;

			default:
				gerarError();
				break;
			}
		}
	}

	@Override
	public void derivaBloco()
	{
		nextToken();
		if(!firstFollow.getFirst().get("bloco").contains(token.getTokenType())) {
			gerarError();
		}
		
		// begin
		if(token.getTokenType().equals(TokenType.BEGIN))
		{		
			// Deriva CMDS
			derivaCmds();
			
			nextToken();
			// end
			if(!token.getTokenType().equals(TokenType.END))
			{
				gerarError();
			}
		}
		else if(firstFollow.getFirst().get("cmd").contains(token.getTokenType()))
		{
			// Deriva CMD
			derivaCmd();
		}
		else 
		{
			gerarError();
		}
	}

	private void gerarError()
	{
		Error error = new Error(ErrorType.SINTATICO, token.getLexema(), token.getLinha(), token.getColuna());
		errorH.registrarErro(error);
		nextToken();
	}
}
