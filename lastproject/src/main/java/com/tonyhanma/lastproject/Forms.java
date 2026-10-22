package com.tonyhanma.lastproject;

import android.widget.EditText;

final class Forms {

    private Forms() {
    }

    static String textOf(EditText field) {
        return field.getText().toString().trim();
    }

    static boolean anyEmpty(EditText... fields) {
        for (EditText field : fields) {
            if (textOf(field).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
