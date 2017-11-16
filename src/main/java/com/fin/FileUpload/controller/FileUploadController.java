/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fin.FileUpload.controller;

import com.fin.FileUpload.config.FileUploadConfig;
import com.fin.FileUpload.dao.FileUploadDAO;
import com.fin.FileUpload.util.FileUploadUtil;
import com.fin.FileUpload.entity.FileMetadata;
import java.io.IOException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author SURVE
 */
@RestController
public class FileUploadController {

    private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Value("${fileupload.path}")
    private String UPLOADED_FOLDER;

    /* 
    Endpoint: http://<serverhost>:<serverport>/file/upload?fileType=testfile&fileOwner=scott
    */
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(
            @RequestBody MultipartFile uploadfile,
            @RequestParam(required = false, value = "fileType") String fileType,
            @RequestParam(required = false, value = "fileOwner") String fileOwner
    ) {

        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(FileUploadConfig.class);

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("Please select a file.", HttpStatus.OK);
        }

        logger.debug("Uploading the file...");
        try {

            FileUploadUtil fileUploadUtil = context.getBean("fileUploadUtil", com.fin.FileUpload.util.FileUploadUtil.class);
            fileUploadUtil.uploadFile(uploadfile, UPLOADED_FOLDER);

        } catch (IOException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        logger.debug("Saving the file metadata...");
        try {
            FileMetadata fileMetadata = new FileMetadata(fileType, fileOwner);

            DataSource dataSource = context.getBean("dataSource", javax.sql.DataSource.class);
            FileUploadDAO fileUploadDAO = context.getBean("fileUploadDAO", com.fin.FileUpload.dao.FileUploadDAO.class);
            fileUploadDAO.setDataSource(dataSource);
            fileUploadDAO.insertFileMetadata(fileMetadata);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return new ResponseEntity("Successfully uploaded file: "
                + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
}
