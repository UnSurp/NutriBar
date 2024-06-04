package com.example.nutribar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StatInfoActivity extends AppCompatActivity {

    TextView fer, pro, mag, vitD, ins, date;
    ImageView ferIm, proIm, magIm, vitIm, insIm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stat_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        Stats stats = (Stats) intent.getParcelableExtra("stat");
        fer = findViewById(R.id.answerFer);
        pro = findViewById(R.id.answerProt);
        mag = findViewById(R.id.answeMag);
        vitD = findViewById(R.id.answeVitD);
        ins = findViewById(R.id.answerIns);
        date = findViewById(R.id.answeDate);
        ferIm = findViewById(R.id.ferIV);
        proIm = findViewById(R.id.proIV);
        magIm = findViewById(R.id.magIV);
        vitIm = findViewById(R.id.vitIV);
        insIm = findViewById(R.id.insIV);
        fer.setText(String.valueOf("Феритин: "+stats.getFeritin()));
        pro.setText(String.valueOf("Протеин: "+stats.getProtein()));
        mag.setText(String.valueOf("Магний: "+stats.getMagnesium()));
        vitD.setText(String.valueOf("Витамин D: "+stats.getVitaminD()));
        ins.setText(String.valueOf("Инсулин: "+stats.getInsulin()));
        date.setText("Дата: " + String.valueOf(stats.getDay()) + "." + String.valueOf(stats.getMonth()) + "." + String.valueOf(stats.getYear()));

    }
}