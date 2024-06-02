package com.example.whatsapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.databinding.ItemUpdatesBinding;

import java.util.ArrayList;

public class RecyclerAdapterUpgrades extends RecyclerView.Adapter<RecyclerAdapterUpgrades.statusHolder> {
    private ArrayList<ModelStatus> info;

    public void setInfo(ArrayList<ModelStatus> info) {
        this.info = info;
    }

    @NonNull
    @Override
    public statusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUpdatesBinding binding = ItemUpdatesBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new statusHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull statusHolder statusHolder, int position) {
        statusHolder.statusBind(info.get(position).getName(),info.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return info != null? info.size() : 0;
    }


    class statusHolder extends RecyclerView.ViewHolder{

        private ItemUpdatesBinding binding;

        public statusHolder(ItemUpdatesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void statusBind(String name,int img){
            binding.imgStatus.setImageResource(img);
            binding.name.setText(name);
        }
    }
}
