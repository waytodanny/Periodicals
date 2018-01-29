package com.periodicals.utils.encryption;

import java.security.NoSuchAlgorithmException;

/**
 * @author Daniel Volnitsky 12.01.2018
 *
 * Interface for classes that have to encrypt something
 */
public interface Cryptographer {
    /**
     * @return encrypted string
     */
    String encrypt(String str) throws NoSuchAlgorithmException;
}
