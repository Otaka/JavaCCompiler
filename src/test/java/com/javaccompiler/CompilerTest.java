package com.javaccompiler;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class CompilerTest extends AbstractBaseTest {

    @Test
    void compile() throws IOException {
        testSourceStringCompilation("25", 25);
        testSourceStringCompilation("0", 0);
    }
}