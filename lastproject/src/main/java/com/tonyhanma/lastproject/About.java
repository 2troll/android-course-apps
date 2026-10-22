package com.tonyhanma.lastproject;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class About extends AppCompatActivity {

    private final ActivityResultLauncher<String> callPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    callStore();
                } else {
                    permissionDenied();
                }
            });

    private final ActivityResultLauncher<String> smsPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    showSmsDialog();
                } else {
                    permissionDenied();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findViewById(R.id.aboutfacebook).setOnClickListener(v -> open(Uri.parse(Store.FACEBOOK_URL)));
        findViewById(R.id.aboutinstagram).setOnClickListener(v -> open(Uri.parse(Store.INSTAGRAM_URL)));
        findViewById(R.id.aboutwaze).setOnClickListener(v -> openWaze());
        findViewById(R.id.aboutphone).setOnClickListener(v -> withPermission(Manifest.permission.CALL_PHONE, callPermission, this::callStore));
        findViewById(R.id.aboutsms).setOnClickListener(v -> withPermission(Manifest.permission.SEND_SMS, smsPermission, this::showSmsDialog));
        findViewById(R.id.aboutemail).setOnClickListener(v -> showEmailDialog());
        findViewById(R.id.aboutback).setOnClickListener(v -> finish());
    }

    private void withPermission(String permission, ActivityResultLauncher<String> launcher, Runnable action) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            action.run();
        } else {
            launcher.launch(permission);
        }
    }

    private void openWaze() {
        // El enlace web abre Waze si está instalado y, si no, el navegador
        open(Uri.parse("https://waze.com/ul?ll=" + Store.LATITUDE + "," + Store.LONGITUDE + "&navigate=yes"));
    }

    private void callStore() {
        open(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Store.PHONE)));
    }

    private void showSmsDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.sms_contact);
        EditText message = dialog.findViewById(R.id.smset);
        dialog.findViewById(R.id.smsbtn).setOnClickListener(v -> {
            String text = Forms.textOf(message);
            dialog.dismiss();
            if (text.isEmpty()) {
                Toast.makeText(this, R.string.fill_message, Toast.LENGTH_SHORT).show();
                return;
            }
            SmsManager sms = ContextCompat.getSystemService(this, SmsManager.class);
            if (sms != null) {
                sms.sendTextMessage(Store.PHONE, null, text, null, null);
                Toast.makeText(this, R.string.sms_sent, Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }

    private void showEmailDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.email_contact);
        EditText subject = dialog.findViewById(R.id.aboutSubject);
        EditText message = dialog.findViewById(R.id.aboutMessage);
        dialog.findViewById(R.id.sendEmailA).setOnClickListener(v -> {
            if (Forms.anyEmpty(subject, message)) {
                Toast.makeText(this, R.string.fill_message, Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
                    .putExtra(Intent.EXTRA_EMAIL, new String[]{Store.EMAIL})
                    .putExtra(Intent.EXTRA_SUBJECT, Forms.textOf(subject))
                    .putExtra(Intent.EXTRA_TEXT, Forms.textOf(message));
            dialog.dismiss();
            open(intent);
        });
        dialog.show();
    }

    private void open(Uri uri) {
        open(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void open(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.no_app_found, Toast.LENGTH_SHORT).show();
        }
    }

    private void permissionDenied() {
        Toast.makeText(this, R.string.permission_needed, Toast.LENGTH_SHORT).show();
    }
}
