package com.hbq.codedemopersion.common.service;


import org.springframework.web.multipart.MultipartFile;

public interface FileManageService {



    String uploadToNginxForOpen(MultipartFile file, String modelName);
    
    String uploadToNginxForDownload(MultipartFile file, String modelName);
}
