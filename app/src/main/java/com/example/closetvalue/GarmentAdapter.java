package com.example.closetvalue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GarmentAdapter extends RecyclerView.Adapter<GarmentAdapter.GarmentHolder> {
    private List<Garment> garments = new ArrayList<>();

    @NonNull
    @Override
    public GarmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.garment_item, parent, false);
        return new GarmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GarmentHolder holder, int position) {
        Garment currentGarment = garments.get(position);
        holder.textViewTitle.setText(currentGarment.getName());
        holder.textViewDescription.setText(currentGarment.getType());
        holder.textViewPriority.setText(String.valueOf(currentGarment.getUses()));
    }

    @Override
    public int getItemCount() {
        return garments.size();
    }

    public void setGarments(List<Garment> garments) {
        this.garments = garments;
        notifyDataSetChanged();
    }

    public Garment getGarmentAt(int position) {
        return garments.get(position);
    }

    class GarmentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public GarmentHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}