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

public class Login extends AppCompatActivity {

    EditText edEmail, edPassword;
    TextView goRegister;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //for checking
        Log.d("LOG LOGIN", "Email: " + DB.listUsers.get(DB.listUsers.size()-1).email + " Password: " + DB.listUsers.get(DB.listUsers.size()-1).password);

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {
            String strEmail = edEmail.getText().toString();
            String strPassword = edPassword.getText().toString();

            int flag = 0;

            for (int i = 0; i <  DB.listUsers.size(); i++){
                String email = DB.listUsers.get(i).email;
                String password = DB.listUsers.get(i).password;
                if ((email.equals(strEmail)) && (password.equals(strPassword))){
                    flag = 1;
                    Intent home = new Intent(this, Home.class);
                    home.putExtra("UserID", DB.listUsers.get(i).userID);
                    startActivity(home);
                } else if ((email.equals(strEmail)) && (!password.equals(strPassword))) {
                    Toast.makeText(this, "Password incorrect!", Toast.LENGTH_SHORT).show();
                    flag = 2;
                }
            }

            if(flag == 0){
                Toast.makeText(this, "User is not registered!", Toast.LENGTH_SHORT).show();
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
        });

        goRegister = findViewById(R.id.goRegister);
        goRegister.setOnClickListener(view -> {
            Intent register = new Intent(this, Register.class);
            startActivity(register);
        });
    }
}