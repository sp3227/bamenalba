package com.moduse.bamenalba;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by sejung on 2017-03-15.
 */

public class Main extends AppCompatActivity {


    // 탭관련 부분
    LinearLayout Background_tab1, Background_tab2, Background_tab3, Background_tab4, Background_tab5;
    ImageView Icon_tab1, Icon_tab2, Icon_tab3, Icon_tab4, Icon_tab5;
    TextView Text_tab1, Text_tab2,Text_tab3, Text_tab4, Text_tab5;
    LinearLayout Line_tab1, Line_tab2, Line_tab3, Line_tab4, Line_tab5;

    // 메인 CONTEXT
    public static Context MainContext;

    // 탭 클래스 정의
    public Tab_1 tab_1;
    public Tab_2 tab_2;
    public Tab_3 tab_3;
    public Tab_4 tab_4;
    public Tab_5 tab_5;

    LinearLayout add_Linear;  // 내부 삽입 레이아웃
    LayoutInflater Inflater;
    LinearLayout.LayoutParams layoutParams;

    // 선택된 탭
    int select_tabNum = 1;

    // 선택되는 군구 (변화)
    String[] select_address2 = null;

    // 다이얼로그 상수
    final int Dialog_Search_address1           = 0;  // 시도
    final int Dialog_Search_address2           = 1;  // 군구
    final int Dialog_Search_adtpye             = 2;  // 광고 타입 (프리미엄, 일반)
    final int Dialog_Search_adloadtpye        = 3;  // 정렬 (거리순, 등록순)
    final int Dialog_Search_joperloadtpye     = 4;  // 정렬 (전체, 여자, 남자)

    // 탭1 상단 레이아웃
    TextView tab1_search_adress1;
    TextView tab1_search_adress2;
    TextView tab1_search_adtype;
    TextView tab1_search_adloadtype;

    // 탭2 상단 레이아웃
    TextView tab2_search_adress1;
    TextView tab2_search_adress2;
    TextView tab2_search_joperloadtype;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MainContext = this;

        // 버튼 초기화
        init_tablist();

        // ADD 레이아웃 파라메터 정의
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        add_Linear = (LinearLayout) findViewById(R.id.inLayout);

        Inflater = getLayoutInflater();

        Inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //초기 1번탭 실행
        Tab1_road();
    }

    /*-------------------------------------------------------------탭 ADD 함수 정의--------------------------------------------------------------*/

    public void Tab1_road()
    {
        tab_1 = new Tab_1();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_1.in_layout,layoutParams);

        // 탭1 레이아웃 연결
        tab1_search_adress1 = (TextView) findViewById(R.id.tab1_text_adress1);
        tab1_search_adress2 = (TextView) findViewById(R.id.tab1_text_adress2);
        tab1_search_adtype = (TextView) findViewById(R.id.tab1_text_adtype);
        tab1_search_adloadtype = (TextView) findViewById(R.id.tab1_text_adloadtype);

        // 임시
        tab_1.Tamps();

        select_tabNum = 1;
    }

    public void Tab2_road()
    {
        tab_2 = new Tab_2();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_2.in_layout,layoutParams);

        // 탭2 레이아웃 연결
        tab2_search_adress1 = (TextView) findViewById(R.id.tab2_text_adress1);
        tab2_search_adress2 = (TextView) findViewById(R.id.tab2_text_adress2);
        tab2_search_joperloadtype = (TextView) findViewById(R.id.tab2_text_joperloadtype);

        // 임시
        tab_2.Tamps();

        select_tabNum = 2;
    }

    public void Tab3_road()
    {
        tab_3 = new Tab_3();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_3.in_layout,layoutParams);

        //레이아웃
        ImageView main_picture;

        main_picture = (ImageView) findViewById(R.id.write_mainpicture);

        Glide.with(this).load(R.drawable.default_picture).centerCrop().bitmapTransform(new CropCircleTransformation(this)).into(main_picture);

        select_tabNum = 3;

    }

    public void Tab4_road()
    {
        tab_4 = new Tab_4();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_4.in_layout,layoutParams);

        select_tabNum = 4;
    }

    public void Tab5_road()
    {
        tab_5 = new Tab_5();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_5.in_layout,layoutParams);

        select_tabNum = 5;

    }


    public void init_tablist()
    {
        Background_tab1 = (LinearLayout) findViewById(R.id.main_tab1_background);
        Background_tab2 = (LinearLayout) findViewById(R.id.main_tab2_background);
        Background_tab3 = (LinearLayout) findViewById(R.id.main_tab3_background);
        Background_tab4 = (LinearLayout) findViewById(R.id.main_tab4_background);
        Background_tab5 = (LinearLayout) findViewById(R.id.main_tab5_background);

        Icon_tab1 = (ImageView) findViewById(R.id.main_tab1_icon);
        Icon_tab2 = (ImageView) findViewById(R.id.main_tab2_icon);
        Icon_tab3 = (ImageView) findViewById(R.id.main_tab3_icon);
        Icon_tab4 = (ImageView) findViewById(R.id.main_tab4_icon);
        Icon_tab5 = (ImageView) findViewById(R.id.main_tab5_icon);

        Text_tab1 = (TextView) findViewById(R.id.main_tab1_text);
        Text_tab2 = (TextView) findViewById(R.id.main_tab2_text);
        Text_tab3 = (TextView) findViewById(R.id.main_tab3_text);
        Text_tab4 = (TextView) findViewById(R.id.main_tab4_text);
        Text_tab5 = (TextView) findViewById(R.id.main_tab5_text);

        Line_tab1 = (LinearLayout) findViewById(R.id.main_tab1_line);
        Line_tab2 = (LinearLayout) findViewById(R.id.main_tab2_line);
        Line_tab3 = (LinearLayout) findViewById(R.id.main_tab3_line);
        Line_tab4 = (LinearLayout) findViewById(R.id.main_tab4_line);
        Line_tab5 = (LinearLayout) findViewById(R.id.main_tab5_line);

    }


    /*-------------------------------------------------------------버튼 클릭--------------------------------------------------------------*/

    public void click_main_tab1(View v)
    {
        SET_tab_refash(1);

        Tab1_road();
    }

    public void click_main_tab2(View v)
    {
        SET_tab_refash(2);

        Tab2_road();
    }

    public void click_main_tab3(View v)
    {
        SET_tab_refash(3);

        Tab3_road();
    }

    public void click_main_tab4(View v)
    {
        SET_tab_refash(4);

        Tab4_road();
    }

    public void click_main_tab5(View v)
    {
        SET_tab_refash(5);

        Tab5_road();
    }

