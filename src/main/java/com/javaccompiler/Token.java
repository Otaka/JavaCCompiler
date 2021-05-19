package com.javaccompiler;

public class Token {
    private final TokenKind kind;
    private final String stringValue;
    private Token nextToken;
    private int value;

    public Token(TokenKind kind, Token nextToken, int value, String stringValue) {
        this.kind = kind;
        this.nextToken = nextToken;
        this.value = value;
        this.stringValue = stringValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStringValue() {
        return stringValue;
    }

    public Token getNextToken() {
        return nextToken;
    }

    public Token setNextToken(Token nextToken) {
        this.nextToken = nextToken;
        return this;
    }

    public TokenKind getKind() {
        return kind;
    }
}
