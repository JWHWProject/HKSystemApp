package com.anyi.door;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.tools.GeneralUtils;

public class WelcomeAndJudgeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //设置只能在特定机型上运行
//        if (android.os.Build.MODEL.equals("D08ADE19"))
//        {
        if (GeneralUtils.isNotNullOrZeroLenght(Global.getLoginData()))
        {
            startActivity(new Intent(this, MainActivity.class));
        }
        else
        {
            startActivity(new Intent(this, LoginActy.class));
        }
        finish();
//        }
//        else
//        {
//            ToastUtil.makeText(this,"无法在该机型上运行");
//            finish();
//        }
    }
}
