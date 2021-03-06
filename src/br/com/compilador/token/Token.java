/**
* Leonardo Almeida Bevilacqua 1510033187
* Marcelo Costa 1510030281
* Marcio Macedo 1510029701
* Thiago Oliveira 1510028818
*/
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
	
	/**
	* metodo responsavel por imprimir os dados do token
	*/
	public void print() {
		String formatTokens = "(%3s,%3s)| %-10s || %-21s |\n";
		
		System.out.printf(formatTokens,this.coluna,this.linha,tokenType.toString(), this.lexema);	

	}
	
	/**
	* getters e setters
	*/
	public TokenType getTokenType() {
		return this.tokenType;
	}

	public String getLexema() {
		return this.lexema;
	}

	public long getLinha() {
		return this.linha;
	}

	public long getColuna() {
		return this.coluna;
	}

	public void setLinha(long linha) {
		this.linha = linha;
	}

	public void setColuna(long coluna) {
		this.coluna = coluna;
	}

}
