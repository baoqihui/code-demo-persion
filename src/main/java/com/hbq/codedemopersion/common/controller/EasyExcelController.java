package com.hbq.codedemopersion.common.controller;

import cn.hutool.json.JSONUtil;
import com.hbq.codedemopersion.common.model.ExcelVO;
import com.hbq.codedemopersion.util.ExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("test")
public class EasyExcelController {
    //获取并导出excel
    @GetMapping("exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        String s = "[\n" +
                "        {\n" +
                "        \"gpsId\": \"1\",\n" +
                "        \"plateNo\": \"吉AP8933\",\n" +
                "        \"latitude\": \"40.171741999999995\",\n" +
                "        \"velocity\": \"68.8\",\n" +
                "        \"sendTime\": \"2022-06-10 15:27:03\",\n" +
                "        \"direction\": \"60\",\n" +
                "        \"longitude\": \"120.027114\",\n" +
                "        \"mileage\": \"414865.9\"\n" +
                "        },\n" +
                "        {\n" +
                "        \"gpsId\": \"2\",\n" +
                "        \"plateNo\": \"吉AP8933\",\n" +
                "        \"latitude\": \"40.172545\",\n" +
                "        \"velocity\": \"70.4\",\n" +
                "        \"sendTime\": \"2022-06-10 15:27:12\",\n" +
                "        \"direction\": \"58\",\n" +
                "        \"longitude\": \"120.02887899999999\",\n" +
                "        \"mileage\": \"414866.10000000003\"\n" +
                "        }]";
        List<ExcelVO> excelVOS = JSONUtil.toList(s, ExcelVO.class);
        ExcelUtil.exportExcel(response, "test", ExcelVO.class, excelVOS);
    }
}
