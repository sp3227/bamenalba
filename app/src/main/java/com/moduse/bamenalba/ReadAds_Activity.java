package com.moduse.bamenalba;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ReadAds_Activity extends AppCompatActivity {

    ImageView main_picture;
    TextView option_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_company);

        // 테스트
        main_picture = (ImageView) findViewById(R.id.write_mainpicture);
        option_1 = (TextView) findViewById(R.id.read_company_option_8);

        Glide.with(this).load(R.drawable.default_companyimg).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(main_picture);

        // 텍스트 가운데 줄긋기
        option_1.setPaintFlags(option_1.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
