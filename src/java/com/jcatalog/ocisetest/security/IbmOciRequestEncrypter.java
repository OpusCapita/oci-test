package com.jcatalog.ocisetest.security;

import com.jcatalog.oci.section.outbound.encryptor.Encryptor;

import org.apache.commons.lang.StringUtils;

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
    private List standardParams;

    public Map encrypt(Map params) throws Exception {
        Map encryptedParams = new HashMap();

        String secretKey = (String) params.get(SECRET_KEY_PARAM);

        Encryptor encryptor = new Encryptor(secretKey);

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

                encryptedParams.put(name, encryptor.encrypt(value));
            }
        }
        return encryptedParams;
    }

    public void setStandardParams(List standardParams) {
        this.standardParams = standardParams;
    }
}
