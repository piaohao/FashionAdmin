package org.piaohao.fashionadmin.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import org.junit.Test;

public class SecurityTest {

    @Test
    public void encryptPassword() {
        HMac hMac = SecureUtil.hmac(HmacAlgorithm.HmacMD5, "FashionAdmin");
        String digest = hMac.digestHex("19911026");
        System.out.println(digest);
    }
}
