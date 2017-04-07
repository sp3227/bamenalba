package com.moduse.bamenalba;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

/**
 * Created by sejung on 2017-04-05.
 */

public class Setting_Activity extends Activity
{
    AppInfo appInfo;

    ProgressDialog loading;

    CheckBox chack_push;
    LinearLayout click_signout;

    //저장부분 로컬
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        appInfo = new AppInfo();
        loading = new ProgressDialog(this);
        InitShow();

        chack_push = (CheckBox) findViewById(R.id.tab5_sub_setting_push);
        click_signout = (LinearLayout) findViewById(R.id.tab5_sub_setting_signout);

        // 설정되어 있는 푸시 스위치값 적용
        chack_push.setChecked(AppInfo.My_PUSH_STATE);

        SettingLoad();  // 푸시저장 버튼 이벤트 리스너

        setting = getSharedPreferences("setting", 0);  // 로컬 불러오기 선언
        editor= setting.edit();

    }


    // 유저 세팅 저장하기 (푸시)
    public void SettingLoad()
    {
        chack_push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    editor.putBoolean("PUSH_STATE",true);
                    editor.commit();
                    AppInfo.My_PUSH_STATE = true;
                }
                else
                {
                    editor.putBoolean("PUSH_STATE",false);
                    editor.commit();
                    AppInfo.My_PUSH_STATE = false;
                }
            }
        });
    }


    // 닫기
    public void setting_close(View v)
    {
        finish();
    }


    //----------------------------------------------------- 프로그레스 설정------------------------//
    public void InitShow()
    {
        loading.setProgress(ProgressDialog.STYLE_SPINNER);
        loading.setCanceledOnTouchOutside(false);
        loading.setMessage("잠시만 기다려주세요.");
    }
    public void SetmsgShow(String value)
    {
        loading.setMessage(value);
    }
    public void StartShow() {loading.show();}
    public void StopShow() {loading.dismiss();}

}
