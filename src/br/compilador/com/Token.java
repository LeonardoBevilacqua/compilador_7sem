package br.compilador.com;


public class Token {
	private TokenType tokenType; // tipo de token 
	private String lexema; // cadeia de caracteres do token
	private int lin; // linha em que o token ocorre
	private int col; // coluna do 1o caractere do token

	
	public Token(TokenType tokenType, String lexema, int lin, int col) {
		super();
		this.tokenType = tokenType;
		this.lexema = lexema;
		this.lin = lin;
		this.col = col;
	}

	public void print() {
		System.out.println(tokenType.toString());
		
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public String getLexema() {
		return lexema;
	}

	
	
}
