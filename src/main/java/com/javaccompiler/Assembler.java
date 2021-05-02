package com.javaccompiler;

import com.javaccompiler.utils.ExternalAppRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.mutable.MutableObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Assembler {
    public void assemble(String source, File outputFile) throws IOException {
        File tempAssemblerFile = null;
        try {
            tempAssemblerFile = getTempSourceFile(outputFile);
            FileUtils.writeStringToFile(tempAssemblerFile, source, StandardCharsets.UTF_8);
            executeNasm(tempAssemblerFile, outputFile);
        } finally {
            if (tempAssemblerFile != null) {
                tempAssemblerFile.delete();
            }
        }
    }

    private void executeNasm(File assemblerSource, File outputFile) throws IOException {
        MutableObject<String> result = new MutableObject<>();
        File outputObjFile = new File(outputFile.getParent(), "obj.obj");
        int exitCode = ExternalAppRunner.execute(outputFile.getParentFile(), result, "nasm", "-f", "win64", "-o", outputObjFile.getAbsolutePath(), assemblerSource.getAbsolutePath());

        if (exitCode != 0) {
            System.out.println("Cannot assemble file");
            System.out.println(result.getValue());
            System.out.flush();
            throw new IllegalStateException("Cannot assemble file");
        }

        exitCode = ExternalAppRunner.execute(outputFile.getParentFile(), result, "golink", "/console", outputObjFile.getAbsolutePath(), "user32.dll", "kernel32.dll", "/fo", outputFile.getAbsolutePath());
        if (exitCode != 0) {
            System.out.println("Cannot link file");
            System.out.println(result.getValue());
            System.out.flush();
            throw new IllegalStateException("Cannot link the file");
        }
    }


    private File getTempSourceFile(File outputFile) {
        String parentPath = outputFile.getParent();
        return new File(parentPath, "asm_" + outputFile.getName() + ".asm");
    }
}
