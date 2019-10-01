package com.opuscapita.ocisetest.security

class DefaultOciRequestEncryptor implements OciRequestEncryptor {
    private static final String PASSWORD_PARAM = "password"
    private static final String VALIDITY_INTERVAL_PARAM = "validityInterval"
    private static final String SECRET_KEY_PARAM = "secretKey"
    private static final String HOOK_URL_PARAM = "HOOK_URL"
    private List standardParams

    @Override
    Map encrypt(Map params) throws Exception {
        Map encryptedParams = [:]
        String secretKey = params.get(SECRET_KEY_PARAM) as String
        Encryptor encryptor = new Encryptor(secretKey)

        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry param = iterator.next() as Map.Entry

            String name = param.getKey() as String
            String value = param.getValue() as String

            if (HOOK_URL_PARAM.equalsIgnoreCase(name)) {
                //adding secret key to hook URL to use it during outbound processing (TEST FUNCTIONALITY ONLY)
                encryptedParams[name] = value + "/" + secretKey
                continue
            }

            if (standardParams.contains(name)) {
                encryptedParams[name] = value
            } else {
                if (PASSWORD_PARAM == name) {
                    String validityIntervalString = params.get(VALIDITY_INTERVAL_PARAM) as String
                    // setting valid interval to password
                    int validityInterval = validityIntervalString ? validityIntervalString as int : 0
                    Date validTo = new Date(System.currentTimeMillis() + (validityInterval * 1000))
                    value = value + " " + validTo.getTime()
                }
                encryptedParams[name] = encryptor.encrypt(value)
            }
        }
        return encryptedParams
    }

    void setStandardParams(List standardParams) {
        this.standardParams = standardParams
    }
}
