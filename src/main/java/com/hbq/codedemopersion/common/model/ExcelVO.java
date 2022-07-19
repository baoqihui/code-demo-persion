package com.hbq.codedemopersion.common.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelVO {
    @ExcelProperty("ID")
    private String gpsId;

    @ExcelProperty("车牌号")
    private String plateNo;

    @ExcelProperty("纬度")
    private String latitude;

    @ExcelProperty("速度")
    private String velocity;

    @ExcelProperty("发送时间")
    private String sendTime;

    @ExcelProperty("描述")
    private String direction;

    @ExcelProperty("经度")
    private String longitude;

    @ExcelProperty("里程")
    private String mileage;
}
