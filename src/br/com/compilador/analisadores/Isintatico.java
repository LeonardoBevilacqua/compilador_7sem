package br.com.compilador.analisadores;

public interface Isintatico
{
	void derivaS();
	boolean derivaCmds();
	boolean derivaCmd();
	boolean derivaDecl();
	boolean derivaCond();
	boolean derivaCndb();
	boolean derivaAtrib();
	boolean derivaExp();
	void derivaFID();
	void derivaFOPNum();
	void derivaFEXPNum_1();
	void derivaFNUMInt();
	void derivaFOPNum1();
	void derivaFEXPNum_2();
	void derivaFNumFloat();
	void derivaFOPNum2();
	void derivaFEXPNum_3();
	void derivaFLPar();
	void derivaFEXPNum();
	void derivaFRPar();
	void derivaFID_1();
	void derivaFValLog();
	void derivaXEXPNum();
	void derivaOPnum();
	void derivaVal();
	void derivaRep();
	void derivaRepf();
	void derivaEXPNum();
	void derivaREPW();
	void derivaExplo();
	boolean derivaBloco();
}
