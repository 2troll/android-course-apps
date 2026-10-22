package com.tonyhanma.lastproject;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Locale;

public class Cart extends ShopActivity {

    private static final int[] PIECES = {R.id.pict1, R.id.pict2, R.id.pict3, R.id.pict4, R.id.pict5, R.id.pict6};
    private static final int[] PRICES = {R.id.picts1, R.id.picts2, R.id.picts3, R.id.picts4, R.id.picts5, R.id.picts6};

    private String pickedDate = "";
    private String pickedTime = "";

    // Si el usuario acepta el permiso se avisa a la tienda por SMS; si no, el pedido sigue por correo
    private final ActivityResultLauncher<String> smsPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    notifyStoreBySms();
                }
                sendOrderEmail();
            });

    @Override
    protected int menuRes() {
        return R.menu.menu_cart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        int[] counts = new CartStorage(this).load();
        for (int i = 0; i < Product.CATALOG.length; i++) {
            this.<TextView>findViewById(PIECES[i]).setText(getString(R.string.pieces_amount, counts[i]));
            this.<TextView>findViewById(PRICES[i]).setText(getString(R.string.price_amount, counts[i] * Product.CATALOG[i].price));
        }
        this.<TextView>findViewById(R.id.totalpic).setText(getString(R.string.pieces_amount, CartStorage.totalPieces(counts)));
        this.<TextView>findViewById(R.id.totalprice).setText(getString(R.string.price_amount, CartStorage.totalPrice(counts)));

        findViewById(R.id.CartPickTime).setOnClickListener(v -> pickTime());
        findViewById(R.id.CartPickDate).setOnClickListener(v -> Dialogs.pickDate(this, date -> pickedDate = date));
        findViewById(R.id.CartCheckout).setOnClickListener(v -> checkout());
    }

    private void pickTime() {
        Calendar now = Calendar.getInstance();
        new TimePickerDialog(this,
                (view, hour, minute) -> pickedTime = String.format(Locale.US, "%02d:%02d", hour, minute),
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
                .show();
    }

    private void checkout() {
        if (pickedTime.isEmpty()) {
            Toast.makeText(this, R.string.pick_time_first, Toast.LENGTH_SHORT).show();
            return;
        }
        if (pickedDate.isEmpty()) {
            Toast.makeText(this, R.string.pick_date_first, Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, R.string.thanks_for_ordering, Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            notifyStoreBySms();
            sendOrderEmail();
        } else {
            smsPermission.launch(Manifest.permission.SEND_SMS);
        }
    }

    private String orderMessage() {
        return getString(R.string.order_message, pickedDate, pickedTime);
    }

    private void notifyStoreBySms() {
        SmsManager sms = ContextCompat.getSystemService(this, SmsManager.class);
        if (sms != null) {
            sms.sendTextMessage(Store.PHONE, null, orderMessage(), null, null);
        }
    }

    private void sendOrderEmail() {
        User user = new UserStorage(this).load();
        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType("message/rfc822")
                .putExtra(Intent.EXTRA_EMAIL, new String[]{user == null ? "" : user.email})
                .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_subject))
                .putExtra(Intent.EXTRA_TEXT, orderMessage());
        try {
            startActivity(Intent.createChooser(intent, null));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.no_app_found, Toast.LENGTH_SHORT).show();
        }
    }
}
