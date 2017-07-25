package com.anyi.door;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.tools.GeneralUtils;

public class WelcomeAndJudgeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //设置只能在特定机型上运行
        if (android.os.Build.MODEL.equals("FP07"))
        {
            if (GeneralUtils.isNotNullOrZeroLenght(Global.getLoginData()))
            {
                startActivity(new Intent(this, MainActivity.class));
            }
            else
            {
                startActivity(new Intent(this, LoginActy.class));
            }
            finish();
        }
        else
        {

            Button button = new Button(WelcomeAndJudgeActivity.this);
            setContentView(button);
            button.setText("本机机型为："+android.os.Build.MODEL+"\n该应用只能在定制平板上运行");
        }
    }
}
