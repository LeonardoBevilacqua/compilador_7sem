/**
* Leonardo Almeida Bevilacqua 1510033187
* Marcelo Costa 1510030281
* Marcio Macedo 1510029701
* Thiago Oliveira 1510028818
*/
package br.com.compilador.utils;

/**
* Enumerado de tipos de erros
*/
public enum ErrorType {
    LEXICO(1),
    SINTATICO(2),
    SEMANTICO(3);

    private int cod;

    private ErrorType(int codErro) {
        this.cod = codErro;
    }

    public int getCod(){
        return cod;
    }

    public static ErrorType toEnum(int codToken) {

        for (ErrorType tokenType : ErrorType.values()){
            if(codToken == tokenType.getCod()) { return tokenType; }
        }

        throw new IllegalArgumentException("codigo invalido "+codToken);
    }
}
