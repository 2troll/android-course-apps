package com.tonyhanma.lastproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private EditText name;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText birthDate;
    private EditText phone;
    private EditText gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.registerFName);
        username = findViewById(R.id.registerUserName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        birthDate = findViewById(R.id.registerDate);
        phone = findViewById(R.id.registerPhoneNumber);
        gender = findViewById(R.id.registerGender);

        findViewById(R.id.register_select_date).setOnClickListener(v -> Dialogs.pickDate(this, birthDate));
        findViewById(R.id.register_select_gender).setOnClickListener(v -> Dialogs.pickGender(this, gender));
        findViewById(R.id.register_back_btn).setOnClickListener(v -> finish());
        findViewById(R.id.register_btn).setOnClickListener(v -> Dialogs.confirm(this, this::register));
    }

    private void register() {
        if (Forms.anyEmpty(name, username, email, password, birthDate, phone, gender)) {
            Toast.makeText(this, R.string.fill_all_values, Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(Forms.textOf(name), Forms.textOf(username), Forms.textOf(email),
                Forms.textOf(birthDate), Forms.textOf(phone), Forms.textOf(gender));
        new UserStorage(this).save(user, password.getText().toString());
        Toast.makeText(this, R.string.register_success, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Login.class));
        finish();
    }
}
