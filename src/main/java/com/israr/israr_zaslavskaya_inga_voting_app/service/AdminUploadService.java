package com.israr.israr_zaslavskaya_inga_voting_app.service;

import com.israr.israr_zaslavskaya_inga_voting_app.dao.AdminUploadDao;
import com.israr.israr_zaslavskaya_inga_voting_app.model.AdminUpload;
import jakarta.validation.Path;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.Optional;

@Service
public class AdminUploadService {
    private final AdminUploadDao adminUploadDao;

    public AdminUploadService(AdminUploadDao adminUploadDao) {
        this.adminUploadDao = adminUploadDao;
    }

//    private AdminUploadDao adminUploadDao;

    public void createUser(AdminUpload image) {
        adminUploadDao.save(image);
    }

    public Optional<AdminUpload> getAdminUploadByUd(Long id) {
        return adminUploadDao.findById(id);
    }
}
