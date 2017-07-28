package com.anyi.door.utils.signature;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.anyi.door.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.tools.FileSystemManager;

public class MainActivity extends AppCompatActivity
{

    @Bind(R.id.btn1)
    Button btn1;

    @Bind(R.id.btn2)
    Button btn2;

    @Bind(R.id.img1)
    ImageView img1;

    @Bind(R.id.img2)
    ImageView img2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_main);
        ButterKnife.bind(this);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(MainActivity.this, HandWriteActivity.class), 1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(MainActivity.this, LandscapeActivity.class), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == 100)
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bm = BitmapFactory.decodeFile(FileSystemManager.getSignaturePath(this), options);
            img1.setImageBitmap(bm);
        }

    }
}
