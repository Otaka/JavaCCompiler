package com.javaccompiler;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class CompilerTest extends AbstractBaseTest {

    @Test
    void compile() throws IOException {
        testSourceStringCompilation("10 + 25", 35);
        testSourceStringCompilation("10", 10);
        testSourceStringCompilation("6 - 2", 4);
        testSourceStringCompilation("10 +2-3", 9);
    }
}