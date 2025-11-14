package com.example.jedyinstrumenttm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class AboutUs extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    TextView txtCompanyDescription;
    Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Intent i = getIntent();
        int userID = i.getIntExtra("UserID", 0);

        txtCompanyDescription = findViewById(R.id.txtCompanyDescription);
        txtCompanyDescription.setText("Jedy Instrument is a company that sells various kinds of instrument. Currently, the company wants to expand their sells to be all over the world.");

        btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(view -> {
            Intent goHome = new Intent(this, Home.class);
            goHome.putExtra("UserID", userID);
            startActivity(goHome);
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //nentuiin posisi
        LatLng jedy = new LatLng(-6.20201,106.78113);

        //add marker
        googleMap.addMarker(new MarkerOptions()
                .position(jedy)
                .title("Jedy Instrument"));

        CameraPosition cameraPosition = new CameraPosition.Builder().
                target(jedy).
                zoom(20).
                build();

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(jedy));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}