/*--------------------------------------------------------------------------공용 함수  ----------------------------------------   */

    //업체로 쪽지 보내기
    public void sand_massage(String tagetidx, String adtype, String sex, String nickname, String age, String loaction)
    {
        Intent intent = new Intent(this, Popup_letter.class);
        intent.putExtra("ad_type",adtype);
        intent.putExtra("taget_idx",tagetidx);
        intent.putExtra("taget_sex",sex);
        intent.putExtra("taget_nickname",nickname);
        intent.putExtra("taget_age",age);
        intent.putExtra("taget_loaction",loaction);

        startActivity(intent);
    }


    // 이미지 뷰어
    public void ImageViewer(String url, String nickname, String age)
    {
        Intent intent = new Intent(Main.MainContext, ImageView_Activity.class);

        intent.putExtra("adimg_url",url);
        intent.putExtra("user_nickname",nickname);
        intent.putExtra("user_age",age);

        startActivity(intent);
    }



/*--------------------------------------------------------------------------탭 1 함수  ----------------------------------------   */

    // 클릭 리스너
    // 검색 시도 클릭
    public void tab1_click_adress1(View v)
    {
        select_address2 = null;
        showDialog(Dialog_Search_address1);
    }

    // 검색 군구 클릭
    public void tab1_click_adress2(View v)
    {
        if(select_address2 == null)
        {
            Toast.makeText(Main.MainContext,"시도를 먼저 선택해주세요.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            showDialog(Dialog_Search_address2);
        }
    }

    // 광고 타입 클릭
    public void tab1_click_adtype(View v)
    {
        showDialog(Dialog_Search_adtpye);
    }

    // 광고 정렬 타입 클릭
    public void tab1_click_adloadtype(View v)
    {
        showDialog(Dialog_Search_adloadtpye);
    }


    // 업체 상세보기
    public void company_detail(String adidx, String tagetidx, String adtype, String sex, String nickname, String age, String loaction)
    {
        Intent intent = new Intent(this, ReadAds_Activity.class);
        intent.putExtra("ad_idx",adidx);
        intent.putExtra("ad_type",adtype);
        intent.putExtra("taget_idx",tagetidx);
        intent.putExtra("taget_sex",sex);
        intent.putExtra("taget_nickname",nickname);
        intent.putExtra("taget_age",age);
        intent.putExtra("taget_loaction",loaction);

        startActivity(intent);

    }



/*--------------------------------------------------------------------------탭 2 함수  ----------------------------------------   */

    //클릭리스너
    // 시도 검색
    public void tab2_click_adress1(View v)
    {
        select_address2 = null;
        showDialog(Dialog_Search_address1);
    }

    // 군구 검색
    public void tab2_click_adress2(View v)
    {
        if(select_address2 == null)
        {
            Toast.makeText(Main.MainContext,"시도를 먼저 선택해주세요.",Toast.LENGTH_SHORT).show();
        }
        else
        {
            showDialog(Dialog_Search_address2);
        }
    }

    // 검색 타입 (전체, 남자, 여자)
    public void tab2_click_joperloadtype(View v)
    {
        showDialog(Dialog_Search_joperloadtpye);
    }

    // 구직자 상세보기
    public void joper_detail(String deviceid, String sex, String nickname, String age, String loaction)
    {

        Intent intent = new Intent(this, Readjoper_Activity.class);
        intent.putExtra("taget_idx",deviceid);
        intent.putExtra("taget_sex",sex);
        intent.putExtra("taget_nickname",nickname);
        intent.putExtra("taget_age",age);
        intent.putExtra("taget_loaction",loaction);

        startActivity(intent);

    }

/*--------------------------------------------------------------------------탭 3 함수  ----------------------------------------   */

    











    public Dialog onCreateDialog(int id)
    {
        switch(id)
        {
            case Dialog_Search_address1: // 시도
            {
                final CharSequence[] item = mergeArrays(getResources().getStringArray(R.array.all), getResources().getStringArray(R.array.area));
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("검색 하려는 지역을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if(select_tabNum == 1)
                                {
                                    tab1_search_adress1.setText(item[i].toString());
                                }
                                else
                                {
                                    tab2_search_adress1.setText(item[i].toString());
                                }

                                    //  text_address_1.setText(item[i].toString());
                                    //  DATA_address_1 = item[i].toString();

                                 select_address(item[i].toString());
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_Search_address2: // 군구
            {
                final CharSequence[] item = mergeArrays(getResources().getStringArray(R.array.all), select_address2);
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("세부 지역을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if(select_tabNum == 1)
                                {
                                    tab1_search_adress2.setText(item[i].toString());
                                }
                                else
                                {
                                    tab2_search_adress2.setText(item[i].toString());
                                }

                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_Search_adtpye: // 애드 타입
            {
                final CharSequence[] item = {"프리미엄","일반"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("광고 유형을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {

                                tab1_search_adtype.setText(item[i].toString());

                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_Search_adloadtpye: // 애드 로드 타입
            {
                final CharSequence[] item = {"거리순", "등록순"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("광고 정렬을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                tab1_search_adloadtype.setText(item[i].toString());
                            }

                        });
                AlertDialog alert = builder.create();  //알림 객체 생성

                return alert;

            }
            case Dialog_Search_joperloadtpye:
            {
                final CharSequence[] item = {"전체", "여자", "남자"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("검색 성별을 선택하세요.") // 제목 설정
                        .setItems(item, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i)
                            {

                                tab2_search_joperloadtype.setText(item[i].toString());

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
            case Dialog_Search_address1:
                removeDialog(Dialog_Search_address2);

                //없에면 다시 새로 그림
        }
    }






    //-------------------------------------------------------- 배열 합치는 메서드-------------------------------//
    public CharSequence[] mergeArrays(CharSequence[] arrA, CharSequence[] arrB)
    {

        int sumLength = arrA.length + arrB.length;
        CharSequence[] arrSum = new CharSequence[sumLength];

        for (int i = 0; i < sumLength; i++)
        {
            if (i < arrA.length)
            {
                arrSum[i] = arrA[i];
            }
            else
            {
                arrSum[i] = arrB[i - arrA.length];
            }
        }
        return arrSum;
    }


    //--------------------------------------------------------------- 군구 선택 함수----------------------//
    public void select_address(String value)
    {
        select_address2 = null;

        if(select_tabNum == 1)
        {
            tab1_search_adress2.setText("전체");
        }
        else
        {
            tab2_search_adress2.setText("전체");
        }

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

/*--------------------------------------------------------------------------탭 체인지 이미지 변경 막코딩 ----------------------------------------   */
    public void SET_tab_refash(int value)
    {
        switch (value)
        {
            case 1:
            {
                Background_tab1.setBackgroundColor(getResources().getColor(R.color.tab_on));
                Background_tab2.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab3.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab4.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab5.setBackgroundColor(getResources().getColor(R.color.tab_off));

                Icon_tab1.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab1_1));
                Icon_tab2.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab2_2));
                Icon_tab3.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab3_2));
                Icon_tab4.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab4_2));
                Icon_tab5.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab5_2));

                Text_tab1.setTextColor(getResources().getColor(R.color.tab_on_text));
                Text_tab2.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab3.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab4.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab5.setTextColor(getResources().getColor(R.color.tab_off_text));

                Line_tab1.setVisibility(View.VISIBLE);
                Line_tab2.setVisibility(View.INVISIBLE);
                Line_tab3.setVisibility(View.INVISIBLE);
                Line_tab4.setVisibility(View.INVISIBLE);
                Line_tab5.setVisibility(View.INVISIBLE);
                break;
            }
            case 2:
            {
                Background_tab1.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab2.setBackgroundColor(getResources().getColor(R.color.tab_on));
                Background_tab3.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab4.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab5.setBackgroundColor(getResources().getColor(R.color.tab_off));

                Icon_tab1.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab1_2));
                Icon_tab2.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab2_1));
                Icon_tab3.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab3_2));
                Icon_tab4.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab4_2));
                Icon_tab5.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab5_2));

                Text_tab1.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab2.setTextColor(getResources().getColor(R.color.tab_on_text));
                Text_tab3.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab4.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab5.setTextColor(getResources().getColor(R.color.tab_off_text));

                Line_tab1.setVisibility(View.INVISIBLE);
                Line_tab2.setVisibility(View.VISIBLE);
                Line_tab3.setVisibility(View.INVISIBLE);
                Line_tab4.setVisibility(View.INVISIBLE);
                Line_tab5.setVisibility(View.INVISIBLE);
                break;
            }
            case 3:
            {
                Background_tab1.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab2.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab3.setBackgroundColor(getResources().getColor(R.color.tab_on));
                Background_tab4.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab5.setBackgroundColor(getResources().getColor(R.color.tab_off));

                Icon_tab1.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab1_2));
                Icon_tab2.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab2_2));
                Icon_tab3.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab3_1));
                Icon_tab4.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab4_2));
                Icon_tab5.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab5_2));

                Text_tab1.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab2.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab3.setTextColor(getResources().getColor(R.color.tab_on_text));
                Text_tab4.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab5.setTextColor(getResources().getColor(R.color.tab_off_text));

                Line_tab1.setVisibility(View.INVISIBLE);
                Line_tab2.setVisibility(View.INVISIBLE);
                Line_tab3.setVisibility(View.VISIBLE);
                Line_tab4.setVisibility(View.INVISIBLE);
                Line_tab5.setVisibility(View.INVISIBLE);
                break;
            }
            case 4:
            {
                Background_tab1.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab2.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab3.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab4.setBackgroundColor(getResources().getColor(R.color.tab_on));
                Background_tab5.setBackgroundColor(getResources().getColor(R.color.tab_off));

                Icon_tab1.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab1_2));
                Icon_tab2.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab2_2));
                Icon_tab3.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab3_2));
                Icon_tab4.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab4_1));
                Icon_tab5.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab5_2));

                Text_tab1.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab2.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab3.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab4.setTextColor(getResources().getColor(R.color.tab_on_text));
                Text_tab5.setTextColor(getResources().getColor(R.color.tab_off_text));

                Line_tab1.setVisibility(View.INVISIBLE);
                Line_tab2.setVisibility(View.INVISIBLE);
                Line_tab3.setVisibility(View.INVISIBLE);
                Line_tab4.setVisibility(View.VISIBLE);
                Line_tab5.setVisibility(View.INVISIBLE);
                break;
            }
            case 5:
            {
                Background_tab1.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab2.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab3.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab4.setBackgroundColor(getResources().getColor(R.color.tab_off));
                Background_tab5.setBackgroundColor(getResources().getColor(R.color.tab_on));

                Icon_tab1.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab1_2));
                Icon_tab2.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab2_2));
                Icon_tab3.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab3_2));
                Icon_tab4.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab4_2));
                Icon_tab5.setImageDrawable(getResources().getDrawable(R.drawable.icon_tab5_1));

                Text_tab1.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab2.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab3.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab4.setTextColor(getResources().getColor(R.color.tab_off_text));
                Text_tab5.setTextColor(getResources().getColor(R.color.tab_on_text));

                Line_tab1.setVisibility(View.INVISIBLE);
                Line_tab2.setVisibility(View.INVISIBLE);
                Line_tab3.setVisibility(View.INVISIBLE);
                Line_tab4.setVisibility(View.INVISIBLE);
                Line_tab5.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

}
