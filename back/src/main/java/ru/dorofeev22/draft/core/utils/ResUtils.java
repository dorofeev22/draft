package ru.dorofeev22.draft.core.utils;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

public class ResUtils {

    private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    public static byte[] getResourceAsBytes(@NotNull final String classPath) throws IOException {
        return FileUtils.readFileToByteArray(RESOURCE_LOADER.getResource(classPath).getFile());
    }

}