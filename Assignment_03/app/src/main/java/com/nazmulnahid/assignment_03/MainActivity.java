package com.nazmulnahid.assignment_03;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, contactnoEditText, passwordEditText, confirmpassEdittext;
    private AutoCompleteTextView divisionDropdown;
    private Button submit, clear;
    private String name, email, contactno, pass, conpass, div;
    private LinearLayout inputLayout, outputLayout;
    private TextView outputText;

    private final Pattern namePattern = Pattern.compile("[a-z A-Z_.]+");
    private final Pattern emailPattern = Pattern.compile("^[a-z0-9]+@(gmail|yahoo|org)\\.com$");
    private final Pattern contactnoPattern = Pattern.compile("^[01][\\d]{10}$");
    private final Pattern passPattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        contactnoEditText = findViewById(R.id.contact);
        divisionDropdown = findViewById(R.id.spinner);
        submit = findViewById(R.id.buttonsubmit);
        inputLayout = findViewById(R.id.main);
        outputLayout = findViewById(R.id.outputlayout);
        outputText = findViewById(R.id.outputText);
        clear = findViewById(R.id.clear);
        passwordEditText = findViewById(R.id.password);
        confirmpassEdittext = findViewById(R.id.confirmpassword);

        // Setup divisions for AutoCompleteTextView
        String[] divisions = new String[]{"Dhaka", "Chottogram", "Rajshahi", "Khulna", "Sylhet", "Barishal", "Cumilla"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, divisions);
        divisionDropdown.setAdapter(adapter);

        // Submit button logic
        submit.setOnClickListener(v -> {
            name = nameEditText.getText().toString();
            email = emailEditText.getText().toString();
            contactno = contactnoEditText.getText().toString();
            pass = passwordEditText.getText().toString();
            conpass = confirmpassEdittext.getText().toString();
            div = divisionDropdown.getText().toString();

            if (validateInputs()) {
                outputLayout.setVisibility(View.VISIBLE);
                inputLayout.setVisibility(View.GONE);
                String result = "Name: " + name + "\nEmail: " + email + "\nContact no: " + contactno + "\nDivision: " + div;
                outputText.setText(result);

                // Clear button logic
                clear.setOnClickListener(v1 -> {
                    outputLayout.setVisibility(View.GONE);
                    inputLayout.setVisibility(View.VISIBLE);
                    clearFields();
                });
            }
        });
    }

    private boolean validateInputs() {
        if (name.isEmpty()) {
            nameEditText.setError("Empty");
            nameEditText.requestFocus();
            return false;
        } else if (!namePattern.matcher(name).matches()) {
            nameEditText.setError("Name should contain only alphabets!");
            nameEditText.requestFocus();
            return false;
        } else if (email.isEmpty()) {
            emailEditText.setError("Empty");
            emailEditText.requestFocus();
            return false;
        } else if (!emailPattern.matcher(email).matches()) {
            emailEditText.setError("Please provide a correct email!");
            emailEditText.requestFocus();
            return false;
        } else if (contactno.isEmpty()) {
            contactnoEditText.setError("Empty");
            contactnoEditText.requestFocus();
            return false;
        } else if (!contactnoPattern.matcher(contactno).matches()) {
            contactnoEditText.setError("Please provide a correct phone number!");
            contactnoEditText.requestFocus();
            return false;
        } else if (!passPattern.matcher(pass).matches()) {
            passwordEditText.setError("Password must contain uppercase, lowercase, digit, special character, and be at least 8 characters long.");
            passwordEditText.requestFocus();
            return false;
        } else if (!pass.equals(conpass)) {
            confirmpassEdittext.setError("Passwords do not match!");
            confirmpassEdittext.requestFocus();
            return false;
        } else if (div.isEmpty() || div.equals("Select Division")) {
            Toast.makeText(this, "Please select a division", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        nameEditText.setText("");
        emailEditText.setText("");
        contactnoEditText.setText("");
        passwordEditText.setText("");
        confirmpassEdittext.setText("");
        divisionDropdown.setText("");
    }
}
