package com.example.whatsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.whatsapp.databinding.ChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {
    private ChatBinding chatBinding;
    DatabaseReference databaseReference = FirebaseDatabase
            .getInstance()
            .getReference();
    FirebaseAuth auth = FirebaseAuth
            .getInstance();
    private ValidationHelper validationHelper;
    ValueEventListener valueEventListener;
    ChildEventListener childEventListener;
    RecyclerAdapterMessage recyclerAdapterMessage = new RecyclerAdapterMessage();
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatBinding = ChatBinding.inflate(getLayoutInflater());
        setContentView(chatBinding.getRoot());
        chatBinding.recyclerMsg.setAdapter(recyclerAdapterMessage);
        chatBinding.txtName.setText(getIntent().getStringExtra(Const.ConstName));
        String friendId = getIntent().getStringExtra(Const.ConstUserId);
        Glide.with(chatBinding.getRoot().getContext())
                .load(getIntent().getStringExtra(Const.ConstImage))
                .placeholder(R.drawable.profile_1)
                .into(chatBinding.profile);
        FindKey(friendId);
        chatBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = chatBinding.msgTxt.getText().toString();
                validate(message,friendId);
            }
        });

        chatBinding.imgArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void validate(String message,String friendId){
        if (message.isEmpty()){
            chatBinding.msgTxt.setError(getString(R.string.Required));
        }else {
            sendMessage(message,friendId);
        }
    }
    public void FindKey(String friendId){
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(auth.getUid().concat(friendId))){
                    key = auth.getUid().concat(friendId);
                    getMessage();
                }else if (snapshot.hasChild(friendId.concat(auth.getUid()))){
                    key = friendId.concat(auth.getUid());
                    getMessage();
                }else {
                    key = auth.getUid().concat(friendId);
                    getMessage();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
        databaseReference.child(Const.ConstMessage)
                .addListenerForSingleValueEvent(valueEventListener);
    }
    public void sendMessage(String message,String friendId){
        String id = databaseReference.push().getKey();
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(Const.ConstMessage)
                        .child(key)
                        .child(id)
                        .setValue(new ModelMessage(auth.getUid(), message, id));
                chatBinding.msgTxt.setText("");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
        databaseReference.child(Const.ConstMessage)
                .addListenerForSingleValueEvent(valueEventListener);
    }
    public void getMessage(){
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                recyclerAdapterMessage.addItem(snapshot.getValue(ModelMessage.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.child(Const.ConstMessage)
                .child(key)
                .addChildEventListener(childEventListener);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.child(Const.ConstMessage).removeEventListener(valueEventListener);
        databaseReference.child(Const.ConstMessage).child(key).removeEventListener(childEventListener);
    }
}