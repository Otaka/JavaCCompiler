package com.javaccompiler.utils;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang3.mutable.MutableObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExternalAppRunner {
    public static int execute(File workingFolder, MutableObject<String> output, String... args) throws IOException {
        CommandLine cmdLine = new CommandLine(args[0]);
        for (int i = 1; i < args.length; i++) {
            cmdLine.addArguments(args[i]);
        }

        DefaultExecutor executor = new DefaultExecutor();
        executor.setWorkingDirectory(workingFolder);
        ByteArrayOutputStream resultOutputStream = new ByteArrayOutputStream();
        executor.setStreamHandler(new PumpStreamHandler(resultOutputStream));
        int exitValue;
        try {
            exitValue = executor.execute(cmdLine);
        } catch (ExecuteException ex) {
            exitValue = ex.getExitValue();
        }
        output.setValue(resultOutputStream.toString(StandardCharsets.UTF_8));
        return exitValue;
    }
}
