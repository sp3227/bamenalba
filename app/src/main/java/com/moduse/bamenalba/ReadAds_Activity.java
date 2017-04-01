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
    TextView[] options;

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

    String main_picture_url = "http://nakk20.raonnet.com/profileimg/sample_119.png";   //임시로 넣어놈
    String company_name_value = "나나나";
    String company_sector_value = "노래방";
    String company_adress_value = "광주 / 남구";
    String company_payvalue_value = "300,000";
    String company_pay_value = "주급";
    String company_ageminmax_value = "20~30";
    String company_sex_value = "여자";

    Boolean[] option_values;

    String company_content_value = "가게 소개개개개~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

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


        option_values = new Boolean[]{false, false, false, false, false, false, false, true, true};


        options = new TextView[9];

        options[0] = (TextView) findViewById(R.id.company_detail_option1);
        options[1] = (TextView) findViewById(R.id.company_detail_option2);
        options[2] = (TextView) findViewById(R.id.company_detail_option3);
        options[3] = (TextView) findViewById(R.id.company_detail_option4);
        options[4] = (TextView) findViewById(R.id.company_detail_option5);
        options[5] = (TextView) findViewById(R.id.company_detail_option6);
        options[6] = (TextView) findViewById(R.id.company_detail_option7);
        options[7] = (TextView) findViewById(R.id.company_detail_option8);
        options[8] = (TextView) findViewById(R.id.company_detail_option9);


        company_content = (TextView) findViewById(R.id.company_detail_content);


        //임시 확인용

        Toast.makeText(getApplicationContext(),"광고식별코드 : "+adidx,Toast.LENGTH_SHORT).show();

        init_layout();  // 임시

        StopShow();
    }

    //------------------------------------- 레이아웃 연결 통신후 -------------------------------------//
    public void init_layout()
    {
        // 업체 로고 연결

        if(main_picture_url.toString().equals("none") || main_picture_url.toString().equals(null))
        {
            Glide.with(this).load(R.drawable.default_companyimg).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(main_picture);
        }
        else
        {
            Glide.with(this).load(main_picture_url).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(main_picture);

            main_picture.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ((Main) Main.MainContext).ImageViewer(main_picture_url, nickname, age);
                }
            });
        }


        //업체 이름
        company_name.setText(company_name_value);

        //업종
        company_sector.setText(company_sector_value);

        //주소
        company_adress.setText(company_adress_value);

        //페이 금액
        company_payvalue.setText(company_payvalue_value);

        //페이 종류
        company_pay.setText(company_pay_value);

        //나이 최대 최소
        company_ageminmax.setText(company_ageminmax_value+"세");

        //요구 성별
        company_sex.setText(company_sex_value);

        if(company_sex_value.toString().equals("여자"))
        {
            company_sex.setTextColor(getResources().getColor(R.color.girl));
        }
        else
        {
            company_sex.setTextColor(getResources().getColor(R.color.man));
        }

        //옵션
        for(int i=0; i< option_values.length; i++)
        {
            if(option_values[i] == true)
            {
                options[i].setTextColor(getResources().getColor(R.color.option_on));
            }
            else
            {
                options[i].setTextColor(getResources().getColor(R.color.option_off));
                options[i].setPaintFlags(options[i].getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

        //가게 소개
        company_content.setText(company_content_value);


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
