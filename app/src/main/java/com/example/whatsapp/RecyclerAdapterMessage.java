package com.example.whatsapp;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.databinding.ItemMsgBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RecyclerAdapterMessage extends RecyclerView.Adapter<RecyclerAdapterMessage.holder> {
    FirebaseAuth auth = FirebaseAuth
            .getInstance();
    private ArrayList<ModelMessage> info = new ArrayList<>();
    public void addItem(ModelMessage message)  {
        info.add(message);
        notifyItemInserted(info.size()-1);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMsgBinding binding = ItemMsgBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.Bind(info.get(position));
    }

    @Override
    public int getItemCount() {
        return info != null? info.size() : 0 ;
    }

    class holder extends RecyclerView.ViewHolder{

        private ItemMsgBinding binding;

        public holder(ItemMsgBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void Bind(ModelMessage modelMessage){
            binding.message.setText(modelMessage.getMessage());
            if (modelMessage.getSenderId().equals(auth.getUid())){
                binding.relativelayout.setGravity(Gravity.END);
                binding.message.setBackgroundResource(R.drawable.rounded_edittext);
            }else {
                binding.relativelayout.setGravity(Gravity.START);
                binding.message.setBackgroundResource(R.drawable.rounded_edittext_green);
            }
        }
    }

}

