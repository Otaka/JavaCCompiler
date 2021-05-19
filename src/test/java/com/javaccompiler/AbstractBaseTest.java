package com.javaccompiler;

import com.javaccompiler.utils.ExternalAppRunner;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractBaseTest {
    @TempDir
    File tempDirectory;

    public void testSourceStringCompilation(String string, int expectedExitCode) throws IOException {
        File outputFile = new File(tempDirectory, "test.exe");
        Compiler compiler = new Compiler();
        compiler.compile(string, outputFile);
        int result = ExternalAppRunner.execute(outputFile.getParentFile(), new MutableObject<>(), outputFile.getAbsolutePath());
        assertEquals(expectedExitCode, result);
    }
}
