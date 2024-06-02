package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase
            .getInstance()
            .getReference();
    FirebaseAuth auth = FirebaseAuth
            .getInstance();
    private ActivitySignupBinding binding;
    private ValidationHelper validationHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.textEmail.getText().toString().trim();
                String password = binding.textPassword.getText().toString().trim();
                validate(email,password);
            }
        });
    }
    private void validate(String email,String password){
        validationHelper = new ValidationHelper(email, password);
        if (!validationHelper.emailValidate()){
            binding.textEmail.setError(getString(R.string.Required));
        }else if (!validationHelper.passwordValidate()){
            binding.textPassword.setError(getString(R.string.Required));
        }else {
            Intent intent = new Intent(SignupActivity.this,ProfileActivity.class);
            intent.putExtra(Const.ConstEmail,email);
            intent.putExtra(Const.ConstPass,password);
            startActivity(intent);
            finish();
            binding.textEmail.setText("");
            binding.textPassword.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
        finish();
    }
}