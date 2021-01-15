package com.petmeeting.zoocheck;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class DogPhotoFragment extends Fragment {



    final private static String TAG = "photo";
    private static final int REQUEST_TAKE_PHOTO=1111;
    private static final int REQUEST_TAKE_ALBUM=2222;
    String mCurrentPhotoPath;
    private Context context;
    Button btn_photo;
    ImageView iv_photo;
    private Uri filePath;

    public static boolean isDogMale = false;
    public static boolean isDogFemale = false;
    public static boolean isTherePhoto = false;

    public static DogPhotoFragment newInstance(){
        return new DogPhotoFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dog_photo_gender_age_fragment,null);
        context = container.getContext();
        EditText dog_age = view.findViewById(R.id.what_dogAge);
        RadioGroup gender_radioGroup = (RadioGroup)view.findViewById(R.id.gender_radioGroup);
        gender_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton)view.findViewById(checkedId);
                if(checkedId == R.id.dog_Male){ isDogMale=true; isDogFemale = false;Log.i("dogGender", String.valueOf(isDogMale));}
                else if(checkedId == R.id.dog_Female){isDogFemale = true; isDogMale=false;Log.i("dogGender", String.valueOf(isDogFemale));}
            }
        });

        iv_photo = view.findViewById(R.id.iv_photo);
        btn_photo = view.findViewById(R.id.btn_photo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu pop = new PopupMenu(getActivity().getApplicationContext(), view);
                getActivity().getMenuInflater().inflate(R.menu.photo_menu, pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.camera:
                                dispatchTakePictureIntent();
                                break;
                            case R.id.gallery:
                                getAlbum();
                                break;
                        }
                        return true;
                    }
                });
                pop.show();
            }

        });


        Button button = (Button)view.findViewById(R.id.next_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(dog_age.getText().toString().equals("")||(isDogFemale == false && isDogMale == false)||isTherePhoto==false){ }
                else{
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }

            }
        });
        return view;
    }
    //촬영한 사진을 이미지 파일로 저장하는 함수
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile( imageFileName,".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    // 카메라 인텐트 실행하는 부분
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;

            try { photoFile = createImageFile(); }
            catch (IOException ex) { }
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context, "com.petmeeting.zoocheck.FileProvider", photoFile);
                filePath = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        //썸네일을 저장후 화면에 사진 띄우기
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap;
                        if (Build.VERSION.SDK_INT >= 29) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                if (bitmap != null) { iv_photo.setImageBitmap(bitmap); }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) { iv_photo.setImageBitmap(bitmap); }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        isTherePhoto=true;
                    }
                    break;
                }

                case REQUEST_TAKE_ALBUM:{
                    if (resultCode == RESULT_OK) {
                        try{
                            InputStream in = getActivity().getContentResolver().openInputStream(intent.getData());
                            Bitmap img = BitmapFactory.decodeStream(in);
                            in.close();
                            //이미지 표시
                            iv_photo.setImageBitmap(img);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        isTherePhoto=true;

                    }
                    break;
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void getAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

}