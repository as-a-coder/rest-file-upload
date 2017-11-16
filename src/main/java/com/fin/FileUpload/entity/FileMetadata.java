/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fin.FileUpload.entity;

/**
 *
 * @author SURVE
 */
public class FileMetadata {

    private String fileType, fileOwner;

    public FileMetadata() {
        // default constructor
    }

    public FileMetadata(String fileType, String fileOwner) {
        super();
        this.fileType = fileType;
        this.fileOwner = fileOwner;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileOwner() {
        return fileOwner;
    }

    public void setFileOwner(String fileOwner) {
        this.fileOwner = fileOwner;
    }

}
