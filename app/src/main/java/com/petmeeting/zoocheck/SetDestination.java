package com.petmeeting.zoocheck;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetDestination extends AppCompatActivity implements OnMapReadyCallback {
    //GoogleMap 객체
    GoogleMap googleMap;
    MapFragment mapFragment;
    LocationManager locationManager;
    RelativeLayout boxMap;
    private TextView textViewResult;

    private RadioGroup radioGroup;
    private Button btn_setOk;
    private EditText edit_expectedElapsedTime, edit_area, edit_minHeadCount, edit_maxHeadCount;
    int expectedElapsedTime, area, minHeadCount, maxHeadCount;
    Boolean isGroup = false;

    //선택한 위치의 위도 경도
    double lat;
    double lon;


    //백엔드에 경도위도 보내기
    String id;
    Retrofit retrofit;
    RetrofitServiceForWalk retrofitServiceForWalk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination);
        // 지오코딩(GeoCoding) : 주소,지명 => 위도,경도 좌표로 변환
        //     위치정보를 얻기위한 권한을 획득, AndroidManifest.xml
        //    ACCESS_FINE_LOCATION : 현재 나의 위치를 얻기 위해서 필요함
        //    INTERNET : 구글서버에 접근하기위해서 필요함

        final TextView tv = (TextView) findViewById(R.id.textView4); // 결과창
        Button b2 = findViewById(R.id.button2);
        Button b4 = findViewById(R.id.button4);
        //Button btn_goto_walk_register = findViewById(R.id.btn_goto_walk_register);
        Button btn_register = findViewById(R.id.btn_register);

        final EditText et3 = findViewById(R.id.edit_address);

        final Geocoder geocoder = new Geocoder(this);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.137.184.184/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitServiceForWalk = retrofit.create(RetrofitServiceForWalk.class);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Address> list = null;
                String str = et3.getText().toString();
                try {
                    list = geocoder.getFromLocationName(
                            str, // 지역 이름
                            10); // 읽을 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list != null) {
                    if (list.size() == 0) {
                        tv.setText("해당되는 주소 정보는 없습니다");
                    } else {
                        tv.setText(list.get(0).toString());
                    }
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 주소입력후 지도2버튼 클릭시 해당 위도경도값의 지도화면으로 이동
                List<Address> list = null;

                String str = et3.getText().toString();
                try {
                    list = geocoder.getFromLocationName
                            (str, // 지역 이름
                                    10); // 읽을 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list != null) {
                    if (list.size() == 0) {
                        tv.setText("해당되는 주소 정보는 없습니다");
                    } else {
                        // 해당되는 주소로 인텐트 날리기
                        Address addr = list.get(0);
                        lat = addr.getLatitude();
                        lon = addr.getLongitude();

                        String sss = String.format("geo:%f,%f", lat, lon);

                        Intent intent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(sss));
                        startActivity(intent);
                    }
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_expectedElapsedTime = findViewById(R.id.edit_expectedElapsedTime);
                edit_area = findViewById(R.id.edit_area);
                edit_minHeadCount = findViewById(R.id.edit_minHeadCount);
                edit_maxHeadCount = findViewById(R.id.edit_maxHeadCount);
                radioGroup = findViewById(R.id.radioGroup_isGroup);
                radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

                expectedElapsedTime = Integer.parseInt(edit_expectedElapsedTime.getText().toString());
                area = Integer.parseInt(edit_area.getText().toString());
                minHeadCount = Integer.parseInt(edit_minHeadCount.getText().toString());
                maxHeadCount = Integer.parseInt(edit_maxHeadCount.getText().toString());
                createPost();
            }
        });

    }

    //구글맵 생성 콜백
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        //지도타입 - 일반
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //나의 위치 설정
        //LatLng position = new LatLng(myLatitude , myLongitude);
        //화면중앙의 위치와 카메라 줌비율
        //this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
        //지도 보여주기
        boxMap.setVisibility(View.VISIBLE);
    }

    //라디오 그룹 클릭 리스너
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.rg_btn1){
                //isGroup = true;
                Toast.makeText(SetDestination.this, "Yes버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
            }
            else if(i == R.id.rg_btn2){
                //isGroup = false;
                Toast.makeText(SetDestination.this, "No버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //서버에 보내기
    private void createPost() {
        //Walk walk = new Walk(id, lat, lon, expectedElapsedTime, area, isGroup, minHeadCount, maxHeadCount);
        //Call<Walk> call = retrofitServiceForWalk.createPost(walk);

        HashMap<String,Object> input = new HashMap<>();
        input.put("id","id");
        input.put("latitude",lat);
        input.put("longitude",lon);
        input.put("expectedElapsedTime",expectedElapsedTime);
        input.put("area",area);
        input.put("isGroup",true);
        input.put("minHeadCount",minHeadCount);
        input.put("maxHeadCount",maxHeadCount);

        retrofitServiceForWalk.createPost(input).enqueue(new Callback<Walk>() {
            @Override
            public void onResponse(Call<Walk> call, Response<Walk> response) {
                if (!response.isSuccessful()) {
                    Log.d("hehe","code:"+response.code());
                    textViewResult.setText("code: " + response.code());
                    return;
                }
                Walk postResponse = response.body();

                String content = "";
                content += "Code : " + response.code() + "\n";
                content += "Id: " + postResponse.getId() + "\n";
                content += "Latitude: " + postResponse.getLatitude() + "\n";
                content += "Longitude: " + postResponse.getLongitude() + "\n";
                content += "ExpectedElapsedTime: " + postResponse.getExpectedElapsedTime() + "\n";
                content += "Area: " + postResponse.getArea() + "\n";
                content += "IsGroup: " + postResponse.getIsGroup() + "\n";
                content += "MinHeadCount: " + postResponse.getMinHeadCount() + "\n";
                content += "MaxHeadCount: " + postResponse.getMaxHeadCount() + "\n";
                textViewResult.setText(content);
            }
            @Override
            public void onFailure(Call<Walk> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


}