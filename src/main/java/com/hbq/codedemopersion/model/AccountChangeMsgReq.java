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
/**
 * @Author: huibq
 * @Description: 奖励到账实体
 * @Date: 2021/11/16 17:18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString
public class AccountChangeMsgReq extends SendMsgReq{
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

    public AccountChangeMsgReq(WechatTemplateEnum templateEnum, String receiver, Integer standardCount, Integer monthMaxAmount, Long changeAmount) {
        super(templateEnum,receiver);
        this.standardCount = standardCount;
        this.changeAmount = changeAmount;
        this.monthMaxAmount = monthMaxAmount;
    }

    /**
     * 装换
     * @return
     */
    @Override
    public Map<String, Object> covertMsgReqToMap(){
        WechatTemplateEnum templateEnum = super.getTemplateEnum();
        String receiver = super.getReceiver();
        /* 处理相关数据参数 */
        //此次变更金额(元)
        BigDecimal changeAmountDollar = AmountConvertUtil.fenToYuanFormatTwoRoundTowards(changeAmount);
        //最高返现金额 （保留整数）
        BigDecimal monthMaxDecimal = AmountConvertUtil.fenToYuanFormatRoundTowards(monthMaxAmount);

        return new HashMap<>(8) {{
            put("body", new HashMap<>(8) {{
                put("touser", receiver);
                put("template_id", templateEnum.getTemplateId());
                put("page", templateEnum.getPage());
                put("data", new HashMap<>(8){{
                    put(templateEnum.getMap().get("param1").toString(),new HashMap<>(2){{ put("value", "恭喜您！本次停驶完成"); }});
                    put(templateEnum.getMap().get("param2").toString(),new HashMap<>(2){{ put("value", changeAmountDollar +"元"); }});
                    put(templateEnum.getMap().get("param3").toString(),new HashMap<>(2){{ put("value", DateUtil.now()); }});
                    put(templateEnum.getMap().get("param4").toString(),new HashMap<>(2){{ put("value", "每月少开"+ standardCount +"天车，必得奖励"+ monthMaxDecimal +"元钱"); }});
                }});
            }});
        }};
    }
}
