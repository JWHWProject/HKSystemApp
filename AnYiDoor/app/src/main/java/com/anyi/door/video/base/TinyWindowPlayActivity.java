package com.anyi.door.video.base;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anyi.door.R;
import com.anyi.door.TestListActivity;
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
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.FinishTrainResponse;
import cn.nj.www.my_module.bean.index.TrainVideoResponse;
import cn.nj.www.my_module.bean.index.UploadFileResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.FileSystemManager;
import cn.nj.www.my_module.tools.FileUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;


public class TinyWindowPlayActivity extends BaseActivity {

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

    private boolean peiXunComplete = false;

    private TrainVideoResponse mTrainContentResponse;

    private String trainId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiny_window_play);
        ButterKnife.bind(this);
        //获取数据
        mTrainContentResponse = GsonHelper.toType(getIntent().getStringExtra(IntentCode.CHOOSE_ID), TrainVideoResponse.class);
        trainId = getIntent().getStringExtra(IntentCode.TRAIN_ID);
        //隐藏标题栏,有效
//        getSupportActionBar().hide();
        initTitle();
        init();

    }

    @Override
    public void initView() {

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event) {

    }

    private void initTitle() {
        if (!peiXunComplete) {
            findViewById(R.id.top_view_close_iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtil.showCloseTwoBnttonDialog(TinyWindowPlayActivity.this,
                            "您确定要中途离开培训？", "取消", "确定");
                }
            });
        } else {
            finish();
        }
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
        controller.setTitle("测试视频");
        //获取总时长
        controller.setLenght(mNiceVideoPlayer.getDuration());
        //现在的时长
//        mNiceVideoPlayer.getCurrentPosition();

        mNiceVideoPlayer.setController(controller);


        //初始化surface
        initSurface();
        takePicMethod = new TakePicMethod(TinyWindowPlayActivity.this, mySurfaceView, myHolder);
        bnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time > maxtime) {
                    picCount = 3;
                    TakePicture();
                } else {
                    DialogUtil.showDialogOneButton(
                            TinyWindowPlayActivity.this, "您现在还无法完成培训,还没有达到培训时间!", "我知道了"
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
                    } else {
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
    protected void onDestroy() {
        super.onDestroy();
        flag = false;
        FileUtil.deleteDirectory(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this));
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return;
        }
        DialogUtil.showCloseTwoBnttonDialog(mContext,
                "您确定要中途取消考核？", "取消", "确定");
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

    private boolean isTakeingPhoto = false;

    CountDownTimer countDownTimer;

    private void TakePicture() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)return;
        if (!isTakeingPhoto) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TinyWindowPlayActivity.this, "拍照中,请您对准摄像头注视5秒",Toast.LENGTH_SHORT).show();
                }
            });
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
                            } else if (picCount == 2) {
                                ivImg2.setImageBitmap(BitmapFactory.decodeFile(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + picCount + ".jpg"));
                            } else {
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
                                if(files.size()>=0) {
                                    UserServiceImpl.instance().uploadPic(files, UploadFileResponse.class.getName());
                                }else{
                                    UserServiceImpl.instance().finishTrain(trainId,
                                            null, FinishTrainResponse.class.getName());
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

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
        }
        if (event instanceof NetResponseEvent) {

            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(FinishTrainResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    FinishTrainResponse finishTrainResponse = GsonHelper.toType(result, FinishTrainResponse.class);
                    if (Constants.SUCESS_CODE.equals(finishTrainResponse.getResultCode())) {
                        peiXunComplete = true;
                        DialogUtil.showDialogOneButton(mContext, "完成培训", "我知道了", NotiTag.TAG_CLOSE_ACTIVITY);
                    } else {
                        ErrorCode.doCode(mContext, finishTrainResponse.getResultCode(), finishTrainResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(UploadFileResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    UploadFileResponse uploadFileResponse = GsonHelper.toType(result, UploadFileResponse.class);
                    if (Constants.SUCESS_CODE.equals(uploadFileResponse.getResultCode())) {
                        NetLoadingDialog.getInstance().loading(mContext);
                        UserServiceImpl.instance().finishTrain(trainId,
                                uploadFileResponse.getUrlList(), FinishTrainResponse.class.getName());
                    } else {
                        NetLoadingDialog.getInstance().dismissDialog();
                        ErrorCode.doCode(mContext, uploadFileResponse.getResultCode(), uploadFileResponse.getDesc());
                    }
                } else {
                    NetLoadingDialog.getInstance().dismissDialog();
                    ToastUtil.showError(mContext);
                }
            }

        }
    }
}
