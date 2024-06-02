package com.example.whatsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsapp.databinding.ItemChatsBinding;

import java.util.ArrayList;

public class RecyclerAdapterChat extends RecyclerView.Adapter<RecyclerAdapterChat.Holder>{
    private onItemClick onItemClick;
    public void setOnItemClick(RecyclerAdapterChat.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    private ArrayList<ModelUser> info;
    public void setInfo(ArrayList<ModelUser> info)  {
        this.info = info;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatsBinding binding = ItemChatsBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(info.get(position));
    }

    @Override
    public int getItemCount() {
        return info != null? info.size() : 0 ;
    }

    class Holder extends RecyclerView.ViewHolder{

        private ItemChatsBinding binding;

        public Holder(ItemChatsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClick != null){
                        onItemClick.onClick(info.get(getLayoutPosition()).getName()
                                ,info.get(getLayoutPosition()).getImage()
                                ,info.get(getLayoutPosition()).getuId());
                    }
                }
            });

        }
        public void bind(ModelUser modelUser){
            binding.nameUser.setText(modelUser.getName());
            Glide.with(binding.getRoot().getContext())
                    .load(modelUser.getImage())
                    .placeholder(R.drawable.profile_1)
                    .into(binding.imageUser);
        }
    }

    interface onItemClick{
        void onClick(String name,String img,String uId);
    }
}


