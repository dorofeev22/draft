package ru.dorofeev22.draft.endpoint;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.dorofeev22.draft.core.constant.UrlConstants;
import ru.dorofeev22.draft.service.FileStorageService;

@RequestMapping(UrlConstants.FILES)
@RestController
public class FileEndpoint {

    private final FileStorageService fileStorageService;

    public FileEndpoint(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(UrlConstants.UPLOAD)
    public void upload(@RequestParam(required = false) MultipartFile file) {
        fileStorageService.save(file);
    }

}
