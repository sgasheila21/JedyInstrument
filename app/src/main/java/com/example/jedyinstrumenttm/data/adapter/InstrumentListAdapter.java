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
import com.example.jedyinstrumenttm.data.DB;
import com.example.jedyinstrumenttm.data.model.Instrument;

public class InstrumentListAdapter extends RecyclerView.Adapter<InstrumentListAdapter.InstrumentViewHolder>{
    Context ctx;
    public int userID;

    public InstrumentListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public InstrumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.template_instrument_item, null, false);
        return new InstrumentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrumentViewHolder holder, int position) {
        Instrument i = DB.listInstruments.get(position);

        View v = holder.itemView;

        ImageView imgInstrumentPath = v.findViewById(R.id.imgInstrumentPath);
        TextView txtInstrumentName = v.findViewById(R.id.txtInstrumentName);

        int drawableID = this.ctx.getResources().getIdentifier(i.instrumentImagePath, "drawable", ctx.getPackageName());
        imgInstrumentPath.setImageResource(drawableID);

        txtInstrumentName.setText(i.instrumentName);

        imgInstrumentPath.setOnClickListener(e -> {
            Intent detailIntent = new Intent(ctx, DetailInstrument.class);

            detailIntent.putExtra("UserID",userID);
            detailIntent.putExtra("instrumentID",i.instrumentID);
            detailIntent.putExtra("instrumentName",i.instrumentName);
            detailIntent.putExtra("instrumentImagePath",i.instrumentImagePath);
            detailIntent.putExtra("instrumentRating",i.instrumentRating);
            detailIntent.putExtra("instrumentPrice",i.instrumentPrice);
            detailIntent.putExtra("instrumentDescription",i.instrumentDescription);

            ctx.startActivity(detailIntent);
        });

        txtInstrumentName.setOnClickListener(e -> {
            Intent detailIntent = new Intent(ctx, DetailInstrument.class);

            detailIntent.putExtra("UserID",userID);
            detailIntent.putExtra("instrumentID",i.instrumentID);
            detailIntent.putExtra("instrumentName",i.instrumentName);
            detailIntent.putExtra("instrumentImagePath",i.instrumentImagePath);
            detailIntent.putExtra("instrumentRating",i.instrumentRating);
            detailIntent.putExtra("instrumentPrice",i.instrumentPrice);
            detailIntent.putExtra("instrumentDescription",i.instrumentDescription);

            ctx.startActivity(detailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return DB.listInstruments.size();
    }

    static class InstrumentViewHolder extends RecyclerView.ViewHolder {
        public InstrumentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
