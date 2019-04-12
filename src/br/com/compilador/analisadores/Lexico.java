package br.com.compilador.analisadores;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

import br.com.compilador.TabSimbolos;
import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;
import br.com.compilador.utils.ErrorHandler;
import br.com.compilador.utils.FileLoader;

public class Lexico
{

	private final char ESPACO_EM_BRANCO = ' ';
	private FileLoader fileLoader;
	private ErrorHandler errorHandler;

	private StringBuilder lexema = null;
	private char caracterLido = ' ';
	private long coluna_inicial;
	
	public Lexico(String filename)
	{
		errorHandler = ErrorHandler.getInstance();
		
		try 							{ fileLoader = new FileLoader(filename); }
		catch (FileNotFoundException e) { errorHandler.registrarErroGenerico(e.getMessage()); }
	}
	
	public Token nextToken()
	{
		Token tokenDeRetorno = null;
		caracterLido = ESPACO_EM_BRANCO;
		coluna_inicial = 1;
		
		try
		{
			while (tokenDeRetorno == null)
			{
				lexema = new StringBuilder();
				
				
				caracterLido = fileLoader.getNextChar();
				if (lexema.length() == 0) { coluna_inicial = fileLoader.getColumn(); }
				
				
				if (caracterLido == '#') 											{ ignoraComentario(); }
				else if (Character.isLetter(caracterLido) || caracterLido == '_')	{ tokenDeRetorno = obterID(); }
				else if (Character.isDigit(caracterLido))							{ tokenDeRetorno = obterIntOrFloat(); }
				else if (caracterLido == '$') 										{ tokenDeRetorno = obterRelop(); }
				else if (caracterLido == '"') 										{ tokenDeRetorno = obterLiteral(); } 
				else if (caracterLido == '<') 										{ tokenDeRetorno = obterAssign(); }
				else 																{ tokenDeRetorno = obterCaracterSimples(); }
				
			}
		} 
		catch (Exception e)
		{
			if (lexema.length() != 0)
			{
				try 					{ fileLoader.resetLastChar(); }
				catch (IOException e1)	{/*Todo implementar oque fazer*/ }
			}
			lexema.setLength(0);
			tokenDeRetorno = gerarToken(TokenType.EOF);
		}
		return tokenDeRetorno;
	}
	
	private Token obterID() throws IOException
	{
        Token token = null;
        try
		{
        	addCaractereLexema();
        	while (token == null)
			{
        		
        		caracterLido = fileLoader.getNextChar();
        		if (Character.isLetter(caracterLido) || caracterLido == '_' || Character.isDigit(caracterLido))
                {
        			addCaractereLexema();
                }
        		else
        		{
        			fileLoader.resetLastChar();
                    token = TabSimbolos.getInstance().addToken(lexema.toString(), fileLoader.getLine(), coluna_inicial);
        		}
			}
		}
        catch (Exception e)
		{
        	fileLoader.resetLastChar();
            token = TabSimbolos.getInstance().addToken(lexema.toString(), fileLoader.getLine(), coluna_inicial);
		}
        
        return token;
	}
	
	private Token obterIntOrFloat() throws EOFException, IOException
	{
		try
		{
			do {
				addCaractereLexema();
				caracterLido = fileLoader.getNextChar();
			}while (Character.isDigit(caracterLido));
	
			if (caracterLido == '.') 								{ return obterNumFloat(); }
			else if (caracterLido == 'E' || caracterLido == 'e') 	{ return obterNotacao(TokenType.NUM_INT); }
			else if (!Character.isWhitespace(caracterLido)) 		{ fileLoader.resetLastChar(); }
			else 													{ return gerarToken(TokenType.NUM_INT); }
		}
		catch (IOException e)
		{ return gerarToken(TokenType.NUM_INT); }

		return null;
	}
	
