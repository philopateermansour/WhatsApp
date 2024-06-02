package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class FragmentMyPagerAdapter extends FragmentStateAdapter {

    private ArrayList<ModelFragment> list;
    public FragmentMyPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<ModelFragment> list) {
        super(fragmentActivity);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return list.get(position).getFragment();
    }

    @Override
    public int getItemCount() {
        return list != null? list.size() : 0;
    }
}
