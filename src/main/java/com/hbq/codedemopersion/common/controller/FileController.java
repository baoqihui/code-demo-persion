package com.hbq.codedemopersion.common.controller;

import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.common.service.FileManageService;
import com.hbq.codedemopersion.util.QiniuCloudUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@Api(tags = "文件服务")
public class FileController {
    @Autowired
    private FileManageService fileManageService;

    @ApiOperation(value = "上传文件以便打开")
    @PostMapping("/uploadToNginxForOpen")
    public String uploadToNginxForOpen(@RequestParam("file") MultipartFile file,String modelName) {
        if (file==null){
            return "文件上传失败，请重新选择文件";
        }
        return fileManageService.uploadToNginxForOpen(file,modelName);
    }
    @ApiOperation(value = "上传文件以便下载")
    @PostMapping("/uploadToNginxForDownload")
    public String uploadToNginxForDownload(@RequestParam("file") MultipartFile file,String modelName) {
        if (file==null){
            return "文件上传失败，请重新选择文件";
        }
        return fileManageService.uploadToNginxForDownload(file,modelName);
    }
    @ApiOperation(value = "上传文件到七牛云")
    @PostMapping(value = "/qiniuUpload")
    public String qiniuUpload(@RequestParam("file") MultipartFile file,String modelName,Integer isAutoUUID){
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        return fileManageService.qiniuUpload(file,modelName,isAutoUUID);
    }
    @ApiOperation(value = "获取文件列表")
    @PostMapping(value = "/getFileList")
    public List getList(String prefix){
        return fileManageService.getList(prefix);
    }
}
