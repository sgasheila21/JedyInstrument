package com.example.jedyinstrumenttm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jedyinstrumenttm.data.DB;
import com.example.jedyinstrumenttm.data.adapter.ActiveOrderListAdapter;

import java.text.DecimalFormat;

public class UpdateOrder extends AppCompatActivity {

    ImageView imgInstrumentPath;
    EditText edQuantity;
    TextView txtInstrumentName, txtTotalPrice;
    Button btnMinus, btnPlus, btnDelete, btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);

        Intent i = getIntent();
        int userID = i.getIntExtra("UserID", 0);
        int instrumentID = i.getIntExtra("instrumentID",0);
        int activeOrderID = i.getIntExtra("activeOrderID",0);
        int orderQTY = i.getIntExtra("orderQTY",0);
        String instrumentName = i.getStringExtra("instrumentName");
        int instrumentImagePath = i.getIntExtra("instrumentImagePath",0);
        Double instrumentPrice = i.getDoubleExtra("instrumentPrice",0);

        txtInstrumentName = findViewById(R.id.txtInstrumentName);
        imgInstrumentPath = findViewById(R.id.imgInstrumentPath);
        edQuantity = findViewById(R.id.edQuantity);

        txtInstrumentName.setText(instrumentName);
        imgInstrumentPath.setImageResource(instrumentImagePath);
        edQuantity.setText(String.valueOf(orderQTY));

        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtTotalPrice.setText("Total Price: Rp " + new DecimalFormat("#,##0").format(orderQTY * instrumentPrice));

        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnDelete = findViewById(R.id.btnDelete);
        btnDone = findViewById(R.id.btnDone);

        btnMinus.setOnClickListener(v -> {
            int new_quantity = new Integer(edQuantity.getText().toString()).intValue() - 1;
            edQuantity.setText(String.valueOf(new_quantity));

            if(new_quantity <= 0){
                Toast.makeText(this, "Quantity must be number and more than 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalPrice = new_quantity * instrumentPrice;
            txtTotalPrice.setText("Total Price: Rp " + new DecimalFormat("#,##0").format(totalPrice));

            DB.updateQuantityCart(userID,instrumentID,new_quantity,this);
            Toast.makeText(this, "Success update instrument to cart!", Toast.LENGTH_SHORT).show();
        });

        btnPlus.setOnClickListener(v -> {
            int new_quantity = new Integer(edQuantity.getText().toString()).intValue() + 1;
            edQuantity.setText(String.valueOf(new_quantity));

            if(new_quantity <= 0){
                Toast.makeText(this, "Quantity must be number and more than 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalPrice = new_quantity * instrumentPrice;
            txtTotalPrice.setText("Total Price: Rp " + new DecimalFormat("#,##0").format(totalPrice));

            DB.updateQuantityCart(userID,instrumentID,new_quantity,this);
            Toast.makeText(this, "Success update instrument to cart!", Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener(view -> {
            DB.deleteCart(activeOrderID, this);

            Intent goListOrder = new Intent(this, ActiveOrder.class);
            goListOrder.putExtra("UserID", userID);
            startActivity(goListOrder);

            Toast.makeText(this, "Success delete instrument from cart!", Toast.LENGTH_SHORT).show();
        });

        btnDone.setOnClickListener(view -> {
            Intent goListOrder = new Intent(this, ActiveOrder.class);
            goListOrder.putExtra("UserID", userID);
            startActivity(goListOrder);
        });
    }
}