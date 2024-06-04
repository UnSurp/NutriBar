package com.example.nutribar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText login;
    EditText passwordEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        login = findViewById(R.id.editEmail);
        passwordEd = findViewById(R.id.editPassword);
        mAuth = FirebaseAuth.getInstance();


        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView linkToReg = (TextView) findViewById(R.id.linkToReg1);

        Intent iToReg = new Intent(getApplicationContext(), MainReg.class);

        linkToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(iToReg);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });



    }

    private void loginUserAccount()
    {

        String email, password;
        email = login.getText().toString();
        password = passwordEd.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(getApplicationContext(), StatsListActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(),"Неудача", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}