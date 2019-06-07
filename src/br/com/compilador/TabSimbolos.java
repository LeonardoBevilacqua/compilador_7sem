/**
* Leonardo Almeida Bevilacqua 1510033187
* Marcelo Costa 1510030281
* Marcio Macedo 1510029701
* Thiago Oliveira 1510028818
*/
package br.com.compilador;

import java.util.HashMap;
import java.util.Map;

import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;

public class TabSimbolos {

	private static TabSimbolos instance = new TabSimbolos();
	private Map<String, Token> tab;
	private Map<String, Boolean> idTokens;
	
	private TabSimbolos() {
		tab = new HashMap<String, Token>();
		idTokens = new HashMap<String, Boolean>();

		Token tokenTrue = new Token(TokenType.LOGIC_VAL, "true");
		Token tokenFalse = new Token(TokenType.LOGIC_VAL, "false");
		Token tokenNot = new Token(TokenType.LOGIC_OP, "not");
		Token tokenAnd = new Token(TokenType.LOGIC_OP, "and");
		Token tokenOr = new Token(TokenType.LOGIC_OP, "or");
		Token tokenBool = new Token(TokenType.TYPE, "bool");
		Token tokenText = new Token(TokenType.TYPE, "text");
		Token tokenInt = new Token(TokenType.TYPE, "int");
		Token tokenFloat = new Token(TokenType.TYPE, "float");
		Token tokenProgram = new Token(TokenType.PROGRAM, "program");
		Token tokenEndProgram = new Token(TokenType.END_PROG, "end_prog");
		Token tokenBegin = new Token(TokenType.BEGIN, "begin");
		Token tokenEnd = new Token(TokenType.END, "end");
		Token tokenIf = new Token(TokenType.IF, "if");
		Token tokenThen = new Token(TokenType.THEN, "then");
		Token tokenElse = new Token(TokenType.ELSE, "else");
		Token tokenFor = new Token(TokenType.FOR, "for");
		Token tokenWhile = new Token(TokenType.WHILE, "while");
		Token tokenDeclare = new Token(TokenType.DECLARE, "declare");
		Token tokenTo = new Token(TokenType.TO, "to");


		tab.put(tokenTrue.getLexema(), tokenTrue);
		tab.put(tokenFalse.getLexema(), tokenFalse);
		tab.put(tokenNot.getLexema(), tokenNot);
		tab.put(tokenAnd.getLexema(), tokenAnd);
		tab.put(tokenOr.getLexema(), tokenOr);
		tab.put(tokenBool.getLexema(), tokenBool);
		tab.put(tokenText.getLexema(), tokenText);
		tab.put(tokenInt.getLexema(), tokenInt);
		tab.put(tokenFloat.getLexema(), tokenFloat);
		tab.put(tokenProgram.getLexema(), tokenProgram);
		tab.put(tokenEndProgram.getLexema(), tokenEndProgram);
		tab.put(tokenBegin.getLexema(), tokenBegin);
		tab.put(tokenEnd.getLexema(), tokenEnd);
		tab.put(tokenIf.getLexema(), tokenIf);
		tab.put(tokenThen.getLexema(), tokenThen);
		tab.put(tokenElse.getLexema(), tokenElse);
		tab.put(tokenFor.getLexema(), tokenFor);
		tab.put(tokenWhile.getLexema(), tokenWhile);
		tab.put(tokenDeclare.getLexema(), tokenDeclare);
		tab.put(tokenTo.getLexema(), tokenTo);


		
	}
	
	public static TabSimbolos getInstance() {
		return instance;
	}
	
	public Token addToken(String lexema, long linha, long coluna) {
		Token token = null;

		if (tab.containsKey(lexema)) {
			token = tab.get(lexema);
			token.setLinha(linha);
			token.setColuna(coluna);
		} else {
			token = new Token(TokenType.ID, lexema, linha, coluna);
			if(!idTokens.containsKey(lexema))
			{
				idTokens.put(lexema, false);
			}
			
		}

		return token;
	}
	
	public void printReport()
	{
		cabecalhoTabela();
		String formatTab = "| %-15s || %-16s |\n";
		
		tab.forEach((Lexema, token) -> {
			   System.out.printf(formatTab, token.getTokenType(), Lexema );
		});
		System.out.println("---------------------------------------");
	}

	private void cabecalhoTabela()
	{
		System.out.println("\n---------------------------------------");
		System.out.printf("|%27s %10s","Tabela de Simbolos","|");
		System.out.println("\n---------------------------------------");
		System.out.println("|      Token      ||      Lexema      |");
		System.out.println("---------------------------------------");
	}

	public Map<String, Boolean> getIdTokens() {
		return idTokens;
	}
	
	
}
