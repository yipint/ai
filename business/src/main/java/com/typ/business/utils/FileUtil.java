package com.typ.business.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {

    public static void writeLine(String line, Path path) throws IOException {
        if (LockUtil.tryLock(path.toString())) {
            try {
                if (!Files.exists(path.getParent())) {
                    Files.createDirectories(path.getParent());
                }
                Files.write(path, (line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            } finally {
                LockUtil.unlock(path.toString());
            }
        }
    }

    public static List<String> readLines(Path path) throws IOException {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        return Files.readAllLines(path).stream()
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());
    }
}
