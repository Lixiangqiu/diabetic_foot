/**
 * @Classname Hash
 * @Description TODO
 * @Date 2023/4/9 16:22
 * @Created by WangSanmu
 */
package cn.edu.cqu.boot.config;

import org.bouncycastle.util.encoders.Hex;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Hash {
    public static String encode(String salt, String data) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");

        // 生成密钥
        SecretKeySpec secret_key = new SecretKeySpec(salt.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secret_key);
        return Hex.toHexString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    public static String encode(String data) throws Exception {
        // 默认HASH加密盐
        return encode("123456", data);
    }
}
