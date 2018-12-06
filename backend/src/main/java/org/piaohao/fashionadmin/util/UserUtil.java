package org.piaohao.fashionadmin.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

public class UserUtil {

    public static String encryptPassword(String password) {
        HMac hMac = SecureUtil.hmac(HmacAlgorithm.HmacMD5, "FashionAdmin");
        return hMac.digestHex(password);
    }

}
