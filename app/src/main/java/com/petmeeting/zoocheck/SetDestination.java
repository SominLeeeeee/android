package com.petmeeting.zoocheck;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.List;

public class SetDestination extends AppCompatActivity implements OnMapReadyCallback {
    //GoogleMap 객체
    GoogleMap googleMap;
    MapFragment mapFragment;
    LocationManager locationManager;
    RelativeLayout boxMap;
    //나의 위도 경도 고도
    double myLatitude;  //위도
    double myLongitude; //경도



    //선택한 위치의 위도 경도
    double lat;
    double lon;


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

        final EditText et3 = findViewById(R.id.editText3);

        final Geocoder geocoder = new Geocoder(this);

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
                        //          list.get(0).getCountryName();  // 국가명
                        //          list.get(0).getLatitude();        // 위도
                        //          list.get(0).getLongitude();    // 경도
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
}