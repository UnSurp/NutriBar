package com.example.nutribar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainNoLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_no_login);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView linkToReg = (TextView) findViewById(R.id.linkToReg);

        Intent iLogin = new Intent(getApplicationContext(), MainLogin.class);
        Intent iReg = new Intent(getApplicationContext(), MainReg.class);



        linkToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(iReg);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iLogin);
                finish();
            }
        });

    }
}