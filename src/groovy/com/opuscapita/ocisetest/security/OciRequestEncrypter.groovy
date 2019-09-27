package com.opuscapita.ocisetest.security

/**
 * Encrypts OCI request parameters.
 */
interface OciRequestEncrypter {
    /**
     * Encrypts OCI request parameters.
     *
     * @param params - OCI request params for encryption
     *
     * @return Map of encrypted parameters
     */
    Map encrypt(Map params) throws Exception
}