package com.moduse.bamenalba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ReadAds_Activity extends AppCompatActivity {

    ProgressDialog loading;

    ImageView main_picture;
    TextView option_1;

    String loadidx = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_company);

        loading = new ProgressDialog(this);
        InitShow();
        StartShow();

        Intent intent = getIntent();
        loadidx = intent.getStringExtra("taget_idx");


        // 테스트
        main_picture = (ImageView) findViewById(R.id.write_mainpicture);
        option_1 = (TextView) findViewById(R.id.read_company_option_8);

        Glide.with(this).load(R.drawable.default_companyimg).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(main_picture);

        // 텍스트 가운데 줄긋기
        option_1.setPaintFlags(option_1.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        //임시 확인용
        Toast.makeText(getApplicationContext(),"광고식별코드 : "+loadidx,Toast.LENGTH_SHORT).show();

        StopShow();
    }









    // 프로그레스 설정
    public void InitShow() {
        loading.setProgress(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("업체 정보를 불러오는 중입니다.");
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


    // 닫기
    public void company_detail_close(View v)
    {
        finish();
    }
}
