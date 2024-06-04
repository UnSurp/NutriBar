package com.example.nutribar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AddNewStatActivity extends AppCompatActivity {

    Button submit;
    EditText protein, feritin, magnesium, vitaminD, insulin;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("eng"));
    String formattedDate = currentDate.format(formatter);
    int day = currentDate.getDayOfMonth();
    int month = currentDate.getMonthValue();
    int year = currentDate.getYear();

    DatabaseReference myRef = database.getReference("users/" + currentFirebaseUser.getUid()+"/stats/" + formattedDate);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_stat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        submit = findViewById(R.id.submitBtn);
        protein = findViewById(R.id.editTextProtein);
        feritin = findViewById(R.id.editTextFeritin);
        magnesium = findViewById(R.id.editTextMagnesium);
        vitaminD = findViewById(R.id.editTextVitaminD);
        insulin = findViewById(R.id.editTextInsuin);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String proteinStr, feritinStr, magnesiumStr, vitaminDStr, insulinStr;
                proteinStr = protein.getText().toString();
                feritinStr = feritin.getText().toString();
                magnesiumStr = magnesium.getText().toString();
                vitaminDStr = vitaminD.getText().toString();
                insulinStr = insulin.getText().toString();
                Double pro = Double.valueOf(proteinStr);
                Double fer = Double.valueOf(feritinStr);
                Double mag = Double.valueOf(magnesiumStr);
                Double vit = Double.valueOf(vitaminDStr);
                Double ins = Double.valueOf(insulinStr);
                myRef.child("protein").setValue(pro);
                myRef.child("feritin").setValue(fer);
                myRef.child("magnesium").setValue(mag);
                myRef.child("vitaminD").setValue(vit);
                myRef.child("insulin").setValue(ins);
                myRef.child("day").setValue(day);
                myRef.child("month").setValue(month);
                myRef.child("year").setValue(year);
                Intent i = new Intent(getApplicationContext(), StatsListActivity.class);
                startActivity(i);
            }
        });



    }
}