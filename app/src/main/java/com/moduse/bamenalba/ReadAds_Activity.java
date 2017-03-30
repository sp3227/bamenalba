package com.moduse.bamenalba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
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


    //--------------------------레이아웃 초기화 ----------------//
    ImageView main_picture;           // 가게 로고
    TextView company_name;            // 가게 이름
    TextView company_sector;          // 구하는 업종
    TextView company_adress;          // 형식 : 시도 / 군구
    TextView company_payvalue;        // 급액
    TextView company_pay;              // 지급 방식
    TextView company_ageminmax;       // 최소최대나이
    TextView company_sex;              // 성별

    // 가게 옵션들
    TextView option_1;TextView option_2;TextView option_3;
    TextView option_4;TextView option_5;TextView option_6;
    TextView option_7;TextView option_8;TextView option_9;

    TextView company_content;   // 소개 내용



    //--------------------------데이터 초기화 ----------------//
    //작성자 정보
    String tagetidx;  // 받는 사람 DEVICEID
    String adtype;    // 광고 유형 (프리미엄, 일반) 거리때문
    String sex;        // 성별
    String nickname;  // 닉네임
    String age;        // 나이
    String loaction;  // 위치


    //작성글 정보
    String adidx = null;

    String main_picture_url = null;
    String company_name_value = null;
    String company_sector_value = null;
    String company_adress_value = null;
    String company_payvalue_value = null;
    String company_pay_value = null;
    String company_ageminmax_value = null;
    String company_sex_value = null;

    String company_call_value = "01023232323";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_company);

        loading = new ProgressDialog(this);
        InitShow();
        StartShow();

        // 인텐트 넘겨받기
        Intent intent = getIntent();
        adidx = intent.getStringExtra("ad_idx");
        tagetidx = intent.getStringExtra("ad_type");
        adtype = intent.getStringExtra("taget_idx");
        sex = intent.getStringExtra("taget_sex");
        nickname = intent.getStringExtra("taget_nickname");
        age = intent.getStringExtra("taget_age");
        loaction = intent.getStringExtra("taget_loaction");


        // 레이아웃 설정
        main_picture = (ImageView) findViewById(R.id.company_detail_mainpicture);
        company_name = (TextView) findViewById(R.id.company_detail_companyname);
        company_sector = (TextView) findViewById(R.id.company_detail_sector);
        company_adress = (TextView) findViewById(R.id.company_detail_adress);
        company_payvalue = (TextView) findViewById(R.id.company_detail_payvalue);
        company_pay = (TextView) findViewById(R.id.company_detail_pay);
        company_ageminmax = (TextView) findViewById(R.id.company_detail_ageminmax);
        company_sex = (TextView) findViewById(R.id.company_detail_sex);

        option_1 = (TextView) findViewById(R.id.company_detail_option1);
        option_2 = (TextView) findViewById(R.id.company_detail_option2);
        option_3 = (TextView) findViewById(R.id.company_detail_option3);
        option_4 = (TextView) findViewById(R.id.company_detail_option4);
        option_5 = (TextView) findViewById(R.id.company_detail_option5);
        option_6 = (TextView) findViewById(R.id.company_detail_option6);
        option_7 = (TextView) findViewById(R.id.company_detail_option7);
        option_8 = (TextView) findViewById(R.id.company_detail_option8);
        option_9 = (TextView) findViewById(R.id.company_detail_option9);

        company_content = (TextView) findViewById(R.id.company_detail_content);






        Glide.with(this).load(R.drawable.default_companyimg).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(main_picture);

        // 텍스트 가운데 줄긋기
        option_1.setPaintFlags(option_1.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        //임시 확인용
        Toast.makeText(getApplicationContext(),"광고식별코드 : "+adidx,Toast.LENGTH_SHORT).show();

        StopShow();
    }




    //------------------------------------- 버튼 설정 -------------------------------------//

    // 전화 걸기
    public void company_detail_call(View v)
    {
        if(AppInfo.MY_TYPE.toString().equals("user"))
        {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + company_call_value));
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"업체 전화는 구직자만 보낼 수 있습니다.",Toast.LENGTH_SHORT).show();
        }
    }

    //쪽지 보내기
    public void company_detail_sandlatter(View v)
    {
        if(AppInfo.MY_TYPE.toString().equals("user"))
        {
            Intent intent = new Intent(this, Popup_letter.class);
            intent.putExtra("ad_type", adtype);
            intent.putExtra("taget_idx", tagetidx);
            intent.putExtra("taget_sex", sex);
            intent.putExtra("taget_nickname", nickname);
            intent.putExtra("taget_age", age);
            intent.putExtra("taget_loaction", loaction);

            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"업체 쪽지는 구직자만 이용할 수 있습니다.",Toast.LENGTH_SHORT).show();
        }
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
