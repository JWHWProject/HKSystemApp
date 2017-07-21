package com.anyi.door;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.anyi.door.utils.TakePicMethod;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.FinishTrainResponse;
import cn.nj.www.my_module.bean.index.TrainContentResponse;
import cn.nj.www.my_module.bean.index.UploadFileResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.FileSystemManager;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.ImageLoaderUtil;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.imagepicker.PhotoPreviewActivity;
import cn.nj.www.my_module.view.imagepicker.model.PhotoModel;
import cn.nj.www.my_module.view.imagepicker.util.CommonUtils;

import static com.anyi.door.R.id.bn_finish;

/**
 * 完成培训视频
 */
public class TrainPicActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(bn_finish)
    Button bnFinish;

    @Bind(R.id.myListView)
    ListView myListView;

    private TrainContentResponse mTrainContentResponse;

    private String trainId;

    private CommonAdapter<TrainContentResponse.ImageBean> adapter;

    private MyTime myTime;
    private String timeStamp="";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acty_train_pic);
        ButterKnife.bind(this);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        timeStamp=sdf.format(new Date());
        bnFinish.setOnClickListener(this);
        mTrainContentResponse = GsonHelper.toType(getIntent().getStringExtra(IntentCode.CHOOSE_ID), TrainContentResponse.class);
        trainId = getIntent().getStringExtra(IntentCode.RECORD_ID);
        initAll();
    }


    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText(mTrainContentResponse.getTraining().getTrainingName());
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }


    @Override
    public void initView()
    {
        initTitle();
        final List<PhotoModel> single_photos = new ArrayList<PhotoModel>();
        for (int i = 0; i < mTrainContentResponse.getImageBeans().size(); i++)
        {
            single_photos.add(new PhotoModel(mTrainContentResponse.getImageBeans().get(i).getUrl()));
        }
        myListView.setAdapter(adapter = new CommonAdapter<TrainContentResponse.ImageBean>(mContext, mTrainContentResponse.getImageBeans(), R.layout.item_pic)
        {
            @Override
            public void convert(final ViewHolder helper, TrainContentResponse.ImageBean item)
            {
                ImageView iv = helper.getView(R.id.ivImg);
                ImageLoaderUtil.getInstance().initImage(mContext, item.getUrl(), iv, "");
                iv.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("photos", (Serializable) single_photos);
                        bundle.putInt("position", helper.getPosition());
                        bundle.putBoolean("isSave", true);
                        CommonUtils.launchActivity(TrainPicActivity.this, PhotoPreviewActivity.class, bundle);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        DialogUtil.showCloseTwoBnttonDialog(TrainPicActivity.this,
                "您确定要中途取消培训？", "取消", "确定");
    }

    boolean flag = true;

    int time = 1;

    int maxtime = 1;

    int randomTime = -1;

    private int picCount = 1;

    private TakePicMethod takePicMethod;

    @Override
    public void initViewData()
    {
        //初始化surface
        initSurface();
        takePicMethod = new TakePicMethod(TrainPicActivity.this, mySurfaceView, myHolder);
        picCount = 1;
        TakePicture();
        double d=Double.parseDouble(mTrainContentResponse.getImageBeans().size() * 4+ "");
        if(d<10){
            startTime(10d);
        }else {
            startTime(d);
        }
        flag = true;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (flag)
                {
                    try
                    {
                        Thread.sleep(1000);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    if (randomTime == -1)
                    {
                        if (mTrainContentResponse.getImageBeans().size() > 0)
                        {
//                            long halftime = mNiceVideoPlayer.getDuration() / 2;
//                            maxtime = (int) (halftime / 1000f);
                            maxtime = mTrainContentResponse.getImageBeans().size() * 4;

                            Random random = new Random();
                            if (maxtime <20)
                            {
                                maxtime = 20;
                            }
                            randomTime = random.nextInt(maxtime);
                            time = 1;
                        }
                    }
                    else
                    {
                        if (time == randomTime)
                        {
                            picCount = 2;
                            TakePicture();
                        }
                        time++;
                    }
                }
            }
        }).start();
    }

    private SurfaceView mySurfaceView;

    private SurfaceHolder myHolder;

    // 初始化surface
    @SuppressWarnings("deprecation")
    private void initSurface()
    {
        // 初始化surfaceview
        if (mySurfaceView == null && myHolder == null)
        {
            mySurfaceView = (SurfaceView) findViewById(R.id.camera_surfaceview);
            // 初始化surfaceholder
            myHolder = mySurfaceView.getHolder();
        }

    }

    private boolean isTakeingPhoto = false;

    CountDownTimer countDownTimer;

    private void TakePicture()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            return;
        }
        if (!isTakeingPhoto)
        {
            isTakeingPhoto = true;
            if (countDownTimer == null)
            {
                countDownTimer = new CountDownTimer(10000, 3000)
                {
                    @Override
                    public void onTick(long millisUntilFinished)
                    {
                        takePicMethod.startTakePhoto("TrainPicActivity_"+timeStamp+"_"+ picCount);
                    }

                    @Override
                    public void onFinish()
                    {
                        countDownTimer.cancel();
                        isTakeingPhoto = false;
                        try
                        {
//                            if (picCount == 1) {
//                                ivImg1.setImageBitmap(BitmapFactory.decodeFile(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + picCount + ".jpg"));
//                            } else if (picCount == 2) {
//                                ivImg2.setImageBitmap(BitmapFactory.decodeFile(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + picCount + ".jpg"));
//                            } else {
//                                ivImg3.setImageBitmap(BitmapFactory.decodeFile(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + picCount + ".jpg"));
//                            }
                            if (picCount == 3)
                            {
                                List<File> files = null;
                                try
                                {
                                    files = new ArrayList<>();
                                    try {
                                        File file1=new File(FileSystemManager.getSlientFilePath(TrainPicActivity.this) + File.separator + "TrainPicActivity_"+timeStamp+"_"+ 1 + ".jpg");
                                        if(file1.exists()){
                                            files.add(file1);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        File file2=new File(FileSystemManager.getSlientFilePath(TrainPicActivity.this) + File.separator + "TrainPicActivity_"+timeStamp+"_"+ 2 + ".jpg");
                                        if(file2.exists()){
                                            files.add(file2);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        File file3=new File(FileSystemManager.getSlientFilePath(TrainPicActivity.this) + File.separator + "TrainPicActivity_"+timeStamp+"_"+ 3 + ".jpg");
                                        if(file3.exists()){
                                            files.add(file3);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                if (files.size() >= 0)
                                {
                                    NetLoadingDialog.getInstance().loading(TrainPicActivity.this);
                                    UserServiceImpl.instance().uploadPic(files, UploadFileResponse.class.getName());
                                }
                                else
                                {
                                    NetLoadingDialog.getInstance().loading(TrainPicActivity.this);
                                    UserServiceImpl.instance().finishTrain(trainId, null, FinishTrainResponse.class.getName());
                                }
                            }
                        } catch (Exception e)
                        {
                        }
                    }
                };
            }
            if (countDownTimer != null)
            {
                countDownTimer.start();
            }
        }
    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    @Override
    public void onEventMainThread(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                DialogUtil.showCloseTwoBnttonDialog(TrainPicActivity.this,
                        "您确定要中途取消培训？", "取消", "确定");
            }
            if (NotiTag.TAG_CLOSE.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
        }
        else if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(FinishTrainResponse.class.getName()))
            {
                FinishTrainResponse mFinishTrainResponse = GsonHelper.toType(result, FinishTrainResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mFinishTrainResponse.getResultCode()))
                    {
                        DialogUtil.showDialogOneButton(mContext, "完成培训", "我知道了", NotiTag.TAG_CLOSE);
                    }
                    else
                    {
                        ErrorCode.doCode(this, mFinishTrainResponse.getResultCode(), mFinishTrainResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(UploadFileResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    UploadFileResponse uploadFileResponse = GsonHelper.toType(result, UploadFileResponse.class);
                    if (Constants.SUCESS_CODE.equals(uploadFileResponse.getResultCode()))
                    {
                        NetLoadingDialog.getInstance().loading(TrainPicActivity.this);
                        UserServiceImpl.instance().finishTrain(trainId, uploadFileResponse.getUrlList(), FinishTrainResponse.class.getName());
                    }
                    else
                    {
                        NetLoadingDialog.getInstance().dismissDialog();
                        ErrorCode.doCode(mContext, uploadFileResponse.getResultCode(), uploadFileResponse.getDesc());
                    }
                }
                else
                {
                    NetLoadingDialog.getInstance().dismissDialog();
                    ToastUtil.showError(mContext);
                }
            }
        }

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case bn_finish:
                //这边需要添加图片
                //获取到所有数据，提交
                if (bnFinish.getText().toString().trim().equals("完成培训"))
                {
                    picCount = 3;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        NetLoadingDialog.getInstance().loading(TrainPicActivity.this);
                        UserServiceImpl.instance().finishTrain(trainId, null, FinishTrainResponse.class.getName());
                    }
                    else
                    {
                        TakePicture();
                    }
                }
                else
                {
                    DialogUtil.showDialogOneButton(
                            TrainPicActivity.this, "您现在还无法完成培训~", "我知道了"
                            , "");
                }
                break;
        }
    }


    /**
     * 倒计时
     */
    private class MyTime extends CountDownTimer
    {
        public MyTime(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish()
        {
            bnFinish.setEnabled(true);
            bnFinish.setText(getResources().getString(R.string.finish_train));
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            bnFinish.setEnabled(false);
            bnFinish.setText(getResources().getString(R.string.finish_train) + "(" + GeneralUtils.splitToSecondTime((millisUntilFinished / 1000) + "") + ")");
        }
    }

    private void startTime(Double time)
    {
        cancelTime();
        myTime = new MyTime(time.longValue() * 1000, Constants.Countdown_end);
        myTime.start();
    }

    private void cancelTime()
    {
        if (myTime != null)
        {
            myTime.cancel();
            myTime = null;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        flag = false;
        cancelTime();
//        FileUtil.deleteDirectory(FileSystemManager.getSlientFilePath(TrainPicActivity.this));
    }

}
