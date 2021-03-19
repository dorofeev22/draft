package ru.dorofeev22.draft.endpoint;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import ru.dorofeev22.draft.core.constant.UrlConstants;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static ru.dorofeev22.draft.core.constant.FileConstants.FILE_STORAGE_PATH;
import static ru.dorofeev22.draft.core.utils.FileUtils.createFilenameFilter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileEndpointTest extends BaseTestRestService {

    private static final String UPLOADED_FILE_CONTENT = "registry.txt";

    @Override
    protected String getPath() {
        return UrlConstants.FILES;
    }

    @Before
    public void before() throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter printWriter = new PrintWriter(UPLOADED_FILE_CONTENT, StandardCharsets.UTF_8.name())) {
            int i = 0;
            while (i < 100000) {
                i++;
                printWriter.println(i + ";" + randomAlphabetic(25) + ";" + randomAlphabetic(53));
            }
        }

    }

    @Test
    public void uploadTest() throws Exception {
        String fileName = "secrets";
        post(UrlConstants.UPLOAD, createRileForUpload(fileName + ".txt"));
        File[] filesInDirectory = new File(FILE_STORAGE_PATH).listFiles(createFilenameFilter(fileName));
        Assert.assertNotNull(filesInDirectory);
        Assert.assertTrue(filesInDirectory.length > 0);
    }

    private MockMultipartFile createRileForUpload(@NotNull final String fileName) throws IOException {
        return new MockMultipartFile(
                "file", // do not rename!
                fileName,
                TEXT_PLAIN_VALUE,
                new FileInputStream(UPLOADED_FILE_CONTENT));
    }

    @After
    public void deleteFiles() throws IOException {
        File[] files = new File(FILE_STORAGE_PATH).listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                Files.deleteIfExists(Paths.get(files[i].getPath()));
            }
        }
        Files.deleteIfExists(Paths.get(UPLOADED_FILE_CONTENT));
    }

}