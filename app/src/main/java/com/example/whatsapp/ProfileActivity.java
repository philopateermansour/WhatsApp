package com.example.whatsapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    Uri fileImageUser;
    private ValidationHelper validationHelper;
    DatabaseReference databaseReference = FirebaseDatabase
            .getInstance()
            .getReference();
    FirebaseAuth auth = FirebaseAuth
            .getInstance();
    StorageReference storageReference = FirebaseStorage
            .getInstance()
            .getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String email = getIntent().getStringExtra(Const.ConstEmail);
        String password = getIntent().getStringExtra(Const.ConstPass);
        binding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                launcher.launch(intent);
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.userName.getText().toString();
                validate(email,password,name);
            }
        });
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if (data!=null){
                        fileImageUser = data.getData();
                        binding.profilePic.setImageURI(fileImageUser);
                    }
                }

            });
    private void validate(String email,String password,String name){
        validationHelper = new ValidationHelper(name);
        if (!validationHelper.nameValidate()){
            binding.userName.setError(getString(R.string.Required));
        } else if (fileImageUser == null) {
            Toast.makeText(ProfileActivity.this,getString(R.string.photo_error),Toast.LENGTH_SHORT).show();
        } else {
            register(email, password,name);
        }
    }
    private void register(String email,String password,String name){
        binding.progress.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                SendImageToStorage(email, name, authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProfileActivity.this,SignupActivity.class));
                finish();
            }
        });
    }
    private void SendImageToStorage(String email ,String name,String uId){
        if(fileImageUser != null){
            StorageReference reference = storageReference
                    .child("image/")
                    .child(uId+System.currentTimeMillis());
            reference.putFile(fileImageUser).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            sendDataToRealTime(email ,name ,uId ,uri.toString());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    binding.progress.setVisibility(View.GONE);
                    Toast.makeText(ProfileActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void sendDataToRealTime(String email,String name,String uID,String imageUrl){
        databaseReference
                .child(Const.ConstUser)
                .child(uID)
                .setValue(new ModelUser(email,name,uID,imageUrl))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        binding.progress.setVisibility(View.GONE);
                        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.progress.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}