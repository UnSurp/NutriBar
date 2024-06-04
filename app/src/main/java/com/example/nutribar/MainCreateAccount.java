package com.example.nutribar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class MainCreateAccount extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 71;
    boolean isMan;
    private Uri filePath;
    FirebaseStorage storage;
    ImageView imageView;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    DatabaseReference myRef = database.getReference("users/" + currentFirebaseUser.getUid());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create_account);

        imageView = findViewById(R.id.imgAva);
        Button btnLoadAva = findViewById(R.id.btnLoadAva);
        EditText editName = findViewById(R.id.editName);
        EditText editLastName = findViewById(R.id.editLastName);
        EditText editDate = findViewById(R.id.editDate);
        RadioGroup radioManWoman = findViewById(R.id.radios);

        Button btnCreate = findViewById(R.id.btnSave);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        radioManWoman.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioManWoman.getCheckedRadioButtonId() == -1) {
                    //ничего не выбрано
                } else {
                    if(radioManWoman.getCheckedRadioButtonId() == R.id.radioMan){
                        isMan = true;
                    }else{
                        isMan = false;
                    }
                }
            }
        });
        btnLoadAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String lastName = editLastName.getText().toString();
                String date = editDate.getText().toString();
                if(!name.isEmpty() && !lastName.isEmpty() && !date.isEmpty() && radioManWoman.getCheckedRadioButtonId() != -1){
                    myRef.child("name").setValue(name);
                    myRef.child("lastName").setValue(lastName);
                    myRef.child("date").setValue(date);
                    myRef.child("isMale").setValue(isMan);
                    if(filePath != null){
                        uploadImage();
                    }
                    Intent i = new Intent(getApplicationContext(), StatsListActivity.class);
                    startActivity(i);

                }
            }
        });









    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Выберите изображение"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Загрузка");
            progressDialog.show();

            StorageReference ref = storageReference.child("user_data/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    String stringUrl = downloadUrl.toString();
                                    myRef.child("icon").setValue(stringUrl);
                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(MainCreateAccount.this, "Загружено", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainCreateAccount.this, "Не удалось  "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Загружено "+(int)progress+"%");
                        }
                    });
        }
    }
}