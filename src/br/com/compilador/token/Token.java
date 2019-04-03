package br.com.compilador.token;

public class Token {
	private TokenType tokenType; // tipo de token
	private String lexema; // cadeia de caracteres do token
	private long linha; // linha em que o token ocorre
	private long coluna; // coluna do 1o caractere do token

	public Token(TokenType tokenType, String lexema) {
		super();
		this.tokenType = tokenType;
		this.lexema = lexema;
	}

	public Token(TokenType tokenType, String lexema, long linha, long coluna) {
		super();
		this.tokenType = tokenType;
		this.lexema = lexema;
		this.linha = linha;
		this.coluna = coluna;
	}

	public void print() {
		System.out.println("("+this.linha+", "+ this.coluna+")	|	" + tokenType.toString() + "	|	" + this.lexema);
		
	}

	public TokenType getTokenType() {
		return this.tokenType;
	}

	public String getLexema() {
		return this.lexema;
	}

}
