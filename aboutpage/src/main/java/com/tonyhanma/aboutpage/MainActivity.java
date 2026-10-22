package com.tonyhanma.aboutpage;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Datos de ejemplo de la tienda: cámbialos por los reales antes de usar la app
    private static final String STORE_PHONE = "0000000000";
    private static final String STORE_EMAIL = "store@example.com";
    private static final String STORE_FACEBOOK_URL = "https://www.facebook.com/store";
    private static final String STORE_LOCATION_QUERY = "store location";

    private EditText emailSubject;
    private EditText emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailSubject = findViewById(R.id.editTextEmailSubject);
        emailText = findViewById(R.id.editTextEmailText);

        findViewById(R.id.imageViewWaze).setOnClickListener(v -> openWaze());
        findViewById(R.id.imageViewPhone).setOnClickListener(v -> callStore());
        findViewById(R.id.imageViewFacebook).setOnClickListener(v -> openFacebook());
        findViewById(R.id.imageViewSMS).setOnClickListener(v -> sendSms());
        findViewById(R.id.buttonSendEmail).setOnClickListener(v -> sendEmail());
    }

    private void openWaze() {
        // El enlace web abre Waze si está instalado y el navegador si no, sin cerrar la app
        Uri uri = Uri.parse("https://waze.com/ul?q=" + Uri.encode(STORE_LOCATION_QUERY));
        startSafely(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void callStore() {
        // ACTION_DIAL abre el marcador con el número puesto y no necesita permiso de llamada
        startSafely(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + STORE_PHONE)));
    }

    private void openFacebook() {
        startSafely(new Intent(Intent.ACTION_VIEW, Uri.parse(STORE_FACEBOOK_URL)));
    }

    private void sendSms() {
        // El usuario ve y confirma el SMS en su app de mensajes: sin permiso SEND_SMS
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + STORE_PHONE));
        intent.putExtra("sms_body", getString(R.string.sms_default_message));
        startSafely(intent);
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{STORE_EMAIL});
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, emailText.getText().toString());
        startSafely(intent);
    }

    private void startSafely(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.no_app_found, Toast.LENGTH_SHORT).show();
        }
    }
}
