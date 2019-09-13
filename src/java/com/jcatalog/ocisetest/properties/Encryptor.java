package com.jcatalog.ocisetest.properties;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * Provides methods for String encryption/encoding and decoding/decryption.
 */
public class Encryptor {
    private static final String ALGORITHM_TYPE = "DES";
    private Base64 base64 = new Base64();
    private SecretKeySpec sks;
    private Cipher encryptionAlgorithm;
    private Cipher decryptionAlgorithm;

    public Encryptor(String inKey)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        sks = new SecretKeySpec(inKey.getBytes(), ALGORITHM_TYPE);

        encryptionAlgorithm = Cipher.getInstance(ALGORITHM_TYPE);
        encryptionAlgorithm.init(Cipher.ENCRYPT_MODE, sks);

        decryptionAlgorithm = Cipher.getInstance(ALGORITHM_TYPE);
        decryptionAlgorithm.init(Cipher.DECRYPT_MODE, sks);
    }

    /**
     * Ecrypts string with the secret key using  DES algorithm and then encode
     * with base64.
     *
     * @param decrypted - plain string string
     * @return encrypted/encoded string
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String encrypt(String decrypted)
            throws IllegalBlockSizeException, BadPaddingException {
        return new String(base64.encode(encryptionAlgorithm.doFinal(decrypted.getBytes())));
    }

    /**
     * Decodes string with base64  and then decrypt with the secret key using DES
     * algorithm.
     *
     * @param encrypted - encrypted and encoded string
     * @return decoded/decrypted string
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String decrypt(String encrypted)
            throws IllegalBlockSizeException, BadPaddingException {
        return new String(decryptionAlgorithm.doFinal((base64.decode(encrypted.getBytes()))));
    }
}
