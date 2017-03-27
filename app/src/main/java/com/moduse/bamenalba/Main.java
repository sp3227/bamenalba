package com.moduse.bamenalba;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    }

    public void Tab2_road()
    {
        tab_2 = new Tab_2();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_2.in_layout,layoutParams);
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

    }

    public void Tab4_road()
    {
        tab_4 = new Tab_4();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_4.in_layout,layoutParams);
    }

    public void Tab5_road()
    {
        tab_5 = new Tab_5();

        //레이아웃 인플레어
        add_Linear.removeAllViews();
        add_Linear.addView(tab_5.in_layout,layoutParams);

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
