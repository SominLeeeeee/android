package com.petmeeting.zoocheck;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class DogNameFragment extends Fragment {

    private Context context;

    public static DogNameFragment newInstance(){
        return new DogNameFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dog_name_fragment,null);
        EditText dog_name = view.findViewById(R.id.what_dogName);
        Button next_button = (Button)view.findViewById(R.id.next_button);
        next_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(dog_name.getText().toString().equals("")){ }
                else{
                    ((DogRegister)getActivity()).replaceFragment(DogCharacterFragment.newInstance());
                }

            }
        });
        return view;
    }



}
