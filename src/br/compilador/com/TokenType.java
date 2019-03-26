package br.compilador.com;

public enum TokenType {
	EOF(1), 
	NUM_INT(2), 
	NUM_FLOAT(3), 
	LITERAL(4), 
	ID(5), 
	RELOP(6), 
	ARIT_AS(7), 
	ARIT_MD(8), 
	ASSIGN(9), 
	TERM(10), 
	L_PAR(11), 
	R_PAR(12), 
	LOGIC_VAL(13), 
	LOGIC_OP(14),
	TYPE(15), 
	PROGRAM(16), 
	END_PROG(17), 
	BEGIN(18), 
	END(19), 
	IF(20), 
	THEN(21), 
	ELSE(22), 
	FOR(23), 
	WHILE(24), 
	DECLARE(25), 
	TO(26);
	
	private int cod;

	private TokenType(int cod){
		this.cod = cod;
	}

	public int getCod(){
		return cod;
	}
	
	public static TokenType toEnum(int cod){
		
		for (TokenType tokenType : TokenType.values()){
			if(cod == tokenType.getCod()) { return tokenType; }
		}
		
		throw new IllegalArgumentException("codigo invalido "+cod);
	}

}
