package com.javaccompiler;

import org.apache.commons.lang3.StringUtils;

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
        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
        PrintStream result = new PrintStream(bufferStream, true, StandardCharsets.UTF_8);

        result.println("global start");
        result.println("section .text");
        result.println("start:");

        String[] tokens = StringUtils.splitByCharacterType(source);
        result.println("  mov rax, " + Integer.parseInt(tokens[0]));
        for (int i = 1; i < tokens.length; i += 2) {
            String tokenOperation = tokens[i];
            if (tokenOperation.equals("+")) {
                result.println("  add rax, " + Integer.parseInt(tokens[i + 1]));
            } else if (tokenOperation.equals("-")) {
                result.println("  sub rax, " + Integer.parseInt(tokens[i + 1]));
            } else {
                throw new IllegalArgumentException("Unexpected token [" + tokenOperation + "]");
            }
        }

        result.println("  ret");

        result.flush();
        return bufferStream.toString(StandardCharsets.UTF_8);
    }
}
