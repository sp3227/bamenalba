package com.moduse.bamenalba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class Readjoper_Activity extends AppCompatActivity {

    //DATA
    String joper_user_deviceID;
    String joper_user_sex;
    String joper_user_nickname;
    String joper_user_age;
    String joper_user_loaction;

    String joper_main_picture_value = "https://i.ytimg.com/vi/7ojaz9X-BAg/maxresdefault.jpg";
    String joper_detail_name_value = "손파리";
    String joper_detail_age_value = "33세";
    String joper_detail_sex_value = "남자";
    String joper_detail_adress1_value = "전남";
    String joper_detail_adress2_value = "나주";
    String joper_detail_sector_value = "요정";
    String joper_detail_ment_value = "일자리 아무거나 구하러 왔어요~";

    // UI
    TextView company_ui_user_info;
    ImageView joper_ui_main_picture;
    TextView joper_ui_detail_name;
    TextView joper_ui_detail_age;
    TextView joper_ui_detail_sex;
    TextView joper_ui_detail_adress1;
    TextView joper_ui_detail_adress2;
    TextView joper_ui_detail_sector;
    TextView joper_ui_detail_ment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_jophunter);


        // 인텐트 데이터 받기
        Intent intent = getIntent();
        joper_user_deviceID = intent.getStringExtra("taget_idx");
        joper_user_sex = intent.getStringExtra("taget_sex");
        joper_user_nickname = intent.getStringExtra("taget_nickname");
        joper_user_age = intent.getStringExtra("taget_age");
        joper_user_loaction = intent.getStringExtra("taget_loaction");


        // 레이아웃 초기화
        company_ui_user_info = (TextView) findViewById(R.id.joperlist_detail_info);
        joper_ui_main_picture = (ImageView) findViewById(R.id.joperlist_detail_mainpicture);
        joper_ui_detail_name = (TextView) findViewById(R.id.joperlist_detail_company_name);
        joper_ui_detail_age = (TextView) findViewById(R.id.joperlist_detail_company_age);
        joper_ui_detail_sex = (TextView) findViewById(R.id.joperlist_detail_company_sex);
        joper_ui_detail_adress1 = (TextView) findViewById(R.id.joperlist_detail_company_adress1);
        joper_ui_detail_adress2 = (TextView) findViewById(R.id.joperlist_detail_company_adress2);
        joper_ui_detail_sector = (TextView) findViewById(R.id.joperlist_detail_company_sector);
        joper_ui_detail_ment = (TextView) findViewById(R.id.joperlist_detail_company_ment);

        init_layout();


    }

    //----------------------------------------------- 레이아웃 연결 (통신후) -----------------------------//
    public void init_layout()
    {
        //상단 작성자 정보
        company_ui_user_info.setText(joper_user_nickname+"("+joper_user_age+"세) "+joper_user_loaction+"km");

        //프로필 이미지
        if(joper_main_picture_value.toString().equals("none"))
        {
            if(joper_detail_sex_value.toString().equals("여자"))
            {
                Glide.with(this).load(R.drawable.default_icon_girl).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).bitmapTransform(new CropCircleTransformation(this)).thumbnail(0.1f).into(joper_ui_main_picture);
            }
            else
            {
                Glide.with(this).load(R.drawable.default_icon_men).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).bitmapTransform(new CropCircleTransformation(this)).thumbnail(0.1f).into(joper_ui_main_picture);
            }
        }
        else
        {
            Glide.with(this).load(joper_main_picture_value).dontAnimate().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).bitmapTransform(new CropCircleTransformation(this)).thumbnail(0.1f).into(joper_ui_main_picture);
        }

                /*---유저 이미지 클릭 리스너 ---*/
        joper_ui_main_picture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(joper_main_picture_value.toString().equals("none"))
                {
                    Toast.makeText(Main.MainContext,"구직자 이미지가 없습니다.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ((Main) Main.MainContext).ImageViewer(joper_main_picture_value, joper_user_nickname, joper_user_age);
                }
            }
        });

        // 구직자 이름
        joper_ui_detail_name.setText(joper_detail_name_value);

        //구직자 나이
        joper_ui_detail_age.setText(joper_detail_age_value+"세");

        //구직자 성별
        joper_ui_detail_sex.setText(joper_detail_sex_value);

        if(joper_detail_sex_value.toString().equals("여자"))
        {
            joper_ui_detail_sex.setTextColor(getResources().getColor(R.color.girl));
        }
        else
        {
            joper_ui_detail_sex.setTextColor(getResources().getColor(R.color.man));
        }

        //구직자 시도
        joper_ui_detail_adress1.setText(joper_detail_adress1_value);

        //구직자 군구
        joper_ui_detail_adress2.setText(joper_detail_adress2_value);

        //구직자 희망 업종
        joper_ui_detail_sector.setText(joper_detail_sector_value);

        //구직자 한마디
        joper_ui_detail_ment.setText(joper_detail_ment_value);
    }


    //----------------------------------------버튼 리스너 ------------------------------//

    // 구직자에게 쪽지 보내기  업체 -> 구직자
    public void joperlist_detail_btn_send_massage(View v)
    {
        if(AppInfo.MY_TYPE.toString().equals("company"))
        {
            Intent intent = new Intent(this, Popup_letter.class);
            intent.putExtra("ad_type", "company");
            intent.putExtra("taget_idx", joper_user_deviceID);
            intent.putExtra("taget_sex", joper_user_sex);
            intent.putExtra("taget_nickname", joper_user_nickname);
            intent.putExtra("taget_age", joper_user_age);
            intent.putExtra("taget_loaction", joper_user_loaction);

            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"업체만 구직자에게 쪽지를 보낼수 있습니다.",Toast.LENGTH_SHORT).show();
        }
    }

    //닫기
    public void joperlist_detail_close(View v)
    {
        finish();
    }
}
