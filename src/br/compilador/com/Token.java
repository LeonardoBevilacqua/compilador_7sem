package br.compilador.com;


public class Token {
	private TokenType tokenType; // tipo de token 
	private String lexema; // cadeia de caracteres do token
	private int linha; // linha em que o token ocorre
	private int coluna; // coluna do 1o caractere do token



	public Token(TokenType tokenType, String lexema) {
		super();
		this.tokenType = tokenType;
		this.lexema = lexema;
	}

	public Token(TokenType tokenType, String lexema, int lin, int col) {
		super();
		this.tokenType = tokenType;
		this.lexema = lexema;
		this.linha = linha;
		this.coluna = coluna;
	}

	public void print() {
		System.out.println(tokenType.toString());
		
	}

	public TokenType getTokenType() {
		return this.tokenType;
	}

	public String getLexema() {
		return this.lexema;
	}

	
	
}
