package com.moduse.bamenalba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoView;

public class ImageView_Activity extends AppCompatActivity {

    ProgressDialog loading;

    String url;
    String nickname;
    String age;

    PhotoView photoView;
    TextView text_nickname;
    TextView text_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_viewer_activity);

        loading = new ProgressDialog(this);

        Intent intent = getIntent();
        url = intent.getStringExtra("adimg_url");
        nickname = intent.getStringExtra("user_nickname");
        age = intent.getStringExtra("user_age");

        InitShow();
        StartShow();

        // 레이아웃 세팅
        photoView = (PhotoView) findViewById(R.id.widget_photoview);
        text_nickname = (TextView) findViewById(R.id.view_nickname);
        text_age = (TextView) findViewById(R.id.view_age);

        text_nickname.setText(nickname);
        text_age.setText("("+age+"세)");


        Glide.with(this.getApplicationContext()).load(url).into(photoView);


        StopShow();
    }

    public void Viewer_close(View v)
    {
        finish();
    }


    // 프로그레스 설정
    public void InitShow() {
        loading.setProgress(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("사진을 불러오는 중입니다..");
    }

    public void SetmsgShow(String value) {
        loading.setMessage(value);
    }

    public void StartShow() {
        loading.show();
    }

    public void StopShow() {
        loading.dismiss();
    }
}