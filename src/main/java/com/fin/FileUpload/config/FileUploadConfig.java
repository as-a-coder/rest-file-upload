/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fin.FileUpload.config;

import com.fin.FileUpload.dao.FileUploadDAO;
import com.fin.FileUpload.util.FileUploadUtil;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;

/**
 *
 * @author SURVE
 */
@Configuration
@ComponentScan("com.fin.FileUpload")
public class FileUploadConfig {

    @Autowired
    private Environment env;

    @Bean
    public FileUploadUtil fileUploadUtil() {
        return new FileUploadUtil();
    }

    @Bean
    public DataSource dataSource() throws NamingException {
        return (DataSource) new JndiTemplate().lookup(env.getProperty("jdbc.url"));
    }

    @Bean
    public FileUploadDAO fileUploadDAO() {
        return new FileUploadDAO();
    }
}
