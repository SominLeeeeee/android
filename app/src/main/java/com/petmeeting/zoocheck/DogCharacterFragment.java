package com.petmeeting.zoocheck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class DogCharacterFragment extends Fragment {

    public static DogCharacterFragment newInstance(){
        return new DogCharacterFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dog_character_fragment,null);
        Button button = (Button)view.findViewById(R.id.next_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((DogRegister)getActivity()).replaceFragment(DogPhotoFragment.newInstance());
            }
        });
        return view;
    }


}
