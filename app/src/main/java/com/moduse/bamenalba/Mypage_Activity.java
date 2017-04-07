package com.moduse.bamenalba;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sejung on 2017-04-05.
 */

public class Mypage_Activity extends Activity
{

    AppInfo appInfo;
    ProgressDialog loading;

    //통신
    phpdown task;

    //통신 타입
    enum phptype {load, Fix, Imgdelete}  // 불러오기 // 사진삭제 // 수정
    phptype phptype_value;


    // 레이아웃
    ImageView mypage_img;
    EditText  edit_nickname;

    LinearLayout spenner_age;
    TextView  text_age;

    LinearLayout chack_man;
    LinearLayout chack_girl;
    CheckBox  chackbox_man;
    CheckBox  chackbox_girl;

    LinearLayout spenner_address_1;
    LinearLayout spenner_address_2;
    TextView  text_address_1;
    TextView  text_address_2;

    TextView sector_title;
    LinearLayout spenner_sector;
    TextView  text_sector;

    LinearLayout spenner_ment;
    TextView  text_ment;

    // 다이얼로 그 상수
    final int Dialog_fix_address1            = 0;  // 시도 선택
    final int Dialog_fix_address2            = 1;  // 군구 선택
    final int Dialog_fix_age                  = 2;  // 나이 선택
    final int Dialog_fix_sector              = 3;  //  업종 선택
    final int Dialog_fix_ment                = 4;  // 한마디 선택
    final int Dialog_fix_img                 = 5;  // 이미지 선택

    // 선택되는 군구 (변화)
    String[] select_address2 = null;

    // 데이터를 담을 해시 맵
    HashMap<String,Object> userdata;

    // 이미지 부분
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;


    Bitmap photo = null;
    String photo_path;
    Uri mImageCaptureUri;
    private int id_view;
    private String absoultePath;
    private byte[] imgbyte = null;


    //이미지 업로드 부분
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    URL connectUrl = null;

