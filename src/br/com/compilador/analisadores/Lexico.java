/**
* Leonardo Almeida Bevilacqua 1510033187
* Marcelo Costa 1510030281
* Marcio Macedo 1510029701
* Thiago Oliveira 1510028818
*/
package br.com.compilador.analisadores;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

import br.com.compilador.TabSimbolos;
import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;
import br.com.compilador.utils.ArquivoNaoEncontradoException;
import br.com.compilador.utils.ErrorHandler;
import br.com.compilador.utils.ErrorType;
import br.com.compilador.utils.FileLoader;

public class Lexico
{

	private final char ESPACO_EM_BRANCO = ' ';
	private FileLoader fileLoader;
	private ErrorHandler errorHandler;

	private StringBuilder lexema = null;
	private char caracterLido = ' ';
	private long coluna_inicial;
	
	public Lexico(String filename) throws Exception {
		errorHandler = ErrorHandler.getInstance();
		
		try 							{ fileLoader = new FileLoader(filename); }
		catch (FileNotFoundException e) { throw new ArquivoNaoEncontradoException("Arquivo: "+filename+" nao encontrado"); }
	}
	/**
	* Metodo responsavel por gerar os tokens
	* return Token token lido apos a leitura do arquivo
	*/
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
	
	/**
	* Metodo responsavel por buscar e retornar um ID
	*/
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
	
	/**
	* Metodo responsavel por buscar e retonar um token numerico, podendo
	* ser do tipo inteiro ou real, com ou sem notação cientifica
	*/
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
	
	/**
	* Metodo responsavel de retornar um valor do tipo real,
	* com ou sem notação cientifica
	*/
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
				errorHandler.registrarErroLexico(ErrorType.LEXICO, lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
				return null;
			}
			
			return gerarToken(TokenType.NUM_FLOAT);
		}
		catch(EOFException e)
		{
			fileLoader.resetLastChar();
        	errorHandler.registrarErroLexico(ErrorType.LEXICO, lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
		}
		return null;
	}
	
	/**
	* Metodo responsavel por devolver um numero com notação cientifica
	* @param tokenType parametro que indica de qual tipo numerico ele veio
	*/
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
	
	/**
	* Metodo responsavel para obter e retonar um simbolo relacional
	*/
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
					errorHandler.registrarErroLexico(ErrorType.LEXICO, lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
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
					errorHandler.registrarErroLexico(ErrorType.LEXICO, lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
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
					errorHandler.registrarErroLexico(ErrorType.LEXICO, lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
					lexema.setLength(0);
					fileLoader.resetLastChar();
				}
			}
			else
			{
				addCaractereLexema();
				errorHandler.registrarErroLexico(ErrorType.LEXICO, lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
				lexema.setLength(0);
				fileLoader.resetLastChar();
			}
		}
		catch (Exception e)
		{
        	fileLoader.resetLastChar();
        	errorHandler.registrarErroLexico(ErrorType.LEXICO, lexema.toString(), fileLoader.getLine(), fileLoader.getColumn());
		}
		return null;
	}
	
	/**
	* Metodo responsavel por obter e retonar uma string de caracteres entre aspas
	*/
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
	
	/**
	* Metodo responsavel por devolver um simbolo de atribuição
	*/
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
	
	/**
	* metodo responsavel por verificar simbolos unicos e retornar se devido token
	*/
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
	
	/**
	* metodo responsavel por ignorar o comentario no codigo
	*/
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
	
	/**
	* metodo responsavel por encapsular a atribuição do lexema
	*/
	private void addCaractereLexema()
	{
		if(!Character.isWhitespace(caracterLido)) { lexema.append(caracterLido); }
	}
	
	/**
	* metodo responsavel por encapsular a geração de um token
	*/
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
