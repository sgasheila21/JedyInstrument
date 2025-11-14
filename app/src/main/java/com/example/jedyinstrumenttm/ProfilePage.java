package com.example.jedyinstrumenttm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jedyinstrumenttm.data.DB;

public class ProfilePage extends AppCompatActivity {
    Button btnBackToHome;

    TextView userName, userEmail, userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        Intent i = getIntent();
        int userID = i.getIntExtra("UserID", 0);
        userName = findViewById(R.id.tvUserName);
        userEmail = findViewById(R.id.tvUserEmail);
        userPhone = findViewById(R.id.tvUserPhone);
        String name = null, email = null, phone = null;


        for (int n = 0; n <  DB.listUsers.size(); n++){
            if (DB.listUsers.get(n).userID == userID){
                email = DB.listUsers.get(n).email;
                name = DB.listUsers.get(n).username;
                phone =DB.listUsers.get(n).phoneNumber;
                break;
            }
        }
        
        userName.setText(name);
        userEmail.setText(email);
        userPhone.setText(phone);

        btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(view -> {
            Intent goHome = new Intent(this, Home.class);
            goHome.putExtra("UserID", userID);
            startActivity(goHome);
        });
    }

}