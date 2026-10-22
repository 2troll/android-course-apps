package com.tonyhanma.lastproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);

        findViewById(R.id.loginButton).setOnClickListener(v -> logIn());
        findViewById(R.id.loginRegisterButton).setOnClickListener(v -> startActivity(new Intent(this, Register.class)));
        findViewById(R.id.infoLogin).setOnClickListener(v -> startActivity(new Intent(this, About.class)));
    }

    private void logIn() {
        String user = Forms.textOf(username);
        String pass = password.getText().toString();
        if (user.isEmpty() || pass.isEmpty() || !new UserStorage(this).checkLogin(user, pass)) {
            Toast.makeText(this, R.string.wrong_login, Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(new Intent(this, MainPage.class));
        finish();
    }
}
