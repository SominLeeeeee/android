package com.petmeeting.zoocheck;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DogRegisterAdapter extends FragmentStateAdapter {
    public int mCount;
    public DogRegisterAdapter(@NonNull FragmentActivity fragmentActivity, int count) {
        super(fragmentActivity);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);
        switch(index){
            case 0 :
                return new DogRegisterFrag();

            case 1:
                return DogProfileFrag.newInstance(index+1);

            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position){ return position % mCount;}
}
