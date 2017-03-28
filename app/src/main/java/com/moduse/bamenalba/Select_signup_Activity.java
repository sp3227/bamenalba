package com.moduse.bamenalba;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Select_signup_Activity extends AppCompatActivity {


    final int Dialog_Select_jober = 100;
    final int Dialog_Select_company = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_select_activity);

    }

    //------------------------------------------------ 구직자 가입 선택-------------//
    public void click_select_signup_joper(View v)
    {
        showDialog(Dialog_Select_jober);

    }

    //------------------------------------------------ 업체 가입 선택-------------//
    public void click_select_signup_company(View v)
    {
        showDialog(Dialog_Select_company);
    }



    //------------------------------------------------ 가입 선택 확인용 다이얼로그-------------//
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id)
        {
            case Dialog_Select_jober:  // 구직자로 가입 확인
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("◎ 구직자로 가입") // 제목 설정
                        .setMessage("구직자로 가입하시겠습니까?\n(가입 후 변경이 불가능합니다.)")
                        .setCancelable(true)  //뒤로 버튼 클릭시 취소 설정
                        .setPositiveButton("맞아요", new DialogInterface.OnClickListener() {
                            // 예 버튼 클릭시 설정
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                Intent intent = new Intent(getApplicationContext(), Signup_P_Activity.class);
                                intent.putExtra("signup_type", "joper");

                                startActivity(intent);

                                finish();

                            }
                        })
                        .setNegativeButton("아니요", null).show();

                AlertDialog alert = builder.create();  //알림 객체 생성

                break;
               // return alert;
            }
            case Dialog_Select_company:  // 구인업체로 가입 확인
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

                builder.setTitle("◎ 구인업체로 가입") // 제목 설정
                        .setMessage("구인업체로 가입하시겠습니까?\n(가입 후 변경이 불가능합니다.)")
                        .setCancelable(true)  //뒤로 버튼 클릭시 취소 설정
                        .setPositiveButton("맞아요", new DialogInterface.OnClickListener() {
                            // 예 버튼 클릭시 설정
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                Intent intent = new Intent(getApplicationContext(), Signup_P_Activity.class);
                                intent.putExtra("signup_type", "company");

                                startActivity(intent);

                                finish();

                            }
                        })
                        .setNegativeButton("아니요", null).show();

                AlertDialog alert = builder.create();  //알림 객체 생성

               // return alert;
                break;
            }

        }
        return null;
    }


}
