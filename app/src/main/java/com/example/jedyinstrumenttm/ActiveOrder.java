package com.example.jedyinstrumenttm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.jedyinstrumenttm.data.adapter.ActiveOrderListAdapter;
import com.example.jedyinstrumenttm.data.adapter.InstrumentListAdapter;

public class ActiveOrder extends AppCompatActivity {

    Button btnBackToHome;
    RecyclerView rvActiveOrder;
    ActiveOrderListAdapter activeOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_order);

        Intent i = getIntent();
        int userID = i.getIntExtra("UserID", 0);

        btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(view -> {
            Intent goHome = new Intent(this, Home.class);
            goHome.putExtra("UserID", userID);
            startActivity(goHome);
        });

        rvActiveOrder = findViewById(R.id.rvActiveOrder);

        rvActiveOrder.setLayoutManager(new LinearLayoutManager(this));
        activeOrderAdapter = new ActiveOrderListAdapter(this);
        rvActiveOrder.setAdapter(activeOrderAdapter);
        activeOrderAdapter.userID = userID;
    }
}