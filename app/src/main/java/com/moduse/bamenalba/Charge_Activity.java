package com.moduse.bamenalba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Charge_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_letter_room);
    }


    // 팝업 테스트
    public void click_pop(View v)
    {
        Intent intent = new Intent(this, Popup_letter.class);
        startActivity(intent);
    }
}
