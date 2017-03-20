package com.moduse.bamenalba;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sejung on 2017-03-15.
 */

public class Main extends AppCompatActivity {


    // 탭관련 부분
    LinearLayout Background_tab1, Background_tab2, Background_tab3, Background_tab4, Background_tab5;
    ImageView Icon_tab1, Icon_tab2, Icon_tab3, Icon_tab4, Icon_tab5;
    TextView Text_tab1, Text_tab2,Text_tab3, Text_tab4, Text_tab5;
    LinearLayout Line_tab1, Line_tab2, Line_tab3, Line_tab4, Line_tab5;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        // 버튼 초기화
        init_tablist();
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
    }

    public void click_main_tab2(View v)
    {
        SET_tab_refash(2);
    }

    public void click_main_tab3(View v)
    {
        SET_tab_refash(3);
    }

    public void click_main_tab4(View v)
    {
        SET_tab_refash(4);
    }

    public void click_main_tab5(View v)
    {
        SET_tab_refash(5);
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
