package com.example.whatsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.whatsapp.databinding.FragmentUpdatesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UpdatesFragment extends Fragment {

    private ArrayList<ModelStatus> statusInfo = new ArrayList<>();
    private FragmentUpdatesBinding binding;
    private RecyclerAdapterUpgrades adapterStatus = new RecyclerAdapterUpgrades();
    DatabaseReference reference = FirebaseDatabase
            .getInstance()
            .getReference();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserImage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_updates, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentUpdatesBinding.bind(view);
        adapterStatus.setInfo(statusInfo);
        binding.recyclerStatus.setAdapter(adapterStatus);
    }

    private void getUserImage(){

        reference.child(Const.ConstUser).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            ModelUser modelUser = dataSnapshot.getValue(ModelUser.class);
                            if (modelUser.getuId().equals(FirebaseAuth.getInstance().getUid())){
                                Glide.with(binding.getRoot().getContext())
                                        .load(modelUser.getImage())
                                        .placeholder(R.drawable.profile_1)
                                        .into(binding.imgAddStatus);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}