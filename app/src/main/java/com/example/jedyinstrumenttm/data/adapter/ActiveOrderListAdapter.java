package com.example.jedyinstrumenttm.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.jedyinstrumenttm.DetailInstrument;
import com.example.jedyinstrumenttm.R;
import com.example.jedyinstrumenttm.UpdateOrder;
import com.example.jedyinstrumenttm.data.DB;
import com.example.jedyinstrumenttm.data.model.ActiveOrder;


import java.text.DecimalFormat;
import java.util.Vector;

public class ActiveOrderListAdapter extends RecyclerView.Adapter<ActiveOrderListAdapter.ActiveOrderViewHolder> {
    Context ctx;
    public int userID;
    public ActiveOrderListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ActiveOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.template_active_order, null, false);
        return new ActiveOrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveOrderListAdapter.ActiveOrderViewHolder holder, int position) {
        ActiveOrder i = DB.listActiveOrders.get(position);

        View v = holder.itemView;

        ImageView imgInstrumentPath = v.findViewById(R.id.imgInstrumentPath);
        TextView txtInstrumentName = v.findViewById(R.id.txtInstrumentName);

        TextView txtInstrumentTotalPrice = v.findViewById(R.id.txtInstrumentTotalPrice);
        TextView txtQuantity = v.findViewById(R.id.txtQuantity);


        int drawableID = this.ctx.getResources().getIdentifier(DB.listInstruments.get(i.instrumentID - 1).instrumentImagePath, "drawable", ctx.getPackageName());
        imgInstrumentPath.setImageResource(drawableID);

        txtInstrumentName.setText(DB.listInstruments.get(i.instrumentID - 1).instrumentName);
        int qty = DB.listActiveOrders.get(i.activeOrderID-1).instrumentQuantity;
        double price = DB.listInstruments.get(i.instrumentID-1).instrumentPrice;
        txtInstrumentTotalPrice.setText("Total Price: Rp " + String.valueOf(new DecimalFormat("#,##0").format(new Double(qty*price))));
        txtQuantity.setText("Qty: " + DB.listActiveOrders.get(i.activeOrderID-1).instrumentQuantity);

        imgInstrumentPath.setOnClickListener(e -> {
            Intent updateIntent = new Intent(ctx, UpdateOrder.class);

            updateIntent.putExtra("UserID",userID);
            updateIntent.putExtra("instrumentID",i.instrumentID);
            updateIntent.putExtra("activeOrderID", i.activeOrderID);
            updateIntent.putExtra("orderQTY", i.instrumentQuantity);
            updateIntent.putExtra("instrumentName",DB.listInstruments.get(i.instrumentID - 1).instrumentName);
            updateIntent.putExtra("instrumentImagePath",drawableID);
            updateIntent.putExtra("instrumentPrice",DB.listInstruments.get(i.instrumentID - 1).instrumentPrice);

            ctx.startActivity(updateIntent);
        });
    }

    @Override
    public int getItemCount() {
        return DB.listActiveOrders.size();
    }

    static class ActiveOrderViewHolder extends RecyclerView.ViewHolder {
        public ActiveOrderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static int getActiveOrderId (int userID, int instrumentID) {
        int ACTIVE_ID = 0;

        for (int i = 0; i < DB.listActiveOrders.size(); i++) {
            if(DB.listActiveOrders.get(i).userID == userID && DB.listActiveOrders.get(i).instrumentID == instrumentID){
                ACTIVE_ID = DB.listActiveOrders.get(i).activeOrderID;
                break;
            }
        }

        return ACTIVE_ID;
    }

    public static int getQuantityInCart (int activeOrderID) {
        int qty = 0;

        for (int i = 0; i < DB.listActiveOrders.size(); i++) {
            if(DB.listActiveOrders.get(i).activeOrderID == activeOrderID){
                qty = DB.listActiveOrders.get(i).instrumentQuantity;
                break;
            }
        }

        return qty;
    }
}
