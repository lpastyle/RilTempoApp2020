package com.example.riltempoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TempoDatesAdapter extends RecyclerView.Adapter<TempoDatesAdapter.ViewHolder> {

    private List<TempoDate> tempoDates;
    private Context context;

    public TempoDatesAdapter(Context context, List<TempoDate> tempoDates) {
        this.tempoDates = tempoDates;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tempo_date_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        {
            TempoDate curItem = tempoDates.get(position);
            holder.dateTv.setText(curItem.getDate());
            holder.colorFl.setBackgroundColor(context.getColor(curItem.getCouleur().getResId()));
        }
    }


    @Override
    public int getItemCount() {
        return tempoDates.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTv;
        FrameLayout colorFl;

        public ViewHolder(View v) {
            super(v);
            dateTv = v.findViewById(R.id.date_tv);
            colorFl = v.findViewById(R.id.color_fl);
        }
    }


}

