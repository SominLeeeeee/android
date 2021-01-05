package com.petmeeting.zoocheck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DogProfileFrag extends Fragment {

    TextView dog_name, phone_number, address;
    public static Fragment newInstance(int i) {

        DogProfileFrag fragment = new DogProfileFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.dog_profile_fragment, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstancedState){
        super.onViewCreated(view, savedInstancedState);
        dog_name = (TextView)view.findViewById(R.id.dogName);
        phone_number=(TextView)view.findViewById(R.id.phoneNum);
        address=(TextView)view.findViewById(R.id.address);

        dog_name.setText((CharSequence) dog_name);
        phone_number.setText((CharSequence) phone_number);
        address.setText((CharSequence) address);
    }
}