	private Token obterNumFloat() throws EOFException, IOException
	{
		try
		{
			do {
				addCaractereLexema();
				caracterLido = fileLoader.getNextChar();
			} while (Character.isDigit(caracterLido));
	
			if (caracterLido == 'E' || caracterLido == 'e') { return obterNotacao(TokenType.NUM_FLOAT); }
			else if (!Character.isWhitespace(caracterLido))
			{ 
				addCaractereLexema();
				errorHandler.registrarErroLexico(lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
				return null;
			}
			
			return gerarToken(TokenType.NUM_FLOAT);
		}
		catch(EOFException e)
		{
			fileLoader.resetLastChar();
        	errorHandler.registrarErroLexico(lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
		}
		return null;
	}

	private Token obterNotacao(TokenType tokenType) throws EOFException, IOException
	{ 
		addCaractereLexema();
		caracterLido = fileLoader.getNextChar();
		if(!Character.isDigit(caracterLido) && !(caracterLido == '+' || caracterLido == '-')) {
			fileLoader.resetLastChar();
			return null;
		}
		
		do {
			addCaractereLexema();
			caracterLido = fileLoader.getNextChar();
		} while (Character.isDigit(caracterLido) || (caracterLido == '+' || caracterLido == '-'));
		
		if (!Character.isWhitespace(caracterLido)) { 
			fileLoader.resetLastChar();
			return null;
		}
		
		return gerarToken(tokenType);
	}
	
	private Token obterRelop() throws EOFException, IOException
	{
		try
		{
			addCaractereLexema();
			caracterLido = fileLoader.getNextChar();
			if (caracterLido == 'l' | caracterLido == 'g')
			{
				addCaractereLexema();
				caracterLido = fileLoader.getNextChar();
				if (caracterLido == 't' | caracterLido == 'e')
				{
					addCaractereLexema();
					return gerarToken(TokenType.RELOP);
				}
				else
				{
					addCaractereLexema();
					errorHandler.registrarErroLexico(lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
					lexema.setLength(0);
					fileLoader.resetLastChar();
				}
			}
			else if (caracterLido == 'e')
			{
				addCaractereLexema();
				caracterLido = fileLoader.getNextChar();
				if (caracterLido == 'q')
				{
					addCaractereLexema();
					return gerarToken(TokenType.RELOP);
				}
				else
				{
					addCaractereLexema();
					errorHandler.registrarErroLexico(lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
					lexema.setLength(0);
					fileLoader.resetLastChar();
				}
			}
			else if (caracterLido == 'd')
			{
				addCaractereLexema();
				caracterLido = fileLoader.getNextChar();
				if (caracterLido == 'f')
				{
					addCaractereLexema();
					return gerarToken(TokenType.RELOP);
				}
				else
				{
					addCaractereLexema();
					errorHandler.registrarErroLexico(lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
					lexema.setLength(0);
					fileLoader.resetLastChar();
				}
			}
			else
			{
				addCaractereLexema();
				errorHandler.registrarErroLexico(lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
				lexema.setLength(0);
				fileLoader.resetLastChar();
			}
		}
		catch (Exception e)
		{
        	fileLoader.resetLastChar();
        	errorHandler.registrarErroLexico(lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
		}
		return null;
	}
	
	private Token obterLiteral() throws IOException
	{
		Token token = null;
        try
		{
        	addCaractereLexema();
        	while (token == null)
			{
        		caracterLido = fileLoader.getNextChar();
        		addCaractereLexema();
        		if (caracterLido == '"')
                {
        			token = gerarToken(TokenType.LITERAL);
                }
			}
		}
        catch (Exception e)
		{
        	fileLoader.resetLastChar();
            //TODO
		}
        
        return token;
    }
	
	private Token obterAssign() throws IOException
	{
		Token token = null;
	    try
		{
	    	addCaractereLexema();
			caracterLido = fileLoader.getNextChar();
			if (caracterLido == '-')
	        {
				addCaractereLexema();
				token = gerarToken(TokenType.ASSIGN);
	        }
			else
			{
				fileLoader.resetLastChar();
				//Todo erroH
			}
		}
	    catch (Exception e)
		{
	    	fileLoader.resetLastChar();
	        //TODO
		}
	    
	    return token;
	}
	
	private Token obterCaracterSimples() throws IOException
	{
		TokenType tokenType = null;
		addCaractereLexema();
		if (caracterLido == '+' || caracterLido == '-') 		{ tokenType = TokenType.ARIT_AS; }
		else if (caracterLido == '*' || caracterLido == '/') 	{ tokenType = TokenType.ARIT_MD; } 
		else if (caracterLido == ';') 							{ tokenType = TokenType.TERM; } 
		else if (caracterLido == '(') 							{ tokenType = TokenType.L_PAR; } 
		else if (caracterLido == ')') 							{ tokenType = TokenType.R_PAR; }
		
		return (tokenType == null) ? null : gerarToken(tokenType);
	}
	
	private void ignoraComentario() 
	{
		do {
			try {
				caracterLido = fileLoader.getNextChar();
			} catch (EOFException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (caracterLido != '#');
	}
	
	private void addCaractereLexema()
	{
		if(!Character.isWhitespace(caracterLido)) { lexema.append(caracterLido); }
	}

	private Token gerarToken(TokenType tokenType)
	{
		try {
			fileLoader.resetLastChar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Token(tokenType, lexema.toString(), fileLoader.getLine(), coluna_inicial);
	}
}