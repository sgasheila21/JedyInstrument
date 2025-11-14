package com.example.jedyinstrumenttm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jedyinstrumenttm.data.DB;
import com.example.jedyinstrumenttm.data.model.User;

public class Register extends AppCompatActivity {

    EditText edUsername, edPhoneNumber, edEmail, edPassword;
    TextView goLogin;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.edUsername);
        edPhoneNumber = findViewById(R.id.edPhoneNumber);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view -> {
            String strUsername = edUsername.getText().toString();
            String strPhoneNumber = edPhoneNumber.getText().toString();
            String strEmail = edEmail.getText().toString();
            String strPassword = edPassword.getText().toString();

            for (int i = 0; i <  DB.listUsers.size(); i++){
                String email = DB.listUsers.get(i).email;
                if ((email.equals(strEmail))){
                    Toast.makeText(this, "Email already registered!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if(strUsername.isEmpty()){
                Toast.makeText(this, "Username must be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(strPhoneNumber.isEmpty()){
                Toast.makeText(this, "Phone Number must be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(strEmail.isEmpty()){
                Toast.makeText(this, "Email must be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(strPassword.isEmpty()){
                Toast.makeText(this, "Password must be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(strPassword.length() < 5){
                Toast.makeText(this, "Password must be at least five characters!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!strEmail.endsWith(".com")){
                Toast.makeText(this, "Email must end with '.com'!", Toast.LENGTH_SHORT).show();
                return;
            }

            DB.insertDataUser(strUsername, strEmail, strPassword, strPhoneNumber, this);

            Intent login = new Intent(this, Login.class);
            startActivity(login);

            //for checking
            Log.d("LOG REGISTER", "Email: " + DB.listUsers.get(DB.listUsers.size()-1).email + " Password: " + DB.listUsers.get(DB.listUsers.size()-1).password);
        });

        goLogin = findViewById(R.id.goLogin);
        goLogin.setOnClickListener(view -> {
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        });
    }
}