/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fin.FileUpload.dao;

import com.fin.FileUpload.entity.FileMetadata;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author SURVE
 */
@Component
public class FileUploadDAO {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) throws Exception {
        if (dataSource == null) {
            throw new Exception("FileUploadDAO.setDataSource(): DataSource object is null.");
        }
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertFileMetadata(FileMetadata fileMetadata) throws Exception {

        if (jdbcTemplate == null) {
            throw new Exception("FileUploadDAO.insertFileMetadata(): JdbcTemplate object is not configured with DataSource. "
                    + "Call FileUploadDAO.setDataSource() first.");
        }

        String SQL = "insert into FileMetadata (type, owner) values (?, ?)";
        jdbcTemplate.update(SQL, new Object[]{fileMetadata.getFileType(), fileMetadata.getFileOwner()});
    }
}
