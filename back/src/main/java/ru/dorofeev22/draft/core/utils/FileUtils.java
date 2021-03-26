package ru.dorofeev22.draft.core.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static FilenameFilter createFilenameFilter(@NotNull final String partOfName) {
        return new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(partOfName);
            }
        };
    }
    
    public static byte[] readFile(@NotNull final String filePath) throws IOException {
        return Files.readAllBytes(Paths.get(filePath));
    }

}