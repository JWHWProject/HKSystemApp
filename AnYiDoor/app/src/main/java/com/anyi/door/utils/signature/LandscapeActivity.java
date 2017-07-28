package com.anyi.door.utils.signature;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anyi.door.R;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.tools.FileSystemManager;

public class LandscapeActivity extends Activity {
    @Bind(R.id.view)
    LinePathView pathView;
    @Bind(R.id.clear1)
    Button mClear;
    @Bind(R.id.save1)
    Button mSave;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        setContentView(R.layout.activity_hand_write);
        ButterKnife.bind(this);
        setResult(50);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (pathView.getTouched())
            {
                try {
                    pathView.save(FileSystemManager.getImgPath(LandscapeActivity.this),false,10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setResult(101);
                finish();
            }else
            {
                Toast.makeText(LandscapeActivity.this,"您没有签名~",Toast.LENGTH_SHORT).show();
            }
        }});
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathView.clear();
            }
        });
    }



}
