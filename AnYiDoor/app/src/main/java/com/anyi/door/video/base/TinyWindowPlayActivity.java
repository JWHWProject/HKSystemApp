package com.anyi.door.video.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.anyi.door.R;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;


public class TinyWindowPlayActivity extends AppCompatActivity
{

    private NiceVideoPlayer mNiceVideoPlayer;

    private Button bnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiny_window_play);
        //隐藏标题栏,有效
        getSupportActionBar().hide();
        init();
    }

    private void init()
    {
        bnFinish = (Button) findViewById(R.id.app_finish_bn);
        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        mNiceVideoPlayer.setUp("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4", null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
        controller.setLenght(mNiceVideoPlayer.getDuration());
//        controller.setLenght(98000);
        mNiceVideoPlayer.setController(controller);
        bnFinish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }

    boolean isFirst = true;

    @Override
    protected void onResume()
    {
        super.onResume();
        if (isFirst)
        {
            isFirst = false;
            return;
        }
        if (mNiceVideoPlayer.isCompleted())
        {
            bnFinish.setEnabled(true);
        }
        else
        {
            bnFinish.setEnabled(false);
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed()
    {
        if (NiceVideoPlayerManager.instance().onBackPressd())
        {
            return;
        }
        super.onBackPressed();
    }
}
