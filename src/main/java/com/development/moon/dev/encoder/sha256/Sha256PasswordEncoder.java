package com.development.moon.dev.encoder.sha256;

import com.development.moon.dev.usercase.port.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Sha256PasswordEncoder is a class that implements the PasswordEncoder interface
 * to provide SHA-256 based password encoding and matching functionality.
 */
public class Sha256PasswordEncoder implements PasswordEncoder {

    private static final SecureRandom RANDOM = new SecureRandom();


    /**
     * Encodes the given string using SHA-256 with a generated salt.
     *
     * @param str the string to encode
     * @return the encoded string with the salt prefixed
     */
    @Override
    public String encode(String str) {
        String salt = generateSalt();
        return encodeWithSalt(str, salt);
    }

    /**
     * Encodes the given string using SHA-256 with the provided salt.
     *
     * @param str the string to encode
     * @param salt the salt to use for encoding
     * @return the encoded string with the salt prefixed
     */
    public String encodeWithSalt(String str, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((str + salt).getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return salt + ":" + hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a random salt.
     *
     * @return the generated salt as a Base64 encoded string
     */
    private String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Verifies if the raw password matches the encoded password.
     *
     * @param rawPassword the raw password to verify
     * @param encodedPassword the encoded password to compare against
     * @return true if the passwords match, false otherwise
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        String[] parts = encodedPassword.split(":");
        if (parts.length != 2) return false;

        String salt = parts[0];
        String hash = parts[1];
        return encodeWithSalt(rawPassword, salt).equals(encodedPassword);
    }
}