    // 수정 데이터
    String fix_nickname = null;
    String fix_sex = null;
    String fix_age = null;
    String fix_adress1 = null;
    String fix_adress2 = null;
    String fix_sector = null;
    String fix_ment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_activity);

        loading = new ProgressDialog(this);

        appInfo = new AppInfo();
        InitShow();

        userdata = new HashMap<String, Object>();  // 기존 프로필 데이터를 넣을 리스트 선언

        initlayout();
        clicklistener();

        //통신에 넣어야함 데이터 적용
        dataupdate();
    }


    //------------------------------------ 레이아웃 초기화 --------------------------//
    public void initlayout()
    {
        mypage_img = (ImageView) findViewById(R.id.tab5_sub_mypage_img);
        edit_nickname = (EditText) findViewById(R.id.tab5_sub_mypage_edit_nickname);

        spenner_age = (LinearLayout) findViewById(R.id.tab5_sub_mypage_spenner_age);
        text_age = (TextView) findViewById(R.id.tab5_sub_mypage_text_age);

        chack_man = (LinearLayout) findViewById(R.id.tab5_sub_mypage_chack_man);
        chack_girl = (LinearLayout) findViewById(R.id.tab5_sub_mypage_chack_girl);
        chackbox_man = (CheckBox) findViewById(R.id.tab5_sub_mypage_chackbox_man);
        chackbox_girl = (CheckBox) findViewById(R.id.tab5_sub_mypage_chackbox_girl);

        spenner_address_1 = (LinearLayout) findViewById(R.id.tab5_sub_mypage_spenner_address_1);
        spenner_address_2 = (LinearLayout) findViewById(R.id.tab5_sub_mypage_spenner_address_2);
        text_address_1 = (TextView) findViewById(R.id.tab5_sub_mypage_text_address_1);
        text_address_2 = (TextView) findViewById(R.id.tab5_sub_mypage_text_address_2);

        sector_title = (TextView) findViewById(R.id.tab5_sub_mypage_title_sector);
        spenner_sector = (LinearLayout) findViewById(R.id.tab5_sub_mypage_spenner_sector);
        text_sector = (TextView) findViewById(R.id.tab5_sub_mypage_text_sector);

        spenner_ment = (LinearLayout) findViewById(R.id.tab5_sub_mypage_spenner_ment);
        text_ment = (TextView) findViewById(R.id.tab5_sub_mypage_text_ment);

    }

    // 통신 이후 나의 프로필 데이터 적용
    public void dataupdate()
    {

        //임시
        userdata.put("index","1");
        userdata.put("user_img","none");
        userdata.put("user_nickname","손파리");
        userdata.put("user_sex","여자");
        userdata.put("user_age","22");
        userdata.put("user_adress1","광주");
        userdata.put("user_adress2","서구");
        userdata.put("user_sector","노래방");
        userdata.put("user_ment","가족을찾아요~");

        //임시


        /*  프로필 이미지 적용  */
        if(userdata.get("user_img").toString().equals("none") || userdata.get("user_img").toString().equals(null))
        {
            if(userdata.get("user_sex").toString().equals("여자"))
            {
                Glide.with(this).load(R.drawable.default_icon_girl).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(mypage_img);
            }
            else
            {
                Glide.with(this).load(R.drawable.default_icon_men).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(mypage_img);
            }
        }
        else
        {
            Glide.with(this).load(userdata.get("user_img").toString()).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(mypage_img);
        }

        /*  닉네임 적용  */
        edit_nickname.setText(userdata.get("user_nickname").toString());

        /*  나이 적용  */
        text_age.setText(userdata.get("user_age").toString()+"세");

        /*  성별 적용  */
        if(userdata.get("user_sex").toString().equals("여자"))
        {
            chackbox_man.setChecked(false);
            chackbox_girl.setChecked(true);
        }
        else
        {
            chackbox_man.setChecked(true);
            chackbox_girl.setChecked(false);
        }

        /*  시도 적용  */
        text_address_1.setText(userdata.get("user_adress1").toString());

        /*  군구 적용  */
        text_address_2.setText(userdata.get("user_adress2").toString());

        /*  한마디 적용  */
        text_sector.setText(userdata.get("user_sector").toString());
        if(AppInfo.MY_TYPE.toString().equals("company"))
        {
            sector_title.setText("업종");
        }
        else
        {
            sector_title.setText("희망업종");
        }

        /*  한마디 적용  */
        text_ment.setText(userdata.get("user_ment").toString());

    }

    //------------------------------------ 클릭 리스너 --------------------------//

    public void clicklistener()
    {

        // 프로필 이미지
        mypage_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_fix_img);
            }
        });

        // 나이
        spenner_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDialog(Dialog_fix_age);
            }
        });

        //성별 여
        chack_girl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chackbox_man.setChecked(false);
                chackbox_girl.setChecked(true);
            }
        });

        //성별 남
        chack_man.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chackbox_man.setChecked(true);
                chackbox_girl.setChecked(false);
            }
        });

        // 시도
        spenner_address_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDialog(Dialog_fix_address1);
            }
        });

        // 군구
        spenner_address_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDialog(Dialog_fix_address2);
            }
        });

        // 업종
        spenner_sector.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDialog(Dialog_fix_sector);
            }
        });

        // 한마디
        spenner_ment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDialog(Dialog_fix_ment);
            }
        });
    }


    // 변경 완료
    public void tab5_sub_mypage_click_submit(View v)
    {
        //닉네임
        fix_nickname = edit_nickname.getText().toString();

        //나이
        fix_age = text_age.getText().toString();

        //성별
        if(chackbox_man.isChecked())
        {
            fix_sex = "남자";
        }
        else if(chackbox_girl.isChecked())
        {
            fix_sex = "여자";
        }

        //시도
        fix_adress1 = text_address_1.getText().toString();

        //군구
        fix_adress2 = text_address_2.getText().toString();

        //업종
        fix_sector = text_sector.getText().toString();

        //업종
        fix_ment = text_ment.getText().toString();



          // 임시 다이얼로그
          Toast.makeText(this, "닉네임 : " + fix_nickname
                  + "\n나이 : " + fix_age
                  + "\n성별 : " + fix_sex
                  + "\n시도 : " + fix_adress1
                  + "\n군구 : " + fix_adress2
                  + "\n업종 : " + fix_sector
                  + "\n한마디 : " + fix_ment, Toast.LENGTH_LONG).show();

    }


    // 닫기
    public void mypage_close(View v)
    {
        Tampimgdelete(); // 임시 이미지 삭제
        finish();
    }

    // 뒤로가기 (댓글창 닫기)
    @Override
    public void onBackPressed()
    {
        Tampimgdelete();
        finish();
    }

    //------------------------------------------------------------------다이얼로그 ----------------------------------------------------------//
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case Dialog_fix_address1: // 시도
            {
                final CharSequence[] item = getResources().getStringArray(R.array.area);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("변경 하려는 지역을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                text_address_1.setText(item[i].toString());
                                text_address_2.setText("전체");
                                fix_adress1 = item[i].toString();

                                select_address(item[i].toString());
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_fix_address2: // 군구
            {
                final CharSequence[] item = select_address2;
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("변경 세부 지역을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {

                                text_address_2.setText(item[i].toString());
                                fix_adress2 = item[i].toString();
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_fix_age: // 나이 선택
            {
                final CharSequence[] item = getResources().getStringArray(R.array.age);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("변경 나이를 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                text_age.setText(item[i].toString()+"세");
                                fix_age = item[i].toString();
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_fix_sector: // 업종 선택
            {
                String title_value = null;

                if(AppInfo.MY_TYPE.toString().equals("company"))
                {
                    title_value = "업종을 선택하세요.";
                }
                else
                {
                    title_value = "희망업종을 선택하세요.";
                }

                final CharSequence[] item = getResources().getStringArray(R.array.sector);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle(title_value) // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                text_sector.setText(item[i].toString());
                                fix_sector = item[i].toString();
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_fix_ment: // 한마디 선택
            {
                final CharSequence[] item;
                if(AppInfo.MY_TYPE.toString().equals("company"))
                {
                    item = getResources().getStringArray(R.array.company_ment);
                }
                else
                {
                    item = getResources().getStringArray(R.array.ment);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("한마디를 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                text_ment.setText(item[i].toString());
                                fix_ment = item[i].toString();
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }
            case Dialog_fix_img: // 이미지 선택
            {
                final CharSequence[] item = {"카메라", "갤러리", "삭제", "취소"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("사진 불러오기") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if (item[i].toString().equals(item[0]))
                                {
                                    // 카메라
                                    doTakePhotoAction();
                                }
                                else if (item[i].toString().equals(item[1]))
                                {
                                    // 갤러리
                                    doTakeAlbumAction();
                                }
                                else if (item[i].toString().equals(item[2]))
                                {
                                    // 이미지 삭제
                                }
                                else
                                {
                                    // 취소
                                    Tampimgdelete(); // 임시 이미지 삭제
                                }
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;
            }

        }

        return null;
    }

    @Deprecated
    protected void onPrepareDialog(int id, Dialog dialog)
    {
        super.onPrepareDialog(id, dialog);
        //해당 다이얼로그는 매번 다시 부름.
        switch(id)
        {
            case Dialog_fix_address1:
            {
                removeDialog(Dialog_fix_address2);

                //없에면 다시 새로 그림
            }

        }
    }

    //--------------------------------------------------------------- 군구 선택 함수----------------------//
    public void select_address(String value)
    {
        select_address2 = null;

        if (value.toString().equals("전체"))
        {
            select_address2 = null;
        }
        else if(value.toString().equals("서울"))
        {
            select_address2 = getResources().getStringArray(R.array.seoul);
        }
        else if(value.toString().equals("경기"))
        {
            select_address2 = getResources().getStringArray(R.array.gyeonggi);
        }
        else if(value.toString().equals("부산"))
        {
            select_address2 = getResources().getStringArray(R.array.busan);
        }
        else if(value.toString().equals("인천"))
        {
            select_address2 = getResources().getStringArray(R.array.incheon);
        }
        else if(value.toString().equals("대구"))
        {
            select_address2 = getResources().getStringArray(R.array.daegu);
        }
        else if(value.toString().equals("광주"))
        {
            select_address2 = getResources().getStringArray(R.array.gwangju);
        }
        else if(value.toString().equals("경남"))
        {
            select_address2 = getResources().getStringArray(R.array.gyeongnam);
        }
        else if(value.toString().equals("충남"))
        {
            select_address2 = getResources().getStringArray(R.array.chungnam);
        }
        else if(value.toString().equals("대전"))
        {
            select_address2 = getResources().getStringArray(R.array.daejeon);
        }
        else if(value.toString().equals("충북"))
        {
            select_address2 = getResources().getStringArray(R.array.chungbuk);
        }
        else if(value.toString().equals("경북"))
        {
            select_address2 = getResources().getStringArray(R.array.gyeongsangbuk);
        }
        else if(value.toString().equals("울산"))
        {
            select_address2 = getResources().getStringArray(R.array.ulsan);
        }
        else if(value.toString().equals("세종"))
        {
            select_address2 = getResources().getStringArray(R.array.sejong);
        }
        else if(value.toString().equals("전북"))
        {
            select_address2 = getResources().getStringArray(R.array.jeonbuk);
        }
        else if(value.toString().equals("강원"))
        {
            select_address2 = getResources().getStringArray(R.array.gangwon);
        }
        else if(value.toString().equals("전남"))
        {
            select_address2 = getResources().getStringArray(R.array.jeonnam);
        }
        else if(value.toString().equals("제주"))
        {
            select_address2 = getResources().getStringArray(R.array.jeju);
        }
    }


    //------------------------------------------------ 사진, 갤러리 부분-------------------------------------------------//

    //////////////////////////////////// 사진 카메라, 앨범 //////////////////////////////////////////////

    public void doTakePhotoAction() // 카메라 촬영 후 이미지 가져오기
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".png";
        //mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        // Crop된 이미지를 저장할 파일의 경로를 생성
        mImageCaptureUri = createSaveCropFile();

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }


    // 앨범에서 사진 가져오기
    public void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Toast.makeText(getBaseContext(), "resultCode : " + resultCode, Toast.LENGTH_SHORT).show();

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM: {   //앨범 이미지
                mImageCaptureUri = data.getData();
                File original_file = getImageFile(mImageCaptureUri);

                mImageCaptureUri = createSaveCropFile();
                File cpoy_file = new File(mImageCaptureUri.getPath());

                // SD카드에 저장된 파일을 이미지 Crop을 위해 복사한다.
                copyFile(original_file, cpoy_file);

            }

            case PICK_FROM_CAMERA: {   //촬영 이미지
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("output", mImageCaptureUri);

                /*
                intent.putExtra("outputX", 1000);
                intent.putExtra("outputY", 1000);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                */
                startActivityForResult(intent, CROP_FROM_iMAGE);

                break;
            }
            case CROP_FROM_iMAGE: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.

                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에

                // 임시 파일을 삭제합니다.

                if (resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();
                // CROP된 이미지를 저장하기 위한 FILE 경로

                // String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SmartWheel/"+System.currentTimeMillis()+".jpg";

                if (extras != null) {
                    /*
                    Bitmap photo = (Bitmap) data.getExtras().get("data"); // CROP된 BITMAP
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG,100,stream);
                    */
                    String full_path = mImageCaptureUri.getPath();
                    photo_path = full_path.substring(0, full_path.length());
                    photo = BitmapFactory.decodeFile(photo_path);

                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 50, byteArray);
                    imgbyte = byteArray.toByteArray();


                    //write_img_select.setImageBitmap(photo);
                    Glide.with(this.getApplicationContext()).load(photo_path).centerCrop().bitmapTransform(new CropCircleTransformation(this.getApplicationContext()))
                            .error(R.drawable.default_companyimg).into(mypage_img);
                    //                Log.i("TAG1,", "포토 :" + photo + "   갯 :"+ photo.getGenerationId());
                    // Toast.makeText(getBaseContext(), "잘생겼는지 체크중..", Toast.LENGTH_SHORT).show();


                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
/*
                if (f.exists()) {
                    f.delete();
                }
*/
                break;
            }
        }
    }

    /**
     * Crop된 이미지가 저장될 파일을 만든다.
     *
     * @return Uri
     */

    private Uri createSaveCropFile() {
        Uri uri;
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        return uri;
    }

    /**
     * 선택된 uri의 사진 Path를 가져온다.
     * uri 가 null 경우 마지막에 저장된 사진을 가져온다.
     *
     * @param uri
     * @return
     */
    private File getImageFile(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        if (uri == null) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        Cursor mCursor = getContentResolver().query(uri, projection, null, null,
                MediaStore.Images.Media.DATE_MODIFIED + " desc");
        if (mCursor == null || mCursor.getCount() < 1) {
            return null; // no cursor or no record
        }
        int column_index = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        mCursor.moveToFirst();

        String path = mCursor.getString(column_index);

        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }

        return new File(path);
    }

    public static boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        try {
            InputStream in = new FileInputStream(srcFile);
            try {
                result = copyToFile(in, destFile);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    /**
     * Copy data from a source stream to destFile.
     * Return true if succeed, return false if failed.
     */
    private static boolean copyToFile(InputStream inputStream, File destFile) {
        try {
            OutputStream out = new FileOutputStream(destFile);
            try {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) >= 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                out.close();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void Tampimgdelete()
    {
        // 임시 파일 삭제

        if(imgbyte != null)
        {
            File f = new File(mImageCaptureUri.getPath());
            if (f.exists())
            {
                f.delete();
            }
        }
    }


    //------------------------------------------------------------- 통신 부분 --------------------------------------------------------//

    // 기존 프로필 불러오기
    public void php_profile_select()
    {
        StartShow();

        phptype_value = phptype.load;
        ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();

        post.add(new BasicNameValuePair("loginID", AppInfo.MY_LOGINID));

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
            HttpPost httpPost = new HttpPost("");
            httpPost.setEntity(entity);

            task = new phpdown();    // 쓰레드 시작
            task.execute(httpPost);

        } catch (Exception e) {
            Toast.makeText(this, "서버에 연결이 실패 하였습니다. 다시 시도 해주세요.", Toast.LENGTH_SHORT).show();
            Log.e("Exception Error", e.toString());
        }
    }



    private class phpdown extends AsyncTask<HttpPost, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  loading = ProgressDialog.show(Intro_app.this, "버전 체크중입니다.", null, true, true);
        }

        @Override
        protected String doInBackground(HttpPost... urls) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = new HttpResponse() {
                @Override
                public StatusLine getStatusLine() {
                    return null;
                }
                @Override
                public void setStatusLine(StatusLine statusLine) {}
                @Override
                public void setStatusLine(ProtocolVersion protocolVersion, int i) {}
                @Override
                public void setStatusLine(ProtocolVersion protocolVersion, int i, String s) {}
                @Override
                public void setStatusCode(int i) throws IllegalStateException {}
                @Override
                public void setReasonPhrase(String s) throws IllegalStateException {}
                @Override
                public HttpEntity getEntity() {
                    return null;
                }
                @Override
                public void setEntity(HttpEntity httpEntity) {}
                @Override
                public Locale getLocale() {
                    return null;
                }
                @Override
                public void setLocale(Locale locale) {}
                @Override
                public ProtocolVersion getProtocolVersion() {
                    return null;
                }
                @Override
                public boolean containsHeader(String s) {
                    return false;
                }
                @Override
                public Header[] getHeaders(String s) {
                    return new Header[0];
                }
                @Override
                public Header getFirstHeader(String s) {
                    return null;
                }
                @Override
                public Header getLastHeader(String s) {
                    return null;
                }
                @Override
                public Header[] getAllHeaders() {
                    return new Header[0];
                }
                @Override
                public void addHeader(Header header) {}
                @Override
                public void addHeader(String s, String s1) {}
                @Override
                public void setHeader(Header header) {}
                @Override
                public void setHeader(String s, String s1) {}
                @Override
                public void setHeaders(Header[] headers) {}
                @Override
                public void removeHeader(Header header) {}
                @Override
                public void removeHeaders(String s) {}
                @Override
                public HeaderIterator headerIterator() {
                    return null;
                }
                @Override
                public HeaderIterator headerIterator(String s) {
                    return null;
                }
                @Override
                public HttpParams getParams() {
                    return null;
                }
                @Override
                public void setParams(HttpParams httpParams) {}
            };

            String returnData = "";

            try {
                response = httpclient.execute(urls[0]);
            } catch (Exception e) {
                Log.e("Exception talk", e.toString());
            }


            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder builder = new StringBuilder();
                String str = "";

                while ((str = rd.readLine()) != null) {
                    builder.append(str);
                }

                returnData = builder.toString();
            } catch (Exception e) {
                Log.e("Exception talk", e.toString());
            }

            return returnData;
        }

        protected void onPostExecute(String result)
        {
            //loading.dismiss();
            Log.i("result",result);

            if(phptype_value.equals(phptype.load))
            {
                String index_;
                String user_img_;
                String user_nickname_;
                String user_age_;
                String user_sex_;
                String user_adress1_;
                String user_adress2_;
                String user_sector_;
                String user_ment_;


                try {
                    JSONObject root = new JSONObject(result);

                    JSONArray ja = root.getJSONArray("result");

                    for (int i = 0; i < ja.length(); i++)
                    {

                        JSONObject jo = ja.getJSONObject(i);

                        index_ = jo.getString("index");
                        user_img_ = jo.getString("user_img");
                        user_nickname_ = jo.getString("user_nickname");
                        user_age_ = jo.getString("user_age");
                        user_sex_ = jo.getString("user_sex");
                        user_adress1_ = jo.getString("user_adress1");
                        user_adress2_ = jo.getString("user_adress2");
                        user_sector_ = jo.getString("user_sector");
                        user_ment_ = jo.getString("user_ment");

                        userdata.put("index",index_);
                        userdata.put("user_img",user_img_);
                        userdata.put("user_nickname",user_nickname_);
                        userdata.put("user_age",user_age_);
                        userdata.put("user_sex",user_sex_);
                        userdata.put("user_adress1",user_adress1_);
                        userdata.put("user_adress2",user_adress2_);
                        userdata.put("user_sector",user_sector_);
                        userdata.put("user_ment",user_ment_);

                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                // UI 적용 함수 시작
                dataupdate();
            }

            StopShow();
        }
    }
    //------------------------------------------------------------- 통신 부분 END --------------------------------------------------------//






    //----------------------------------------------------- 프로그레스 설정------------------------//
    public void InitShow()
    {
        loading.setProgress(ProgressDialog.STYLE_SPINNER);
        loading.setCanceledOnTouchOutside(false);
        loading.setMessage("잠시만 기다려주세요.");
    }
    public void SetmsgShow(String value)
    {
        loading.setMessage(value);
    }
    public void StartShow() {loading.show();}
    public void StopShow() {loading.dismiss();}
}
