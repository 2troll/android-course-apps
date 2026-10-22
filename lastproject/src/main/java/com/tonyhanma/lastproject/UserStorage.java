package com.tonyhanma.lastproject;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/** Una sola cuenta local, como en la versión original, pero con la contraseña cifrada con sal. */
final class UserStorage {

    private static final String PREFS = "user";
    private static final String KEY_NAME = "name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_BIRTH_DATE = "birthDate";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_SALT = "salt";
    private static final String KEY_PASSWORD_HASH = "passwordHash";

    private final SharedPreferences prefs;

    UserStorage(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    @Nullable
    User load() {
        if (!prefs.contains(KEY_USERNAME)) {
            return null;
        }
        return new User(
                prefs.getString(KEY_NAME, ""),
                prefs.getString(KEY_USERNAME, ""),
                prefs.getString(KEY_EMAIL, ""),
                prefs.getString(KEY_BIRTH_DATE, ""),
                prefs.getString(KEY_PHONE, ""),
                prefs.getString(KEY_GENDER, ""));
    }

    /** Guarda los datos; con password nulo se conserva la contraseña anterior. */
    void save(User user, @Nullable String password) {
        SharedPreferences.Editor editor = prefs.edit()
                .putString(KEY_NAME, user.name)
                .putString(KEY_USERNAME, user.username)
                .putString(KEY_EMAIL, user.email)
                .putString(KEY_BIRTH_DATE, user.birthDate)
                .putString(KEY_PHONE, user.phone)
                .putString(KEY_GENDER, user.gender);
        if (password != null) {
            String salt = newSalt();
            editor.putString(KEY_SALT, salt).putString(KEY_PASSWORD_HASH, hash(salt, password));
        }
        editor.apply();
    }

    boolean checkLogin(String username, String password) {
        String salt = prefs.getString(KEY_SALT, null);
        String storedHash = prefs.getString(KEY_PASSWORD_HASH, null);
        if (salt == null || storedHash == null) {
            return false;
        }
        return username.equals(prefs.getString(KEY_USERNAME, null))
                && MessageDigest.isEqual(storedHash.getBytes(StandardCharsets.UTF_8),
                hash(salt, password).getBytes(StandardCharsets.UTF_8));
    }

    private static String newSalt() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return toHex(bytes);
    }

    private static String hash(String salt, String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return toHex(digest.digest((salt + password).getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is always available on Android", e);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}
