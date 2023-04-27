package dk.abandonship.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {

    /**
     * Converts a password to a hash.
     * @param password The password to hash
     * @return The hash of the password.
     */
    public static String getPasswordHash(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    /**
     * Compares a password to a hash.
     * @param password The password you want to compare to the hash
     * @param passwordHash The hash to compare the password to
     * @return True if the password is correct. False otherwise.
     */
    public static boolean comparePasswordToHash(String password, String passwordHash) {
        return BCrypt.verifyer().verify(password.toCharArray(), passwordHash.toCharArray()).verified;
    }
}
