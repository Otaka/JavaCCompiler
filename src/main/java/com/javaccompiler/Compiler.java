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
        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
        PrintStream result = new PrintStream(bufferStream, true, StandardCharsets.UTF_8);

        result.println("global start");
        result.println("section .text");
        result.println("start:");
        result.println("  mov eax, " + Integer.parseInt(source));
        result.println("  ret");

        result.flush();
        return bufferStream.toString(StandardCharsets.UTF_8);
    }
}
