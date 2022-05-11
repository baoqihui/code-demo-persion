package com.hbq.codedemopersion.common.controller;

import com.hbq.codedemopersion.common.model.MessageDTO;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.util.BufferImage;
import com.hbq.codedemopersion.util.MessageUtil;
import com.hbq.codedemopersion.util.OssUploadImage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Api(tags = "公共方法")
@AllArgsConstructor
public class CommonController {
    private OssUploadImage ossUploadImage;
    private MessageUtil messageUtil;

    /**
     * 上传图片到 oss
     * @param imgFile
     * @return
     */
    @ApiOperation(value = "上传图片到oss")
    @PostMapping(value="/uploadImgToOSS")
    public String uploadImgToOSS(MultipartFile imgFile) {
        return ossUploadImage.uploadImg(imgFile);
    }


    @ApiOperation(value = "发送短信")
    @PostMapping(value="/send")
    public Result sendMessage(@Valid @RequestBody MessageDTO messageDTO){
        messageUtil.sendMessage(messageDTO.getTel());
        return Result.succeed("发送成功");
    }

    @ApiOperation(value = "验证短信验证码")
    @PostMapping(value="/check")
    public Result checkMessage(@Valid @RequestBody MessageDTO messageDTO){
        if(messageUtil.isCode(messageDTO.getTel(),messageDTO.getCode())){
            return Result.succeed("短信验证成功");
        }else{
            return Result.failed("短信验证失败");
        }
    }
    /**
     * 图片验证码
     * @param req
     * @param res
     * @throws IOException
     */
    @ApiOperation(value = "图片验证码")
    @GetMapping(value = "/BufferImage")
    protected void createImg(HttpServletRequest req, HttpServletResponse res) throws IOException {
        //1.生成随机的验证码及图片
        Object[] objs = BufferImage.createImage();
        //2.将验证码存入session
        String imgcode = (String) objs[0];
        HttpSession session = req.getSession();
        session.setAttribute("checkcode", imgcode);
        //3.将图片输出给浏览器
        BufferedImage img = (BufferedImage) objs[1];
        res.setContentType("image/png");
        //服务器自动创建输出流，目标指向浏览器
        OutputStream os = res.getOutputStream();
        ImageIO.write(img, "png", os);
        os.close();
    }
}
