package com.hbq.codedemopersion.model;

import cn.hutool.core.date.DateUtil;
import com.hbq.codedemopersion.common.model.WechatTemplateEnum;
import com.hbq.codedemopersion.util.AmountConvertUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.hbq.codedemopersion.common.model.WechatTemplateEnum.ACCOUNT_CHANGE;

/**
 * @Author: huibq
 * @Description: 奖励到账实体
 * @Date: 2021/11/16 17:18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class AccountChangeMsgReq extends SendMsgReq {
    /**
     * 达标总天数
     */
    private Integer standardCount;

    /**
     * 每月最高返现金额
     */
    private Integer monthMaxAmount;

    /**
     * 变更金额
     */
    private Long changeAmount;

    public AccountChangeMsgReq(String receiver, Integer standardCount, Integer monthMaxAmount, Long changeAmount) {
        super(receiver);
        this.standardCount = standardCount;
        this.changeAmount = changeAmount;
        this.monthMaxAmount = monthMaxAmount;
    }

    @Override
    public WechatTemplateEnum templateEnum() {
        return ACCOUNT_CHANGE;
    }

    @Override
    public Map<String, Object> assembleCustomTemplateParams() {
        /* 处理相关数据参数 */
        //此次变更金额(元)
        BigDecimal changeAmountDollar = AmountConvertUtil.fenToYuanFormatTwoRoundTowards(changeAmount);
        //最高返现金额 （保留整数）
        BigDecimal monthMaxDecimal = AmountConvertUtil.fenToYuanFormatRoundTowards(monthMaxAmount);

        return new HashMap<>(8) {{
            put("thing1", new HashMap<>() {{
                put("value", "恭喜您！本次停驶完成");
            }});
            put("amount2", new HashMap<>() {{
                put("value", changeAmountDollar + "元");
            }});
            put("time3", new HashMap<>() {{
                put("value", DateUtil.now());
            }});
            put("thing4", new HashMap<>() {{
                put("value", "每月少开" + standardCount + "天车，必得奖励" + monthMaxDecimal + "元钱");
            }});
        }};
    }
}
