package com.tonyhanma.lastproject;

import android.content.Context;
import android.content.SharedPreferences;

/** Cantidades del carrito, guardadas entre pantallas con una clave por producto. */
final class CartStorage {

    private static final String PREFS = "cart";
    private static final String KEY_COUNT = "count";

    private final SharedPreferences prefs;

    CartStorage(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    int[] load() {
        int[] counts = new int[Product.CATALOG.length];
        for (int i = 0; i < counts.length; i++) {
            counts[i] = prefs.getInt(KEY_COUNT + i, 0);
        }
        return counts;
    }

    void save(int[] counts) {
        SharedPreferences.Editor editor = prefs.edit();
        for (int i = 0; i < counts.length; i++) {
            editor.putInt(KEY_COUNT + i, counts[i]);
        }
        editor.apply();
    }

    void clear() {
        prefs.edit().clear().apply();
    }

    static int totalPrice(int[] counts) {
        int total = 0;
        for (int i = 0; i < counts.length; i++) {
            total += counts[i] * Product.CATALOG[i].price;
        }
        return total;
    }

    static int totalPieces(int[] counts) {
        int total = 0;
        for (int count : counts) {
            total += count;
        }
        return total;
    }
}
