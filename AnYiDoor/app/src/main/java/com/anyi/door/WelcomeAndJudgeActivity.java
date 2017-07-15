package com.anyi.door;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeAndJudgeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //设置只能在特定机型上运行
        if (android.os.Build.MODEL.equals("D08ADE19"))
        {
            startActivity(new Intent(this, LoginActy.class));
        }
        else
        {
            finish();
        }
    }
}
