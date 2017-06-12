package com.lhd.slidemenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener
{

    private ImageView main_back;
    private SlideLayout ml_main;

    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_back = (ImageView)findViewById(R.id.main_back);
        ml_main = (SlideLayout)findViewById(R.id.ml_main);
        tv_title = (TextView)findViewById(R.id.tv_title);
        main_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        ml_main.switchMenu();
    }

    public void clickMenuItem(View v) {

        TextView textView= (TextView) v;
        tv_title.setText(textView.getText());
        ml_main.closeMenu();
    }

}
