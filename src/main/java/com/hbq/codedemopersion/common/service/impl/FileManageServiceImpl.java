package com.hbq.codedemopersion.common.service.impl;

import cn.hutool.core.util.IdUtil;
import com.hbq.codedemopersion.common.service.FileManageService;
import com.hbq.codedemopersion.util.QiniuCloudUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Slf4j
@Service
public class FileManageServiceImpl implements FileManageService {

    @Value("${nginx.filePath}")
    private String filePath;
    @Value("${nginx.filePathForDownload}")
    private String filePathForDownload;
    @Value("${nginx.ipAndPortAndFilePrefix}")
    private String ipAndPortAndFilePrefix;

    @Autowired
    private QiniuCloudUtil qiniuCloudUtil;

    @Override
    public String uploadToNginxForOpen(MultipartFile file, String modelName)  {
        try {
            String path=modelName+ "/"+ IdUtil.simpleUUID() +"-"+file.getOriginalFilename();
            File test = new File(filePath+path);
            if (!test.exists()){
                test.mkdirs();
            }
            file.transferTo(test);
            String finalPath="http://"+ipAndPortAndFilePrefix+path;
            log.info("上传直接打开文件{}",finalPath);
            return finalPath;
        }catch (Exception e){
            log.error(file.getOriginalFilename()+"文件上传失败", e);
            return file.getOriginalFilename()+"文件上传失败";
        }
    }

    @Override
    public String uploadToNginxForDownload(MultipartFile file, String modelName) {
        try {
            String path=modelName+ "/"+ IdUtil.simpleUUID() +"-"+file.getOriginalFilename();
            File test = new File(filePathForDownload+path);
            if (!test.exists()){
                test.mkdirs();
            }
            file.transferTo(test);
            String finalPath="http://"+ipAndPortAndFilePrefix+path;
            log.info("上传可下载文件{}",finalPath);
            return finalPath;
        }catch (Exception e){
            log.error(file.getOriginalFilename()+"文件上传失败", e);
            return file.getOriginalFilename()+"文件上传失败";
        }
    }

    @Override
    public String qiniuUpload(MultipartFile file, String modelName,Integer isAutoUUID){
        try {
            String finalFileName=file.getOriginalFilename();
            if (isAutoUUID!=null&&isAutoUUID==1){
                finalFileName=modelName+ "/"+ IdUtil.simpleUUID() +"-"+finalFileName;
            }
            //使用base64方式上传到七牛云
            String url = qiniuCloudUtil.uploadFile((FileInputStream) file.getInputStream(), finalFileName);
            log.info("上传地址为----：" + url);
            return url;
        }catch (Exception e){
            log.error(file.getOriginalFilename()+"文件上传失败", e);
            return file.getOriginalFilename()+"文件上传失败";
        }
    }

    @Override
    public List getList(String prefix) {
        return qiniuCloudUtil.getList(prefix);
    }

    @Override
    public String delete(String path) {
        return qiniuCloudUtil.delete(path);
    }
}
