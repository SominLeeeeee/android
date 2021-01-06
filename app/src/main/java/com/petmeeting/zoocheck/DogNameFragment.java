package com.petmeeting.zoocheck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class DogNameFragment extends Fragment {

    public static DogNameFragment newInstance(){
        return new DogNameFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dog_name_fragment,null);
        Button next_button = (Button)view.findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((DogRegister)getActivity()).replaceFragment(DogCharacterFragment.newInstance());
            }
        });
        return view;
    }


}
