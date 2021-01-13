package com.petmeeting.zoocheck;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DogPhotoFragment extends Fragment {



    final private static String TAG = "photo";
    private static final int MY_PERMISSION_CAMERA=1111;
    private static final int REQUEST_TAKE_PHOTO=2222;
    private static final int REQUEST_TAKE_ALBUM=3333;
    private static final int REQUEST_IMAGE_CROP=4444;
    String mCurrentPhotoPath;
    Uri imageURI, photoURI, albumURI;
    private Context context;
    //카메라 접근 불가능, 갤러리에서 사진 선택 후 이미지뷰에 안뜸. 수정해야함!!!!!!!
    public static DogPhotoFragment newInstance(){
        return new DogPhotoFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dog_photo_gender_age_fragment,null);
        context = container.getContext();

        ImageButton btn_photo=(ImageButton)view.findViewById(R.id.btn_photo);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED&& ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "권한 설정 완료");
            }else{
                Log.d(TAG, "권한 설정 요청");
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

            }
        }
        btn_photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PopupMenu pop = new PopupMenu(getActivity().getApplicationContext(), view);
                getActivity().getMenuInflater().inflate(R.menu.photo_menu, pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.camera:
                                captureCamera();
                                break;
                            case R.id.gallery:
                                getAlbum();
                                break;

                            case R.id.basic:
                                btn_photo.setImageResource(R.drawable.camera_button);
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

            }
        });
        return view;
    }


    private void captureCamera(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!=null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch(IOException e){
                    e.printStackTrace();
                }if(photoFile != null){
                    Uri providerUri = FileProvider.getUriForFile(context, getActivity().getPackageName(), photoFile);
                    imageURI = providerUri;

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,providerUri);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }else{
                Toast.makeText(context, "접근 불가능 합니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp+".jpg";
        File imageFile = null;
        File storageDir =new File(Environment.getExternalStorageDirectory()+"/Pictures");

        if(!storageDir.exists()){
            storageDir.mkdir();
        }
        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;

    }

    private void getAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    public void cropImage(){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        cropIntent.putExtra("aspectX",1);
        cropIntent.putExtra("aspectY",1);
        cropIntent.putExtra("scale",true);
        cropIntent.putExtra("output",albumURI);
        startActivityForResult(cropIntent,REQUEST_IMAGE_CROP);
    }

    private void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(mCurrentPhotoPath);
        Uri contentURI = Uri.fromFile(file);
        mediaScanIntent.setData(contentURI);
        getActivity().sendBroadcast(mediaScanIntent);
        Toast.makeText(context, "앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }



}
