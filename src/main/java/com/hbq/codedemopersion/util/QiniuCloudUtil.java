package com.hbq.codedemopersion.util;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class QiniuCloudUtil {
    protected static final Logger log = LoggerFactory.getLogger(QiniuCloudUtil.class);
    @Value("${QiNiuOos.ACCESS_KEY}")
    private String ACCESS_KEY;
    @Value("${QiNiuOos.SECRET_KEY}")
    private String SECRET_KEY;
    @Value("${QiNiuOos.BUCKET_NAME}")
    private String BUCKET_NAME;
    @Value("${QiNiuOos.DOMAIN}")
    private String DOMAIN;

    /**
     * 将文件上传到七牛云
     */
    public String uploadFile(FileInputStream file, String key) {
        // 密钥
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cfg = new Configuration(Zone.zone2());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传

        try {
            String upToken = auth.uploadToken(BUCKET_NAME);
            try {
                Response response = uploadManager.put(file, key, upToken,null,null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                String returnPath = DOMAIN + "/" + putRet.key;
                // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取某前缀下的文件列表
     */
    public List getList(String prefix){
        // 密钥
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cfg = new Configuration(Zone.zone2());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(BUCKET_NAME, prefix, limit, delimiter);
        List<String> paths=new ArrayList<>();
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                paths.add(DOMAIN + "/" + item.key);
            }
        }
        return paths;
    }
    /**
     * 删除某地址的文件
     */
    public String delete(String path){
        // 密钥
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        Configuration cfg = new Configuration(Zone.zone2());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        String key = StrUtil.removePrefix(path, DOMAIN + "/");
        try {
            bucketManager.delete(BUCKET_NAME, key);
            return "删除成功";
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            return ex.code()+ex.response.toString();
        }
    }
}
