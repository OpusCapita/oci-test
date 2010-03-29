package com.jcatalog.ocisetest.security;

import java.util.Map;


/**
 * Encrypts OCI request parameters.
 *
 * @author Alexander Shulga
 */
public interface OciRequestEncrypter {
    /**
     * Encrypts OCI request parameters.
     *
     * @param params - OCI request params for encryption
     *
     * @return Map of encrypted parameters
     */
    public Map encrypt(Map params) throws Exception;
}
