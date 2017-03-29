package com.moduse.bamenalba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoView;

public class ImageView_Activity extends AppCompatActivity {

    ProgressDialog loading;

    String url;

    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_viewer_activity);

        loading = new ProgressDialog(this);

        Intent intent = getIntent();
        url = intent.getStringExtra("adimg_url");

        InitShow();
        StartShow();

        // 레이아웃 세팅
        photoView = (PhotoView) findViewById(R.id.widget_photoview);

        Glide.with(this.getApplicationContext()).load(url).into(photoView);


        StopShow();
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