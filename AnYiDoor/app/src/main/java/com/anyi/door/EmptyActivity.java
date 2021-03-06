package com.anyi.door;

import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.SharePref;

public class EmptyActivity extends BaseActivity
{

    @Bind(R.id.tv_show)
    TextView tvShow;

    @Bind(R.id.activity_empty)
    RelativeLayout activityEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        ButterKnife.bind(this);
        try
        {
            tvShow.setText("机型"+android.os.Build.MODEL+"\n厂商"+android.os.Build.MANUFACTURER);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        findViewById(R.id.bnCopy).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(mContext,"复制成功", Toast.LENGTH_SHORT).show();
                ClipboardManager cmb = (ClipboardManager) mContext
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(tvShow.getText().toString());
            }
        });

        findViewById(R.id.testBn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startTrain();

            }


        });
        if (GeneralUtils.isNullOrZeroLenght(SharePref.getString(Constants.LAST_UPDATE_TIME,""))){
            SharePref.saveString(Constants.LAST_UPDATE_TIME, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        }
    }
    private void startTrain()
    {
//        UserServiceImpl.instance().testDetail("1","");
//        UserServiceImpl.instance().finishTrain("1",null,"");
//        UserServiceImpl.instance().startTrain("1","2","");
    }
    @Override
    public void initView()
    {

    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }
}
