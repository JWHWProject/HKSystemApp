package com.anyi.door.video.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.anyi.door.R;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;


public class TinyWindowPlayActivity extends AppCompatActivity {

    private NiceVideoPlayer mNiceVideoPlayer;

    private Button bnFinish;

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
        bnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return;
        }
        super.onBackPressed();
    }
}
