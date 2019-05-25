package br.com.compilador.utils;

public class Error
{
	private ErrorType errorType;
	private String lexema;
	private long linha;
	private long coluna;
	
	public Error(ErrorType errorType, String lexema, long linha, long coluna)
	{
		super();
		this.errorType = errorType;
		this.lexema = lexema;
		this.linha = linha;
		this.coluna = coluna;
	}

	@Override
	public String toString()
	{
		return "Error [errorType=" + errorType + ", lexema=" + lexema + ", linha=" + linha + ", coluna=" + coluna + "]";
	}

	public ErrorType getErrorType() 				{ return errorType; }
	public void setErrorType(ErrorType errorType) 	{ this.errorType = errorType; }

	public String getLexema() 						{ return lexema; }
	public void setLexema(String lexema) 			{ this.lexema = lexema; }

	public long getLinha() 							{ return linha; }
	public void setLinha(int linha) 				{ this.linha = linha; }

	public long getColuna() 						{ return coluna; }
	public void setColuna(int coluna) 				{ this.coluna = coluna; }
}
