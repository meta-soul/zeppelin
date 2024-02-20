package org.dmetasoul.lakesoul;

import org.apache.commons.codec.binary.Base64;
import org.apache.zeppelin.conf.ZeppelinConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Asakiny@dmetasoul.com
 * @ClassName AESEncyptUtil
 * @Description TODO
 * @createTime 2024/2/19 18:10
 */
public class AESEncryptUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESEncryptUtil.class);
    private ZeppelinConfiguration zconf;

    private String secret;

    private AESEncryptUtil(){
        zconf = ZeppelinConfiguration.create();
        secret = zconf.getLakesoulMetaPGSecret();
    }
    public static AESEncryptUtil getInstance(){
        return EncryptUtilHolder.instance;
    }
    private static class EncryptUtilHolder {
        private static final AESEncryptUtil instance = new AESEncryptUtil();

    }

    private static String getSecret() {
        return EncryptUtilHolder.instance.secret;
    }

    public static byte[] decode(String base64EncodedString) {
        return new Base64().decode(base64EncodedString);
    }
    public static String decryptAES(String data) throws IOException {
        try
        {
            byte[] encrypted1 = AESEncryptUtil.decode(data);//先用base64解密
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            byte[] content = Arrays.copyOfRange(encrypted1, 16, 32);
            SecretKeySpec keyspec = new SecretKeySpec(AESEncryptUtil.getSecret().getBytes(), "AES");
            byte[] iv = Arrays.copyOfRange(encrypted1,0, 16);
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(content);
            String originalString = new String(original);
            return originalString.trim();
        }
        catch (Exception e) {
            LOGGER.error("Decrypt aes content failed! {}" , e.getMessage());
            throw new IOException(e);
        }
    }
}
