package br.com.compilador;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.compilador.token.TokenType;

public class FirstFollowTables {

	private static FirstFollowTables instance = new FirstFollowTables();
	private Map<String, List<TokenType>> first;
	private Map<String, List<TokenType>> follow;
	
	private FirstFollowTables() {
		first = new HashMap<String, List<TokenType>>();
		setFollow();
		
		follow = new HashMap<String, List<TokenType>>();
		
	}
	
	public static FirstFollowTables getInstance() {
		return instance;
	}

	public Map<String, List<TokenType>> getFirst() {
		return first;
	}

	public void setFirst(Map<String, List<TokenType>> first) {
		this.first = first;
	}

	public Map<String, List<TokenType>> getFollow() {
		return follow;
	}

	private void setFollow() {
		first.put("S", Arrays.asList(TokenType.PROGRAM));
		first.put("cmds", Arrays.asList(TokenType.DECLARE, TokenType.IF, TokenType.FOR, TokenType.WHILE, TokenType.ID));		
		first.put("cmd", Arrays.asList(TokenType.DECLARE, TokenType.IF, TokenType.ID, TokenType.FOR, TokenType.WHILE));
		first.put("decl", Arrays.asList(TokenType.DECLARE));
		first.put("cond", Arrays.asList(TokenType.IF));
		first.put("cndb", Arrays.asList(TokenType.ELSE));
		first.put("atrib", Arrays.asList(TokenType.ID));
		first.put("exp", Arrays.asList(TokenType.LOGIC_VAL, TokenType.ID, TokenType.NUM_INT, TokenType.NUM_FLOAT, TokenType.L_PAR, TokenType.LITERAL));		
		first.put("fid", Arrays.asList(TokenType.LOGIC_OP, TokenType.ARIT_AS, TokenType.ARIT_MD));
		first.put("fopnum", Arrays.asList(TokenType.L_PAR, TokenType.ID, TokenType.NUM_INT, TokenType.NUM_FLOAT));
		first.put("fexpnum1", Arrays.asList(TokenType.RELOP));
		first.put("fnumint", Arrays.asList(TokenType.ARIT_AS, TokenType.ARIT_MD));
		first.put("fopnum1", Arrays.asList(TokenType.L_PAR, TokenType.ID, TokenType.NUM_INT, TokenType.NUM_FLOAT));
		first.put("fexpnum2", Arrays.asList(TokenType.RELOP));
		first.put("fnumfloat", Arrays.asList(TokenType.ARIT_AS, TokenType.ARIT_MD));
		first.put("fopnum2", Arrays.asList(TokenType.L_PAR, TokenType.ID, TokenType.NUM_INT, TokenType.NUM_FLOAT));
		first.put("fexpnum3", Arrays.asList(TokenType.RELOP));
		first.put("flpar", Arrays.asList(TokenType.L_PAR, TokenType.ID, TokenType.NUM_INT, TokenType.NUM_FLOAT));
		first.put("fexpnum", Arrays.asList(TokenType.R_PAR));
		first.put("frpar", Arrays.asList(TokenType.RELOP));
		first.put("fid1", Arrays.asList(TokenType.RELOP, TokenType.LOGIC_OP, TokenType.ARIT_AS, TokenType.ARIT_MD));
		first.put("fvallog", Arrays.asList(TokenType.LOGIC_OP));
		first.put("xexpnum", Arrays.asList(TokenType.ARIT_AS, TokenType.ARIT_MD));
		first.put("opnum", Arrays.asList(TokenType.ARIT_AS, TokenType.ARIT_MD));
		first.put("val", Arrays.asList(TokenType.ID, TokenType.NUM_INT, TokenType.NUM_FLOAT));
		first.put("rep", Arrays.asList(TokenType.FOR, TokenType.WHILE));
		first.put("repf", Arrays.asList(TokenType.FOR));
		first.put("expnum", Arrays.asList(TokenType.L_PAR, TokenType.ID, TokenType.NUM_INT, TokenType.NUM_FLOAT));
		first.put("repw", Arrays.asList(TokenType.WHILE));
		first.put("explo", Arrays.asList(TokenType.LOGIC_VAL, TokenType.ID, TokenType.NUM_INT, TokenType.NUM_FLOAT, TokenType.L_PAR));
		first.put("bloco", Arrays.asList(TokenType.BEGIN, TokenType.DECLARE, TokenType.IF, TokenType.FOR, TokenType.WHILE));		
	}
}
