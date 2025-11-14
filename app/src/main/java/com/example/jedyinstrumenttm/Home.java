package com.example.jedyinstrumenttm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jedyinstrumenttm.data.DB;
import com.example.jedyinstrumenttm.data.adapter.InstrumentListAdapter;
import com.example.jedyinstrumenttm.data.model.Instrument;
import com.example.jedyinstrumenttm.data.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends AppCompatActivity {

    RecyclerView rvInstrumentList;
    InstrumentListAdapter instrumentAdapter;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();
        userID = i.getIntExtra("UserID", 0);

        rvInstrumentList = findViewById(R.id.rvInstrumentList);

        rvInstrumentList.setLayoutManager(new LinearLayoutManager(this));
        instrumentAdapter = new InstrumentListAdapter(this);
        rvInstrumentList.setAdapter(instrumentAdapter);
        instrumentAdapter.userID = userID;

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mocki.io/v1/99b969d3-0cf6-4061-8418-b9026a70b775";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray instrumentArray = response.getJSONArray("instruments");

                        for(int n=0;n<instrumentArray.length();n++) {
                            try {
                                JSONObject obj = instrumentArray.getJSONObject(n);
                                String Name = obj.getString("name");
                                double Rating = Double.parseDouble(obj.getString("rating"));
                                double Price = Double.parseDouble(obj.getString("price"));
                                String Image = obj.getString("image");
                                String Description = obj.getString("description");
                                String modifiedString = Description.replace("'", " ");
                                String msg = String.format("Obj-%s: %s,%s,%s,%s.", Name, Rating, Price, Image, modifiedString);

                                Log.d("VOLLEY",msg);

                                DB.insertDataInstrument(Name, Rating, Price, Image, Description, this);

                            } catch (Exception e) {
                                e.printStackTrace();
                                throw new RuntimeException(e);
                            }
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {
                    Log.e("VOLLEY",error.getMessage());
                    Toast.makeText(this,error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        DB.syncData(this);

        for (Instrument instrument:DB.listInstruments) {
            Log.d("print",instrument.instrumentName);
        }

        queue.add(request);
        queue.start();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.jedy_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.homeMenu:
                Intent homeIntent = new Intent(this, Home.class);
                homeIntent.putExtra("UserID", userID);
                startActivity(homeIntent);
                break;

            case R.id.aboutUsMenu:
                Intent aboutUsIntent = new Intent(this, AboutUs.class);
                aboutUsIntent.putExtra("UserID", userID);
                startActivity(aboutUsIntent);
                break;

            case R.id.activeOrderMenu:
                Intent activeOrderIntent = new Intent(this, ActiveOrder.class);
                activeOrderIntent.putExtra("UserID", userID);
                startActivity(activeOrderIntent);
                break;

            case R.id.profileMenu:
                Intent profileIntent = new Intent(this, ProfilePage.class);
                profileIntent.putExtra("UserID", userID);
                startActivity(profileIntent);
                break;

            case R.id.logOut:
                Intent logoutIntent = new Intent(this, Login.class);
                userID = 0;
                logoutIntent.putExtra("UserID", userID);
                startActivity(logoutIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}