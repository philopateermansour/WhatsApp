package com.example.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.whatsapp.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private ArrayList<ModelUser> list = new ArrayList<>();
    private FragmentChatBinding binding;
    private RecyclerAdapterChat adapterChat = new RecyclerAdapterChat();
    DatabaseReference reference = FirebaseDatabase
            .getInstance()
            .getReference();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentChatBinding.bind(view);
        adapterChat.setOnItemClick(new RecyclerAdapterChat.onItemClick() {
            @Override
            public void onClick(String name, String image,String uId) {
                Intent intent = new Intent(getActivity().getApplication(),ChatActivity.class);
                intent.putExtra(Const.ConstName,name);
                intent.putExtra(Const.ConstImage,image);
                intent.putExtra(Const.ConstUserId,uId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding= null;
    }

    private void getData(){

        reference.child(Const.ConstUser).addListenerForSingleValueEvent(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelUser modelUser = dataSnapshot.getValue(ModelUser.class);
                    if (!modelUser.getuId().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(modelUser);
                    }
                }
                adapterChat.setInfo(list);
                binding.recyclerChats.setAdapter(adapterChat);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}