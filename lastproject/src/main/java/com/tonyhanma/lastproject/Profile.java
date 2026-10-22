package com.tonyhanma.lastproject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class Profile extends ShopActivity {

    private EditText name;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText birthDate;
    private EditText phone;
    private EditText gender;
    private ImageView photo;

    private final ActivityResultLauncher<Void> takePhoto =
            registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
                if (bitmap != null) {
                    photo.setImageBitmap(bitmap);
                }
            });

    @Override
    protected int menuRes() {
        return R.menu.menu_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.prFName);
        username = findViewById(R.id.prUserName);
        email = findViewById(R.id.prEmail);
        password = findViewById(R.id.prPassword);
        birthDate = findViewById(R.id.prDate);
        phone = findViewById(R.id.prPhoneNumber);
        gender = findViewById(R.id.prGender);
        photo = findViewById(R.id.pr_Image);

        showUser(new UserStorage(this).load());
        // La contraseña ya no se puede mostrar (solo se guarda su hash): se deja vacía para conservarla
        password.setHint(R.string.password_keep_hint);

        findViewById(R.id.pr_select_date).setOnClickListener(v -> Dialogs.pickDate(this, birthDate));
        findViewById(R.id.pr_select_gender).setOnClickListener(v -> Dialogs.pickGender(this, gender));
        findViewById(R.id.pr_update_button).setOnClickListener(v -> Dialogs.confirm(this, this::update));
        photo.setOnClickListener(v -> takePhoto.launch(null));
    }

    private void showUser(User user) {
        if (user == null) {
            return;
        }
        name.setText(user.name);
        username.setText(user.username);
        email.setText(user.email);
        birthDate.setText(user.birthDate);
        phone.setText(user.phone);
        gender.setText(user.gender);
    }

    private void update() {
        if (Forms.anyEmpty(name, username, email, birthDate, phone, gender)) {
            Toast.makeText(this, R.string.fill_all_values, Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(Forms.textOf(name), Forms.textOf(username), Forms.textOf(email),
                Forms.textOf(birthDate), Forms.textOf(phone), Forms.textOf(gender));
        String newPassword = password.getText().toString();
        new UserStorage(this).save(user, newPassword.isEmpty() ? null : newPassword);
        Toast.makeText(this, R.string.profile_updated, Toast.LENGTH_SHORT).show();
        finish();
    }
}
