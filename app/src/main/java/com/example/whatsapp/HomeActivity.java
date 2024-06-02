package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.whatsapp.databinding.ActivityChatBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    ArrayList<ModelFragment> list = new ArrayList<>();
    ChatFragment chatsFragment;
    UpdatesFragment updatesFragment;
    CallsFragment callsFragment;
    FragmentMyPagerAdapter fragmentMyPagerAdapter;
    private ActivityChatBinding binding;
    FirebaseAuth auth = FirebaseAuth
            .getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentMyPagerAdapter = new FragmentMyPagerAdapter(this,list);

        updatesFragment = new UpdatesFragment();
        chatsFragment = new ChatFragment();
        callsFragment = new CallsFragment();

        list.add(new ModelFragment(chatsFragment,"Chats"));
        list.add(new ModelFragment(updatesFragment,"Updates"));
        list.add(new ModelFragment(callsFragment,"Calls"));


        binding.viewPagerContainer.setAdapter(fragmentMyPagerAdapter);

        new TabLayoutMediator(binding.tabs, binding.viewPagerContainer, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(list.get(position).getTitle());
            }
        }).attach();
        setSupportActionBar(binding.appBatTv);
    }

    @Override
    public void onBackPressed() {
        if (!(chatsFragment.isVisible())){
            binding.tabs.selectTab(binding.tabs.getTabAt(0));
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chats_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            auth.signOut();
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}