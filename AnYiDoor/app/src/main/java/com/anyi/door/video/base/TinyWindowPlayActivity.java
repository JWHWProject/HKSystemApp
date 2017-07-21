package com.anyi.door.video.base;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyi.door.R;
import com.anyi.door.utils.TakePicMethod;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.index.FinishTrainResponse;
import cn.nj.www.my_module.bean.index.TrainVideoResponse;
import cn.nj.www.my_module.bean.index.UploadFileResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.NetWorkResponse;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.FileSystemManager;
import cn.nj.www.my_module.tools.FileUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;


public class TinyWindowPlayActivity extends AppCompatActivity {
    private boolean isWatched = false;

    @Bind(R.id.iv_img1)
    ImageView ivImg1;

    @Bind(R.id.iv_img2)
    ImageView ivImg2;

    @Bind(R.id.iv_img3)
    ImageView ivImg3;

    @Bind(R.id.top_view_title_tv)
    TextView topViewTitleTv;

    private NiceVideoPlayer mNiceVideoPlayer;

    private Button bnFinish;

    private TakePicMethod takePicMethod;

    private TrainVideoResponse mTrainContentResponse;

    private String trainId;

    private MyTime myTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
//        //隐藏标题栏,有效
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_tiny_window_play);
        ButterKnife.bind(this);
        //获取数据
        mTrainContentResponse = GsonHelper.toType(getIntent().getStringExtra(IntentCode.CHOOSE_ID), TrainVideoResponse.class);
        trainId = getIntent().getStringExtra(IntentCode.TRAIN_ID);

        initTitle();
        init();

    }

    private void initTitle() {
        findViewById(R.id.top_view_close_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isWatched) {
                    DialogUtil.showCloseTwoBnttonDialog(TinyWindowPlayActivity.this,
                            "您确定要中途取消培训？", "取消", "确定");
                }else {
                    DialogUtil.showCloseTwoBnttonDialog(TinyWindowPlayActivity.this,
                            "您确定要关闭？", "取消", "确定");
                }

            }
        });
        topViewTitleTv.setText(mTrainContentResponse.getTraining().getTrainingName());
    }

    boolean flag = true;

    int time = 1;

    int maxtime = 1;

    int randomTime = -1;

    private int picCount = 1;

    private void init() {
        bnFinish = (Button) findViewById(R.id.app_finish_bn);
        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        mNiceVideoPlayer.setUp(mTrainContentResponse.getVideoUrl(), null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setGetDuration(new TxVideoPlayerController.IGetDuration() {
            @Override
            public void getDuration() {
                CMLog.e("hq", mNiceVideoPlayer.getDuration() + "获取到的时长");
                if (!isWatched) {
                    startTime(mNiceVideoPlayer.getDuration());
                }
            }
        });
        controller.setTitle(mTrainContentResponse.getTraining().getTrainingName());
        mNiceVideoPlayer.setController(controller);
        //初始化surface
        initSurface();
        takePicMethod = new TakePicMethod(TinyWindowPlayActivity.this, mySurfaceView, myHolder);
        bnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time > maxtime) {
                    picCount = 3;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        NetLoadingDialog.getInstance().loading(TinyWindowPlayActivity.this);
                        UserServiceImpl.instance().finishTrain(trainId,
                                null, FinishTrainResponse.class.getName(), new NetWorkResponse.NetCallBack() {

                                    @Override
                                    public void showCallback(BaseResponse event) {
                                        if (event instanceof NetResponseEvent) {
                                            String tag = ((NetResponseEvent) event).getTag();
                                            String result = ((NetResponseEvent) event).getResult();
                                            NetLoadingDialog.getInstance().dismissDialog();
                                            if (tag.equals(FinishTrainResponse.class.getName())) {
                                                NetLoadingDialog.getInstance().dismissDialog();
                                                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                                                    FinishTrainResponse finishTrainResponse = GsonHelper.toType(result, FinishTrainResponse.class);
                                                    if (Constants.SUCESS_CODE.equals(finishTrainResponse.getResultCode())) {
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                DialogUtil.showCloseDialogOneButton(TinyWindowPlayActivity.this, "完成培训", "我知道了", NotiTag.TAG_CLOSE_ACTIVITY);
                                                            }
                                                        });
                                                    }
                                                    else {
                                                        ErrorCode.doCode(TinyWindowPlayActivity.this, finishTrainResponse.getResultCode(), finishTrainResponse.getDesc());
                                                    }
                                                }
                                                else {
                                                    ToastUtil.showError(TinyWindowPlayActivity.this);
                                                }
                                            }
                                        }
                                    }
                                });
                    }
                    else {
                        TakePicture();
                    }
                }
                else {
                    DialogUtil.showDialogOneButton(
                            TinyWindowPlayActivity.this, "您现在还无法完成培训~", "我知道了"
                            , "");
                }
            }
        });
        picCount = 1;
        TakePicture();
        flag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (randomTime == -1) {
                        if (mNiceVideoPlayer.getDuration() != 0) {
                            long halftime = mNiceVideoPlayer.getDuration() / 2;
                            maxtime = (int) (halftime / 1000f);
                            Random random = new Random();
                            if (maxtime == 0) {
                                maxtime = 17;
                            }
                            randomTime = random.nextInt(maxtime);
                            time = 1;
                        }
                    }
                    else {
                        if (time == randomTime) {
                            picCount = 2;
                            TakePicture();
                        }
                        time++;
                    }
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
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return;
        }
        if (!isWatched) {
            DialogUtil.showCloseTwoBnttonDialog(TinyWindowPlayActivity.this,
                    "您确定要中途取消培训？", "取消", "确定");
        }else {
            DialogUtil.showCloseTwoBnttonDialog(TinyWindowPlayActivity.this,
                    "您确定要关闭？", "取消", "确定");
        }

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

    private boolean isTakeingPhoto = false;

    CountDownTimer countDownTimer;

    private void TakePicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bnFinish.setVisibility(View.VISIBLE);
            return;
        }
        if (!isTakeingPhoto) {
            isTakeingPhoto = true;
            if (countDownTimer == null) {
                countDownTimer = new CountDownTimer(10000, 3000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        takePicMethod.startTakePhoto("TinyWindowPlayActivity" + picCount);
                    }

                    @Override
                    public void onFinish() {
                        countDownTimer.cancel();
                        isTakeingPhoto = false;
                        try {
                            if (picCount == 1) {
                                ivImg1.setImageBitmap(BitmapFactory.decodeFile(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + picCount + ".jpg"));
                            }
                            else if (picCount == 2) {
                                bnFinish.setVisibility(View.VISIBLE);
                                ivImg2.setImageBitmap(BitmapFactory.decodeFile(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + picCount + ".jpg"));
                            }
                            else {
                                ivImg3.setImageBitmap(BitmapFactory.decodeFile(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + picCount + ".jpg"));
                            }
                            if (picCount == 3) {
                                List<File> files = null;
                                try {
                                    files = new ArrayList<>();
                                    files.add(new File(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + 1 + ".jpg"));
                                    files.add(new File(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + 2 + ".jpg"));
                                    files.add(new File(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + 3 + ".jpg"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (files.size() >= 0) {
                                    NetLoadingDialog.getInstance().loading(TinyWindowPlayActivity.this);
                                    UserServiceImpl.instance().uploadPic(files, UploadFileResponse.class.getName(), new NetWorkResponse.NetCallBack() {
                                        @Override
                                        public void showCallback(BaseResponse event) {
                                            if (event instanceof NetResponseEvent) {
                                                String tag = ((NetResponseEvent) event).getTag();
                                                String result = ((NetResponseEvent) event).getResult();
                                                NetLoadingDialog.getInstance().dismissDialog();
                                                if (tag.equals(UploadFileResponse.class.getName())) {
                                                    if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                                                        UploadFileResponse uploadFileResponse = GsonHelper.toType(result, UploadFileResponse.class);
                                                        if (Constants.SUCESS_CODE.equals(uploadFileResponse.getResultCode())) {
                                                            NetLoadingDialog.getInstance().loading(TinyWindowPlayActivity.this);
                                                            UserServiceImpl.instance().finishTrain(trainId,
                                                                    uploadFileResponse.getUrlList(), FinishTrainResponse.class.getName(), new NetWorkResponse.NetCallBack() {

                                                                        @Override
                                                                        public void showCallback(BaseResponse event) {
                                                                            if (event instanceof NetResponseEvent) {
                                                                                String tag = ((NetResponseEvent) event).getTag();
                                                                                String result = ((NetResponseEvent) event).getResult();
                                                                                NetLoadingDialog.getInstance().dismissDialog();
                                                                                if (tag.equals(FinishTrainResponse.class.getName())) {
                                                                                    NetLoadingDialog.getInstance().dismissDialog();
                                                                                    if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                                                                                        FinishTrainResponse finishTrainResponse = GsonHelper.toType(result, FinishTrainResponse.class);
                                                                                        if (Constants.SUCESS_CODE.equals(finishTrainResponse.getResultCode())) {
                                                                                            runOnUiThread(new Runnable() {
                                                                                                @Override
                                                                                                public void run() {
                                                                                                    DialogUtil.showCloseDialogOneButton(TinyWindowPlayActivity.this, "完成培训", "我知道了", NotiTag.TAG_CLOSE_ACTIVITY);
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                        else {
                                                                                            ErrorCode.doCode(TinyWindowPlayActivity.this, finishTrainResponse.getResultCode(), finishTrainResponse.getDesc());
                                                                                        }
                                                                                    }
                                                                                    else {
                                                                                        ToastUtil.showError(TinyWindowPlayActivity.this);
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                        else {
                                                            NetLoadingDialog.getInstance().dismissDialog();
                                                            ErrorCode.doCode(TinyWindowPlayActivity.this, uploadFileResponse.getResultCode(), uploadFileResponse.getDesc());
                                                        }
                                                    }
                                                    else {
                                                        NetLoadingDialog.getInstance().dismissDialog();
                                                        ToastUtil.showError(TinyWindowPlayActivity.this);
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                                else {
                                    NetLoadingDialog.getInstance().loading(TinyWindowPlayActivity.this);
                                    UserServiceImpl.instance().finishTrain(trainId,
                                            null, FinishTrainResponse.class.getName(), new NetWorkResponse.NetCallBack() {

                                                @Override
                                                public void showCallback(BaseResponse event) {
                                                    if (event instanceof NetResponseEvent) {
                                                        String tag = ((NetResponseEvent) event).getTag();
                                                        String result = ((NetResponseEvent) event).getResult();
                                                        NetLoadingDialog.getInstance().dismissDialog();
                                                        if (tag.equals(FinishTrainResponse.class.getName())) {
                                                            NetLoadingDialog.getInstance().dismissDialog();
                                                            if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                                                                FinishTrainResponse finishTrainResponse = GsonHelper.toType(result, FinishTrainResponse.class);
                                                                if (Constants.SUCESS_CODE.equals(finishTrainResponse.getResultCode())) {
                                                                    runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            DialogUtil.showCloseDialogOneButton(TinyWindowPlayActivity.this, "完成培训", "我知道了", NotiTag.TAG_CLOSE_ACTIVITY);
                                                                        }
                                                                    });
                                                                }
                                                                else {
                                                                    ErrorCode.doCode(TinyWindowPlayActivity.this, finishTrainResponse.getResultCode(), finishTrainResponse.getDesc());
                                                                }
                                                            }
                                                            else {
                                                                ToastUtil.showError(TinyWindowPlayActivity.this);
                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                };
            }
            if (countDownTimer != null) {
                countDownTimer.start();
            }
        }
    }

    /**
     * 倒计时
     */
    private class MyTime extends CountDownTimer {
        public MyTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            bnFinish.setEnabled(true);
            bnFinish.setText(getResources().getString(R.string.finish_train));
            isWatched = true;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            bnFinish.setEnabled(false);
            bnFinish.setText(getResources().getString(R.string.finish_train) + "(" + GeneralUtils.splitToSecondTime((millisUntilFinished / 1000) + "") + ")");
        }
    }

    private void startTime(long time) {
        cancelTime();
        myTime = new MyTime(time, Constants.Countdown_end);
        myTime.start();
    }

    private void cancelTime() {
        if (myTime != null) {
            myTime.cancel();
            myTime = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = false;
        cancelTime();
        FileUtil.deleteDirectory(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this));
    }


}
