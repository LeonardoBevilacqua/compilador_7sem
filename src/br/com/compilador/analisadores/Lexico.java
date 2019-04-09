package br.com.compilador.analisadores;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

import br.com.compilador.TabSimbolos;
import br.com.compilador.token.Token;
import br.com.compilador.token.TokenType;
import br.com.compilador.utils.ErrorHandler;
import br.com.compilador.utils.FileLoader;

public class Lexico {

    private FileLoader fl;
    private ErrorHandler errorH;

    private StringBuilder lexema = new StringBuilder();
    private TokenType tokenType = null;
    private char c = ' ';
    private long coluna_inicial;

    public Lexico(String filename) {
        errorH = ErrorHandler.getInstance();
        try {
            fl = new FileLoader(filename);
        } catch (FileNotFoundException e) {
            errorH.registraErro(e.getMessage());
        }
    }

    public Token nextToken() {
        Token tk = null;
        lexema = new StringBuilder();
        c = ' ';
        tokenType = null;
        coluna_inicial = 1;

        try {
            do {
                c = fl.getNextChar();
                if (lexema.length() == 0) {
                    coluna_inicial = fl.getColumn();
                }
                if (Character.isLetter(c) || c == '_') {
                    return verificaID();

                } else if (Character.isDigit(c)) {
                    if (verifica_int_float()) {
                        break;
                    }
                } else if (c == '$') {
                    return verificarRelop();
                } else if (c == '"') {
                    return verificaLiteral();
                } else if (c == '<') {
                    return verificaAssign();
                } else if (verifica_caracteres_simples()) {
                    break;
                }

            } while (Character.isWhitespace(c));
        } catch (IOException e) {
            if (lexema.length() != 0) {
                try {
                    fl.resetLastChar();
                } catch (IOException e1) {

                    return tk;
                }
                return obter_token();
            }
            return new Token(TokenType.EOF, "", fl.getLine(), fl.getColumn());
        }

        return obter_token();
    }

    /**
     * This function verifies what type of number will get and generate the save the
     * token
     *
     * @throws EOFException
     * @throws IOException
     */
    private boolean verifica_int_float() throws EOFException, IOException {
        tokenType = TokenType.NUM_INT;

        do {
            next_character();
        } while (Character.isDigit(c));

        if (c == '.') {
            if (!verifica_float()) {
                fl.resetLastChar();
                return false;
            }
        } else if (c == 'E' || c == 'e') {
            if (!verifica_notacao()) {
                fl.resetLastChar();
                return false;
            }
        } else if (!Character.isWhitespace(c)) {
            fl.resetLastChar();
            return false;
        }
        fl.resetLastChar();

        return true;
    }

    private void next_character() throws IOException {
        lexema.append(c);
        c = fl.getNextChar();
    }

    private boolean verifica_float() throws IOException {
        tokenType = TokenType.NUM_FLOAT;
        do {
            next_character();
        } while (Character.isDigit(c));

        if (c == 'E' || c == 'e') {
            verifica_notacao();
        } else if (!Character.isWhitespace(c)) {
            fl.resetLastChar();
            return false;
        }

        return true;
    }

    private boolean verifica_notacao() throws IOException {
        do {
            next_character();
        } while (Character.isDigit(c) || (c == '+' || c == '-'));
        if (!Character.isWhitespace(c)) {
            fl.resetLastChar();
            return false;
        }

        return true;
    }

    private boolean verifica_caracteres_simples() throws IOException {
        if (c == '+' || c == '-') {
            tokenType = TokenType.ARIT_AS;
        } else if (c == '*' || c == '/') {
            tokenType = TokenType.ARIT_MD;
        } else if (c == ';') {
            tokenType = TokenType.TERM;
        } else if (c == '(') {
            tokenType = TokenType.L_PAR;
        } else if (c == ')') {
            tokenType = TokenType.R_PAR;

        } else {
            //fl.resetLastChar();
            return false;
        }

        lexema.append(c);
        return true;
    }

