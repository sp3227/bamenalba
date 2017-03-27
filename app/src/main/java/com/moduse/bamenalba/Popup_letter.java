package com.moduse.bamenalba;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by sejung on 2017-03-23.
 */

public class Popup_letter extends Activity
{
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

    }

    public void click_letter_send(View v)
    {
        Toast.makeText(this.getApplicationContext(),"클릭욌다!!!",Toast.LENGTH_SHORT).show();
        finish();
    }


}
