/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infiniteskills.mvc.controllers;

import com.infiniteskills.mvc.entity.Users;
import com.infiniteskills.mvc.repository.UsersRepository;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Юыху
 */
@Controller
public class FileUploadController {

     private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(FileUploadController.class.getName());

    private UsersRepository usersDAO;

    @Autowired
    private HttpServletRequest request;

    @Autowired(required = false)
    public void setUsersRepository(UsersRepository userDAO) {
        this.usersDAO = userDAO;
    }

    /**
     * Upload single file using Spring Controller
     *
     * @param name
     * @param email
     * @param id
     * @param file
     * @return
     */
    @RequestMapping(value = "/saveprofil", method = RequestMethod.POST)
    public String uploadFileHandler(@RequestParam("username") String name, @RequestParam("email") String email, @RequestParam("id") Integer id,
            @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                //String rootPath = System.getProperty("catalina.home");
                String uploadsDir = "/resources/images/";
                String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);

                File dir = new File(realPathtoUploads + File.separator + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Create the file on server
                File serverFile = new File(dir.getCanonicalPath()
                        + File.separator + name + ".jpg");
                try (BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile))) {
                    stream.write(bytes);
                }

                log.info("Server File Location="
                        + serverFile.getAbsolutePath());

                // save updated date about users
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String username = auth.getName();
                Users user = usersDAO.findUserByLogin(username);
                user.setEmail(email);
                user.setUsername(name);
                user.setPicture("resources/images/tmpFiles/" + name + ".jpg");
                usersDAO.update(user);

                return "redirect:viewprofil";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name
                    + " because the file was empty.";
        }
    }

}
