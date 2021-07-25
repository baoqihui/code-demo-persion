package com.hbq.codedemopersion.common.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileManageService {



    String uploadToNginxForOpen(MultipartFile file, String modelName);
    
    String uploadToNginxForDownload(MultipartFile file, String modelName);

    String qiniuUpload(MultipartFile file, String modelName,Integer isAutoUUID);

    List getList(String prefix);
}
