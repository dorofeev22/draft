package ru.dorofeev22.draft.service;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev22.draft.core.utils.DateTimeUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;
import static ru.dorofeev22.draft.core.constant.FileConstants.FILE_STORAGE_PATH;

@Service
public class FileStorageService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Path path = Paths.get(FILE_STORAGE_PATH);

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            log.error("Couldn't create file storage: ", e);
            throw new RuntimeException();
        }
    }

    public void save(@NotNull final MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), path.resolve(createFileName(file)));
        } catch (Exception e) {
            log.error("Couldn't save file: ", e);
            throw new RuntimeException();
        }
    }

    private String createFileName(@NotNull final MultipartFile file) {
        return defaultIfBlank(file.getOriginalFilename(), "") + DateTimeUtils.getNowAsInstant();
    }

}
