package com.tonyhanma.class1;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText emailInput;
    private TextView firstNameResult;
    private TextView lastNameResult;
    private TextView emailResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameInput = findViewById(R.id.edtTxtFirstName);
        lastNameInput = findViewById(R.id.edtTxtLastName);
        emailInput = findViewById(R.id.edtTxtEmail);
        firstNameResult = findViewById(R.id.txtFirstName);
        lastNameResult = findViewById(R.id.txtLastName);
        emailResult = findViewById(R.id.txtEmail);

        findViewById(R.id.btnRegister).setOnClickListener(v -> showRegistration());
    }

    private void showRegistration() {
        firstNameResult.setText(getString(R.string.first_name_result, textOf(firstNameInput)));
        lastNameResult.setText(getString(R.string.last_name_result, textOf(lastNameInput)));
        emailResult.setText(getString(R.string.email_result, textOf(emailInput)));
    }

    private static String textOf(EditText input) {
        return input.getText().toString().trim();
    }
}
