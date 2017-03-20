package com.moduse.bamenalba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class WiteAds_Activity extends AppCompatActivity {

    ImageView main_picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_company);

        // 테스트
        main_picture = (ImageView) findViewById(R.id.write_mainpicture);

        Glide.with(this).load(R.drawable.default_picture).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(main_picture);
    }
}
