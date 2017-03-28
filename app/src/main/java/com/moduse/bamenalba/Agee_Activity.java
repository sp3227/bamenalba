package com.moduse.bamenalba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Agee_Activity extends AppCompatActivity {

    String type;

    RadioGroup radioGroup;

    TextView ageetext;

    RadioButton btn_agee1;
    RadioButton btn_agee2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agee_activity);

        radioGroup = (RadioGroup) findViewById(R.id.agee_radio);
        ageetext = (TextView) findViewById(R.id.agee_text);
        btn_agee1 = (RadioButton) findViewById(R.id.agee_select1);
        btn_agee2 = (RadioButton) findViewById(R.id.agee_select2);


        Intent intent = getIntent();
        type = intent.getStringExtra("agee_type");

        if(type.toString().equals("agee1"))
        {
            radioGroup.check(R.id.agee_select1);
            ageetext.setText(getResources().getString(R.string.useterms_usecontent));
            btn_agee1.setTextColor(getResources().getColor(R.color.textwhite));
            btn_agee2.setTextColor(getResources().getColor(R.color.textcolor));
        }
        else if(type.toString().equals("agee2"))
        {
            radioGroup.check(R.id.agee_select2);
            ageetext.setText(getResources().getString(R.string.useterms_gpscontent));
            btn_agee1.setTextColor(getResources().getColor(R.color.textcolor));
            btn_agee2.setTextColor(getResources().getColor(R.color.textwhite));
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.agee_select1:
                    {
                        ageetext.setText(getResources().getString(R.string.useterms_usecontent));
                        btn_agee1.setTextColor(getResources().getColor(R.color.textwhite));
                        btn_agee2.setTextColor(getResources().getColor(R.color.textcolor));
                        break;
                    }
                    case R.id.agee_select2:
                    {
                        ageetext.setText(getResources().getString(R.string.useterms_gpscontent));
                        btn_agee1.setTextColor(getResources().getColor(R.color.textcolor));
                        btn_agee2.setTextColor(getResources().getColor(R.color.textwhite));
                        break;
                    }

                }
            }
        });

    }
}
