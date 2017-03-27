package com.moduse.bamenalba;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by sejung on 2017-03-22.
 */

public class Tab_3 extends Activity
{

    public LinearLayout in_layout;
    public LayoutInflater Inflater;


    Tab_3()
    {
        Inflater = ((Main) Main.MainContext).getLayoutInflater();
        Inflater = (LayoutInflater) Main.MainContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        in_layout = (LinearLayout) Inflater.inflate(R.layout.tab_3, null);


    }


}
