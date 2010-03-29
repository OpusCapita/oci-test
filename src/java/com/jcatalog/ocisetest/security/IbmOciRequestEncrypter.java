package com.jcatalog.ocisetest.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * IBM specific implementation of <code>OciParametersEncrypter</code>
 *
 * @author Alexander Shulga
 */
public class IbmOciRequestEncrypter implements OciRequestEncrypter {
    private static final String PASSWORD_PARAM = "password";
    private static final String VALIDITY_INTERVAL_PARAM = "validityInterval";
    private static final String SECRET_KEY_PARAM = "secretKey";
    private Base64 base64 = new Base64();
    private List standardParams;

    public Map encrypt(Map params) throws Exception {
        Map encryptedParams = new HashMap();

        String secretKey = (String) params.get(SECRET_KEY_PARAM);

        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry param = (Map.Entry) iterator.next();

            String name = (String) param.getKey();
            String value = (String) param.getValue();

            if (standardParams.contains(name)) {
                //standard params are not encrypted
                encryptedParams.put(name, value);
            } else {
                if (PASSWORD_PARAM.equals(name)) {
                    //password is concatenated with validToDate (currentDate+validity interval from UI)
                    String validityIntervalString = (String) params.get(VALIDITY_INTERVAL_PARAM);

                    int validityInterval = StringUtils.isNotBlank(validityIntervalString)
                        ? Integer.parseInt(validityIntervalString) : 0;
                    Date validTo = new Date(System.currentTimeMillis()
                            + (validityInterval * 1000));

                    value = value + " " + validTo.getTime();
                }

                encryptedParams.put(name, encrypt(secretKey, value));
            }
        }
        return encryptedParams;
    }

    private String encrypt(String keyString, String inputString) throws Exception {
        BlockCipher engine = new DESEngine();
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(
                    engine));

        byte[] key = keyString.getBytes();
        byte[] input = inputString.getBytes();

        cipher.init(true, new KeyParameter(key));
        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
        int outputLen = cipher.processBytes(input, 0, input.length, cipherText, 0);
        cipher.doFinal(cipherText, outputLen);

        return new String(base64.encode(cipherText));
    }

    public void setStandardParams(List standardParams) {
        this.standardParams = standardParams;
    }
}
