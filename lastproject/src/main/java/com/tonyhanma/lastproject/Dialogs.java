package com.tonyhanma.lastproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;

/** Diálogos que comparten el registro, el perfil y el carrito. */
final class Dialogs {

    interface OnDatePicked {
        void onPicked(String date);
    }

    private Dialogs() {
    }

    /** El "Are You Sure ?" de la app: solo ejecuta la acción si se pulsa Yes. */
    static void confirm(Context context, Runnable onYes) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.are_you_sure);
        dialog.findViewById(R.id.yes).setOnClickListener(v -> {
            dialog.dismiss();
            onYes.run();
        });
        dialog.findViewById(R.id.no).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    /** Elige Male/Female y lo escribe en el campo de género. */
    static void pickGender(Context context, EditText target) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.gender_layout);
        dialog.setCancelable(false);
        RadioGroup genders = dialog.findViewById(R.id.genderRadioGroup);
        genders.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.genderMale) {
                target.setText(R.string.male);
            } else if (checkedId == R.id.genderFemale) {
                target.setText(R.string.female);
            }
        });
        dialog.findViewById(R.id.genderButtonConfirm).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    /** Calendario que empieza en hoy y devuelve la fecha como d/m/aaaa. */
    static void pickDate(Context context, OnDatePicked listener) {
        Calendar today = Calendar.getInstance();
        new DatePickerDialog(context,
                (view, year, month, dayOfMonth) -> listener.onPicked(dayOfMonth + "/" + (month + 1) + "/" + year),
                today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    static void pickDate(Context context, EditText target) {
        pickDate(context, target::setText);
    }

    /** Muestra una de las fichas de descripción de producto. */
    static void showLayout(Context context, int layout) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(layout);
        dialog.setCancelable(true);
        dialog.show();
    }
}
