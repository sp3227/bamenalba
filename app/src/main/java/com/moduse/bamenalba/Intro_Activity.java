package com.moduse.bamenalba;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Intro_Activity extends AppCompatActivity {


    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        loading = new ProgressDialog(this);
    }



    //----------------------------------------------------메인 시작 딜레이 타이머-----------------------------------------------//
    public void startmain()
    {
        Handler hd = new Handler();
        hd.postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                //accreditationToDatabase(appInfo.Get_AppVer(),appInfo.Get_CertificationKey(),appInfo.Get_CertificationURL());

            }
        }, 2000);
    }


    // 프로그레스 설정
    public void InitShow()
    {
        loading.setProgress(ProgressDialog.STYLE_SPINNER);
        loading.setCanceledOnTouchOutside(false);
        loading.setMessage("밤엔알바에 접속중입니다.");
    }
    public void SetmsgShow(String value)
    {
        loading.setMessage(value);
    }
    public void StartShow() {loading.show();}
    public void StopShow() {loading.dismiss();}
}