    private Token obter_token() {
        return new Token(tokenType, lexema.toString(), fl.getLine(), coluna_inicial);
    }

    private Token verificarRelop() throws IOException {

        Token tk = null;

        try {
            next_character();
            if (c == 'l' || c == 'g') {
                next_character();
                if (c == 't' || c == 'e') {
                    lexema.append(c);
                    tokenType = TokenType.RELOP;
                    tk = new Token(tokenType, lexema.toString(), fl.getLine(), coluna_inicial);
                } else {
                    fl.resetLastChar();
                    errorH.registraErro("lexema errado :" + lexema + " | Linha: " + fl.getLine() + " | Coluna " + coluna_inicial);
                }
            } else if (c == 'e') {
                next_character();
                if (c == 'q') {
                    lexema.append(c);
                    tokenType = TokenType.RELOP;
                    tk = new Token(tokenType, lexema.toString(), fl.getLine(), coluna_inicial);
                } else {
                    fl.resetLastChar();
                    errorH.registraErro("lexema errado :" + lexema + " | Linha: " + fl.getLine() + " | Coluna " + coluna_inicial);
                }
            } else if (c == 'd') {
                next_character();
                if (c == 'f') {
                    lexema.append(c);
                    tokenType = TokenType.RELOP;
                    tk = new Token(tokenType, lexema.toString(), fl.getLine(), coluna_inicial);

                } else {
                    fl.resetLastChar();
                    errorH.registraErro("lexema errado :" + lexema + " | Linha: " + fl.getLine() + " | Coluna " + coluna_inicial);
                }

            } else {
                fl.resetLastChar();
                errorH.registraErro("lexema errado :" + lexema + " | Linha: " + fl.getLine() + " | Coluna " + coluna_inicial);
            }
        } catch (IOException e) {
            fl.resetLastChar();
            errorH.registraErro("lexema errado :" + lexema + " | Linha: " + fl.getLine() + " | Coluna " + coluna_inicial);
        }

        return tk;
    }

    private Token verificaID() throws IOException {
        Token tk = null;
        while (true) {
            try {
                if (Character.isLetter(c) || c == '_' || Character.isDigit(c)) {
                    lexema.append(c);
                    c = fl.getNextChar();
                } else {
                    fl.resetLastChar();
                    tk = TabSimbolos.getInstance().addToken(lexema.toString(), fl.getLine(), coluna_inicial);
                    break;
                }
            } catch (EOFException e) {
                fl.resetLastChar();
                tk = TabSimbolos.getInstance().addToken(lexema.toString(), fl.getLine(), coluna_inicial);
                break;
            }
        }
        return tk;
    }

    private Token verificaLiteral() throws IOException {
        Token tk = null;

        lexema.append(c);
        while (true) {
            try {
                c = fl.getNextChar();
                if (c != '"') {
                    lexema.append(c);
                } else {
                    lexema.append(c);
                    tokenType = TokenType.LITERAL;
                    tk = new Token(tokenType, lexema.toString(), fl.getLine(), coluna_inicial);
                    break;
                }
            } catch (IOException e) {
                fl.resetLastChar();
                errorH.registraErro("lexema errado :" + lexema + " | Linha: " + fl.getLine() + " | Coluna " + coluna_inicial);
                break;
            }
        }
        return tk;
    }

    private Token verificaAssign() throws IOException {
        Token tk = null;

        try {
            next_character();
            if (c == '-') {
                lexema.append(c);
                tokenType = TokenType.ASSIGN;
                tk = new Token(tokenType, lexema.toString(), fl.getLine(), coluna_inicial);
            } else {
                fl.resetLastChar();
                errorH.registraErro("lexema errado :" + lexema + " | Linha: " + fl.getLine() + " | Coluna " + coluna_inicial);
            }

        } catch (IOException e) {
            fl.resetLastChar();
            errorH.registraErro("lexema errado :" + lexema + " | Linha: " + fl.getLine() + " | Coluna " + coluna_inicial);
        }
        return tk;
    }
}
