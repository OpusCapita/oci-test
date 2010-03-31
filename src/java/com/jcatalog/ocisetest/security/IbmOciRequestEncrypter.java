package com.jcatalog.ocisetest.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;


/**
 * IBM specific implementation of <code>OciParametersEncrypter</code>
 *
 * @author Alexander Shulga
 */
public class IbmOciRequestEncrypter implements OciRequestEncrypter {
    private static final String PASSWORD_PARAM = "password";
    private static final String VALIDITY_INTERVAL_PARAM = "validityInterval";
    private static final String SECRET_KEY_PARAM = "secretKey";
    private static final String ALGORITHM_TYPE = "DES";
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

                    String validityIntervalString = (String) params.get(VALIDITY_INTERVAL_PARAM);
                    // setting valid interval to password
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
        byte[] key = keyString.getBytes();
        SecretKeySpec sks = new SecretKeySpec(key, ALGORITHM_TYPE);
        Cipher algorithm = Cipher.getInstance(ALGORITHM_TYPE);
        algorithm.init(Cipher.ENCRYPT_MODE, sks);

        byte[] encrypted = algorithm.doFinal(inputString.getBytes());

        return new String(base64.encode(encrypted));
    }

    public void setStandardParams(List standardParams) {
        this.standardParams = standardParams;
    }
}
