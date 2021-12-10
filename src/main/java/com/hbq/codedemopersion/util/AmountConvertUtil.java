package com.hbq.codedemopersion.util;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * @Author: huibq
 * @Description: 金额转换工具类
 * @Date: 2021/11/15 10:52
 */
public class AmountConvertUtil {
    /**
     * 金额单位与元进率
     */
    public static final BigDecimal AMOUNT_UNIT_RATE_CTR = BigDecimal.valueOf(100);
    /**
     * 分->元（四舍五入两位小数）
     * @param amount 金额（分）
     * @return 元（四舍五入两位小数）
     */
    public static BigDecimal fenToYuanFormatTwoRoundTowards(Integer amount){
        return BigDecimal.valueOf(amount).divide(AMOUNT_UNIT_RATE_CTR,2, RoundingMode.HALF_UP);
    }
    /**
     * 分->元（四舍五入两位小数）
     * @param amount 金额（分）
     * @return 元（四舍五入两位小数）
     */
    public static BigDecimal fenToYuanFormatTwoRoundTowards(Long amount){
        return BigDecimal.valueOf(amount).divide(AMOUNT_UNIT_RATE_CTR,2, RoundingMode.HALF_UP);
    }
    /**
     * 分->元（四舍五入两位小数）
     * @param amount 金额（分）
     * @return 元（四舍五入两位小数）
     */
    public static BigDecimal fenToYuanFormatTwoRoundTowards(BigDecimal amount){
        return amount.divide(AMOUNT_UNIT_RATE_CTR,2, RoundingMode.HALF_UP);
    }
    /**
     * 分->元
     * @param amount 金额（分）
     * @return 元
     */
    public static BigDecimal fenToYuanFormatRoundTowards(Integer amount){
        return BigDecimal.valueOf(amount).divide(AMOUNT_UNIT_RATE_CTR, RoundingMode.HALF_UP);
    }
}

