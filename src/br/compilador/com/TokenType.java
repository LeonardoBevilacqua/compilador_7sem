package br.compilador.com;

public enum TokenType {
	EOF(0), 		//
	NUM_INT(1), 	// 123,48, 3E+10
	NUM_FLOAT(2), 	// 4.8, 3.10E+10
	LITERAL(3), 	// “Carlos”, “Batata”
	ID(4), 			// val, _salario, i__
	RELOP(5), 		// $df, $gt, $le
	ARIT_AS(6), 	// +, -
	ARIT_MD(7), 	// *, /
	ASSIGN(8), 		// <-
	TERM(9), 		// ;
	L_PAR(10), 		// (
	R_PAR(11), 		// )
	LOGIC_VAL(12), 	// true, false
	LOGIC_OP(13), 	// and, not, or
	TYPE(14), 		// bool, text, int
	PROGRAM(15), 	// program
	END_PROG(16), 	// end_prog
	BEGIN(17), 		// begin
	END(18), 		// end
	IF(19), 		// if
	THEN(20), 		// then
	ELSE(21), 		// else
	FOR(22), 		// for
	WHILE(23), 		// while
	DECLARE(24), 	// declare 
	TO(25); 		// to
	
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
