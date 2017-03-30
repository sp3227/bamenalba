package com.moduse.bamenalba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sejung on 2017-03-23.
 */

public class Popup_letter extends Activity
{
    TextView taget_name;
    TextView taget_age;
    TextView taget_loaction;

    LinearLayout point_panel;
    TextView     my_point;
    TextView     my_send_text;

    EditText latter_edit;

    //------------------------------------------//

    String Ad_Type             = null;

    String Sand_AdIdx          = null;
    String Sand_Sex             = null;
    String Sand_NickName       = null;
    String Sand_Age             = null;
    String Sand_Loaction        = null;

    //임시 거리
    int Tamp_location;

    //-----------보내는 값 -----------//



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //TITLE바 NONONO.

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();

        //팝업 외부 뿌연 효과

        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        //뿌연 효과 정도

        layoutParams.dimAmount= 0.7f;

        //적용

        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.pop_letter);


        // 인텐트
        Intent intent = getIntent();
        Ad_Type = intent.getStringExtra("ad_type");
        Sand_AdIdx = intent.getStringExtra("taget_idx");
        Sand_Sex = intent.getStringExtra("taget_sex");
        Sand_NickName = intent.getStringExtra("taget_nickname");
        Sand_Age = intent.getStringExtra("taget_age");
        Sand_Loaction = intent.getStringExtra("taget_loaction");

        init_poplayout();
    }

    // 레이아웃 업데이트
    public void init_poplayout()
    {
        taget_name = (TextView) findViewById(R.id.pop_latter_nickname);
        taget_age = (TextView) findViewById(R.id.pop_latter_age);
        taget_loaction = (TextView) findViewById(R.id.pop_latter_loaction);

        point_panel = (LinearLayout) findViewById(R.id.pop_latter_point_panel);
        my_point = (TextView) findViewById(R.id.pop_latter_point);
        my_send_text = (TextView) findViewById(R.id.pop_latter_sand_text);

        latter_edit = (EditText) findViewById(R.id.pop_latter_edit);

        // 유저가 쪽지 보낼때는 포인트 창 끄기
        if(AppInfo.MY_TYPE.toString().equals("user"))
        {
            point_panel.setVisibility(View.GONE);
            my_send_text.setText("보내기");
        }
        else
        {
            point_panel.setVisibility(View.VISIBLE);
            my_send_text.setText("보내기(30P)");
        }

        // 받는 사람 타겟 닉네임, 나이 - 성별 구별
        taget_name.setText(Sand_NickName);
        taget_age.setText("("+Sand_Age+"세)");

        if(Sand_Sex.toString().equals("여자"))
        {
            taget_name.setTextColor(getResources().getColor(R.color.girl));
            taget_age.setTextColor(getResources().getColor(R.color.girl));
        }
        else
        {
            taget_name.setTextColor(getResources().getColor(R.color.man));
            taget_age.setTextColor(getResources().getColor(R.color.man));
        }

        // 거리
        if(Ad_Type.toString().equals("premium"))
        {
            Tamp_location = Integer.parseInt(Sand_Loaction);

            if(Tamp_location > 400)
            {
                taget_loaction.setText("???km");
            }
            else
            {
                taget_loaction.setText(Sand_Loaction + "km");
            }

            if (Tamp_location < 30)
            {
                taget_loaction.setTextColor(getResources().getColor(R.color.km10));
            }
            else
            {
                if (Tamp_location < 60)
                {
                    taget_loaction.setTextColor(getResources().getColor(R.color.km50));
                }
                else
                {
                    taget_loaction.setTextColor(getResources().getColor(R.color.km100));
                }
            }
        }
        else
        {
            taget_loaction.setVisibility(View.GONE);
        }

    }


    // 쪽지 보내기
    public void click_letter_send(View v)
    {
        latter_edit.getText();
        if(!latter_edit.getText().toString().equals(null) || !latter_edit.getText().toString().equals(""))
        Toast.makeText(this.getApplicationContext(),"보냈슴~~",Toast.LENGTH_SHORT).show();
        finish();
    }

    // 팝업 닫기
    public void click_letter_close(View v)
    {
        finish();
    }


}
