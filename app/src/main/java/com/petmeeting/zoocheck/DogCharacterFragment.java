package com.petmeeting.zoocheck;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

public class DogCharacterFragment extends Fragment {

    private Context context;
    public static boolean isActivityChecked = false;
    public static boolean isSocialityChecked = false;
    public static boolean isAggressiveChecked = false;
    public static boolean isBarkChecked = false;
    public static int dogActivity = 0;
    public static int dogSociality=0;
    public static int dogAggressive=0;
    public static int dogBark = 0;

    public static DogCharacterFragment newInstance(){
        return new DogCharacterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dog_character_fragment,null);
        RadioGroup activity_radioGroup = (RadioGroup)view.findViewById(R.id.dogActivity_radioGroup);
        activity_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton)view.findViewById(checkedId);
                if(checkedId == R.id.dogActivity_1){dogActivity=1;isActivityChecked=true;Log.i("dogActivity", String.valueOf(isActivityChecked));}
                else if(checkedId == R.id.dogActivity_2){dogActivity=2;isActivityChecked=true;Log.i("dogActivity", String.valueOf(isActivityChecked));}
                else if(checkedId==R.id.dogActivity_3){dogActivity=3;isActivityChecked=true;Log.i("dogActivity", String.valueOf(isActivityChecked));}
                else if(checkedId==R.id.dogActivity_4){dogActivity=4;isActivityChecked=true;Log.i("dogActivity", String.valueOf(isActivityChecked));}
                else if(checkedId==R.id.dogActivity_5){dogActivity=5;isActivityChecked=true;Log.i("dogActivity", String.valueOf(isActivityChecked));}


            }
        });

        RadioGroup sociality_radioGroup = (RadioGroup)view.findViewById(R.id.dogSociality_radioGroup);
        sociality_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton)view.findViewById(checkedId);
                if(checkedId == R.id.dogSociality_1){dogSociality=1;isSocialityChecked=true;Log.i("dogSociality", String.valueOf(isSocialityChecked));}
                else if(checkedId == R.id.dogSociality_2){dogSociality=2;isSocialityChecked=true;Log.i("dogSociality", String.valueOf(isSocialityChecked));}
                else if(checkedId == R.id.dogSociality_3){dogSociality=3;isSocialityChecked=true;Log.i("dogSociality", String.valueOf(isSocialityChecked));}
                else if(checkedId == R.id.dogSociality_4){dogSociality=4;isSocialityChecked=true;Log.i("dogSociality", String.valueOf(isSocialityChecked));}
                else if(checkedId == R.id.dogSociality_5){dogSociality=5;isSocialityChecked=true;Log.i("dogSociality", String.valueOf(isSocialityChecked));}


            }
        });

        RadioGroup aggressive_radioGroup = (RadioGroup)view.findViewById(R.id.dogAggressive_radioGroup);
        aggressive_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton)view.findViewById(checkedId);
                if(checkedId == R.id.dogAggressive_1){dogAggressive=1;isAggressiveChecked=true;Log.i("dogAggressive", String.valueOf(isAggressiveChecked));}
                else if(checkedId == R.id.dogAggressive_2){dogAggressive=2;isAggressiveChecked=true;Log.i("dogAggressive", String.valueOf(isAggressiveChecked));}
                else if(checkedId == R.id.dogAggressive_3){dogAggressive=3;isAggressiveChecked=true;Log.i("dogAggressive", String.valueOf(isAggressiveChecked));}
                else if(checkedId == R.id.dogAggressive_4){dogAggressive=4;isAggressiveChecked=true;Log.i("dogAggressive", String.valueOf(isAggressiveChecked));}
                else if(checkedId == R.id.dogAggressive_5){dogAggressive=5;isAggressiveChecked=true;Log.i("dogAggressive", String.valueOf(isAggressiveChecked));}


            }
        });


        RadioGroup bark_radioGroup = (RadioGroup)view.findViewById(R.id.dogBark_radioGroup);
        bark_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton)view.findViewById(checkedId);

                if(checkedId == R.id.dogBark_1){dogBark=1;isBarkChecked=true;Log.i("dogBark", String.valueOf(isBarkChecked));}
                else if(checkedId == R.id.dogBark_2){dogBark=2;isBarkChecked=true;Log.i("dogBark", String.valueOf(isBarkChecked));}
                else if(checkedId == R.id.dogBark_3){dogBark=3;isBarkChecked=true;Log.i("dogBark", String.valueOf(isBarkChecked));}
                else if(checkedId == R.id.dogBark_4){dogBark=4;isBarkChecked=true;Log.i("dogBark", String.valueOf(isBarkChecked));}
                else if(checkedId == R.id.dogBark_5){dogBark=5;isBarkChecked=true;Log.i("dogBark", String.valueOf(isBarkChecked));}

            }
        });


        Button button = (Button)view.findViewById(R.id.next_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isActivityChecked==true&&isAggressiveChecked==true&&isSocialityChecked==true&&isBarkChecked==true){
                    ((DogRegister)getActivity()).replaceFragment(DogPhotoFragment.newInstance());
                }
            }
        });
        return view;
    }
}
