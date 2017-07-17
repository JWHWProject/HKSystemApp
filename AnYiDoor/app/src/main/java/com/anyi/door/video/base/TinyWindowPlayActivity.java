package com.anyi.door.video.base;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anyi.door.R;
import com.anyi.door.utils.TakePicMethod;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.io.File;
import java.util.Random;


public class TinyWindowPlayActivity extends AppCompatActivity {

    private NiceVideoPlayer mNiceVideoPlayer;

    private Button bnFinish;
    private TakePicMethod takePicMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiny_window_play);
        //隐藏标题栏,有效
        getSupportActionBar().hide();
        initTitle();
        init();
    }

    private void initTitle() {
        findViewById(R.id.top_view_close_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    boolean flag=true;
    int time=1;
    int maxtime=1;
    private int picCount=1;
    private void init() {
        bnFinish = (Button) findViewById(R.id.app_finish_bn);
        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        mNiceVideoPlayer.setUp("http://www.12365aq.cn/f/file/20/screenContent/ad8230d2-39e8-4926-ad6e-e98a109ff4f1.mp4", null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("测试视频");
        //获取总时长
        controller.setLenght(mNiceVideoPlayer.getDuration());
        //现在的时长
//        mNiceVideoPlayer.getCurrentPosition();

        mNiceVideoPlayer.setController(controller);
        //初始化surface
        initSurface();
        takePicMethod=new TakePicMethod(TinyWindowPlayActivity.this,mySurfaceView,myHolder);
        bnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time>maxtime) {
                    picCount=3;
                    TakePicture();
                }else{
                    Toast.makeText(TinyWindowPlayActivity.this,"您现在还无法完成培训,还没有达到培训时间!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        picCount=1;
        TakePicture();
        long halftime=mNiceVideoPlayer.getDuration()/2;
        maxtime=(int)(halftime/1000f);
        Random random = new Random();
        final int randomTime=random.nextInt(maxtime);
        time=1;
        flag=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(time==randomTime){
                        picCount=2;
                        TakePicture();
                    }
                    time++;
                }
            }
        }).start();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag=false;
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return;
        }
        super.onBackPressed();
    }
    private SurfaceView mySurfaceView;
    private SurfaceHolder myHolder;
    // 初始化surface
    @SuppressWarnings("deprecation")
    private void initSurface() {
        // 初始化surfaceview
        if (mySurfaceView == null && myHolder == null) {
            mySurfaceView = (SurfaceView) findViewById(R.id.camera_surfaceview);
            // 初始化surfaceholder
            myHolder = mySurfaceView.getHolder();
        }

    }
    private boolean isTakeingPhoto=false;
    private void TakePicture(){
        if(isTakeingPhoto) {
            isTakeingPhoto = true;
            CountDownTimer countDownTimer = new CountDownTimer(10000, 2000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    takePicMethod.startTakePhoto();
                }

                @Override
                public void onFinish() {
                    isTakeingPhoto = false;
                        try {
                            File file = new File(takePicMethod.getPictureFromFile());

                            if (takePicMethod.path != null && !takePicMethod.path.trim().equals("")) {
                                takePicMethod.resizePhoto();
                            }

//                            //将图片显示到textView上面
//                            Glide.with(TinyWindowPlayActivity.this).load(file).asBitmap().into(mPhoto);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                }
            }.start();
        }
    }

}
