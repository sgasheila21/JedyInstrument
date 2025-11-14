package com.example.jedyinstrumenttm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jedyinstrumenttm.data.DB;
import com.example.jedyinstrumenttm.data.adapter.ActiveOrderListAdapter;

import java.text.DecimalFormat;

public class DetailInstrument extends AppCompatActivity {

    ImageView imgInstrumentPath;
    TextView txtInstrumentName, txtInstrumentRating, txtInstrumentPrice, txtInstrumentDescription, txtTotalPrice;
    Button btnMinus, btnPlus, btnAddToCart;
    EditText edQuantity;
    int THIS_USER_ID;

    private static final int SMS_PERMISSION_REQUEST_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_instrument);

        Intent i = getIntent();

        imgInstrumentPath = findViewById(R.id.imgInstrumentPath);
        txtInstrumentName = findViewById(R.id.txtInstrumentName);
        txtInstrumentRating = findViewById(R.id.txtInstrumentRating);
        txtInstrumentPrice = findViewById(R.id.txtInstrumentPrice);
        txtInstrumentDescription = findViewById(R.id.txtInstrumentDescription);

        int drawableID = this.getResources().getIdentifier(i.getStringExtra("instrumentImagePath"), "drawable", this.getPackageName());
        imgInstrumentPath.setImageResource(drawableID);

        String name = i.getStringExtra("instrumentName");
        Double rating = i.getDoubleExtra("instrumentRating", 0);
        Double price = i.getDoubleExtra("instrumentPrice", 0);
        String desc = i.getStringExtra("instrumentDescription");

        txtInstrumentName.setText(name);
        txtInstrumentRating.setText(rating + "/5.0");
        txtInstrumentPrice.setText("Rp " + new DecimalFormat("#,##0").format(price));
        txtInstrumentDescription.setText(desc);

        edQuantity = findViewById(R.id.edQuantity);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        THIS_USER_ID = i.getIntExtra("UserID",0);
        int THIS_INSTRUMENT_ID = i.getIntExtra("instrumentID",0);
        int activeOrderID = ActiveOrderListAdapter.getActiveOrderId(THIS_USER_ID, THIS_INSTRUMENT_ID);
        int qtyInCart = ActiveOrderListAdapter.getQuantityInCart(activeOrderID);

        edQuantity.setText(String.valueOf(qtyInCart));
        txtTotalPrice.setText("Total Price: Rp " + String.valueOf(new DecimalFormat("#,##0").format((new Double(edQuantity.getText().toString()).intValue()) * price)));

        btnMinus.setOnClickListener(v -> {
            int quantity = new Integer(edQuantity.getText().toString()).intValue() - 1;
            edQuantity.setText(String.valueOf(quantity));

            double totalPrice = quantity * price;
            txtTotalPrice.setText("Total Price: Rp " + new DecimalFormat("#,##0").format(totalPrice));
        });

        btnPlus.setOnClickListener(v -> {
            int quantity = new Integer(edQuantity.getText().toString()).intValue() + 1;
            edQuantity.setText(String.valueOf(quantity));

            double totalPrice = quantity * price;
            txtTotalPrice.setText("Total Price: Rp " + new DecimalFormat("#,##0").format(totalPrice));
        });

        btnAddToCart.setOnClickListener(v -> {
            String quantity = edQuantity.getText().toString();
            int quantity_int = new Integer(quantity);

            if(quantity.isEmpty()){
                Toast.makeText(this, "Quantity must be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            if(quantity_int <= 0){
                Toast.makeText(this, "Quantity must be number and more than 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission not granted, request it
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
            } else {
                // Permission already granted, proceed with sending the SMS

            String phone = null;


            for (int n = 0; n <  DB.listUsers.size(); n++){
                if (DB.listUsers.get(n).userID == THIS_USER_ID){
                    phone =DB.listUsers.get(n).phoneNumber;
                    break;
                }
            }

                sendOrderDetails(quantity_int, price ,phone, name,  quantity);

            }
            if(activeOrderID == 0){
                DB.insertDataActiveOrder(THIS_USER_ID, THIS_INSTRUMENT_ID, quantity_int, this);
                Intent goHome = new Intent(this, Home.class);
                goHome.putExtra("UserID", THIS_USER_ID);
                startActivity(goHome);
                Toast.makeText(this, "Success add intrument to cart!", Toast.LENGTH_SHORT).show();
            }
            else {
                DB.updateQuantityCart(THIS_USER_ID, THIS_INSTRUMENT_ID, quantity_int, this);
                Intent goHome = new Intent(this, Home.class);
                goHome.putExtra("UserID", THIS_USER_ID);
                startActivity(goHome);
                Toast.makeText(this, "Success add intrument to cart!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendOrderDetails(int quantity_int, Double price, String phone, String name, String quantity) {
        double totalPrice = quantity_int * price;
        String phoneNumber = phone;

        String message = "Instrument Name: " + name +
                "\nQuantity: " + quantity +
                "\nTotal Price: Rp " + new DecimalFormat("#,##0").format(totalPrice);

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recreate();
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}