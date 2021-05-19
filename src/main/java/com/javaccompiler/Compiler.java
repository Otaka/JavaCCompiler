package com.javaccompiler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Compiler {

    public void compile(String source, File outputApplicationFile) throws IOException {
        String assemblerSource = generateAssemblerSourceFile(source);
        new Assembler().assemble(assemblerSource, outputApplicationFile);
    }

    private String generateAssemblerSourceFile(String source) {
        Token token = tokenize(source);

        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
        PrintStream result = new PrintStream(bufferStream, true, StandardCharsets.UTF_8);

        result.println("global start");
        result.println("section .text");
        result.println("start:");

        result.println("  mov rax, " + getNumber(token));
        token = token.getNextToken();
        while (token.getKind() != TokenKind.EOF) {
            if (token.getKind() == TokenKind.PUNCT) {
                if (token.getStringValue().equals("+")) {
                    result.println("  add rax, " + getNumber(token.getNextToken()));
                    token = token.getNextToken();
                } else if (token.getStringValue().equals("-")) {
                    result.println("  sub rax, " + getNumber(token.getNextToken()));
                    token = token.getNextToken();
                } else {
                    error("illegal state " + token.getKind());
                }
                token = token.getNextToken();
            }
        }

        result.println("  ret");
        result.flush();
        return bufferStream.toString(StandardCharsets.UTF_8);
    }

    private int getNumber(Token token) {
        if (token.getKind() == TokenKind.NUMBER) {
            return token.getValue();
        }

        error("Expected number");
        return -1;
    }

    private Token tokenize(String source) {
        Token head = new Token(null, null, 0, "");
        Token current = head;
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (Character.isSpaceChar(c)) {
                continue;
            }
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                for (i = i + 1; i < source.length(); i++) {
                    char _c = source.charAt(i);
                    if (!Character.isDigit(_c)) {
                        i--;
                        break;
                    }

                    sb.append(_c);
                }
                Token numberToken = newNumberToken(Integer.parseInt(sb.toString()));
                current.setNextToken(numberToken);
                current = numberToken;
            } else if (c == '+' || c == '-') {
                current.setNextToken(newToken(TokenKind.PUNCT, String.valueOf(c)));
                current = current.getNextToken();
            } else {
                error("Invalid token");
            }
        }

        current.setNextToken(new Token(TokenKind.EOF, null, 0, null));
        return head.getNextToken();
    }

    private Token newToken(TokenKind tokenKind, String value) {
        return new Token(tokenKind, null, 0, value);
    }

    private Token newNumberToken(int value) {
        return new Token(TokenKind.NUMBER, null, value, null);
    }

    private void error(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
