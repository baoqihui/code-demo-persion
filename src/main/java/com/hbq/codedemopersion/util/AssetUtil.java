package com.hbq.codedemopersion.util;

import com.hbq.codedemopersion.common.model.DingDingBot;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Author: huibq
 * @Date: 2022/7/12 13:53
 * @Description: 签名加密工具类
 */
public class AssetUtil {
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    /**
     * https://open.dingtalk.com/document/robots/customize-robot-security-settings
     * 钉钉机器人签名
     * @return 签名结果和时间戳
     * @throws Exception
     */
    public static DingDingBot dingDingSign() {
        Long timestamp = System.currentTimeMillis();
        String secret = "SEC5340984f51a92178529ba39e0004ed1396b03f939469a5e610fd63b5d1f4838f";

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256_ALGORITHM));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8);
        return DingDingBot.builder().sign(sign).timestamp(timestamp.toString()).build();
    }
}
