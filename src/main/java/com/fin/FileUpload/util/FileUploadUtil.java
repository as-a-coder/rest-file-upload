/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fin.FileUpload.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author SURVE
 */
@Component
public class FileUploadUtil {
    private final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

    public void uploadFile(MultipartFile file, String folderLocation) throws IOException {
        logger.debug("Called uploadFile");

        if (file.isEmpty()) {
            // handle error condition...
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(folderLocation + file.getOriginalFilename());
        Files.write(path, bytes);

    }
}
