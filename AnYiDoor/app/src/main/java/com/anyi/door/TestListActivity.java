package com.anyi.door;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anyi.door.test_utils.JudgeAdapter;
import com.anyi.door.test_utils.MultiltyAdapter;
import com.anyi.door.test_utils.SingleListAdapter;
import com.anyi.door.utils.TakePicMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.ExamBean;
import cn.nj.www.my_module.bean.index.ExamResponse;
import cn.nj.www.my_module.bean.index.FinishTestResponse;
import cn.nj.www.my_module.bean.index.OnlineTrainingAnswer;
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
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.MyListView;

/**
 * test
 */
public class TestListActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv1)
    TextView tv1;

    @Bind(R.id.ListView01)
    MyListView listView1;

    @Bind(R.id.tv2)
    TextView tv2;

    @Bind(R.id.ListView02)
    MyListView listView2;

    @Bind(R.id.getBn)
    Button bnFinish;

    @Bind(R.id.tv3)
    TextView tv3;

    @Bind(R.id.ListView03)
    MyListView listView3;

    private SingleListAdapter singleAdapter;

    private MultiltyAdapter multiltyAdapter;

    private JudgeAdapter judgeAdapter;


    private String examID;

    private TestListActivity.MyTime myTime;

    private String examName = "";

    private String timeStamp = "";

    private String trainId = "";

    private String examFinishID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sheet);
        ButterKnife.bind(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        timeStamp = sdf.format(new Date());
        initAll();
        initTitle();


    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText(examName + "考核");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    boolean flag = true;

    int time = 1;

    int maxtime = 1;

    int randomTime = -1;

    private int picCount = 1;

    private TakePicMethod takePicMethod;

    @Override
    public void initView() {
        //初始化surface
        initSurface();
        takePicMethod = new TakePicMethod(TestListActivity.this, mySurfaceView, myHolder);
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
                        if (answerList.size() > 0) {
//                            long halftime = mNiceVideoPlayer.getDuration() / 2;
//                            maxtime = (int) (halftime / 1000f);
                            maxtime = answerList.size() * 4;
                            Random random = new Random();
                            if (maxtime < 10) {
                                maxtime = 10;
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
            return;
        }
        if (!isTakeingPhoto) {
            isTakeingPhoto = true;
            if (countDownTimer == null) {
                countDownTimer = new CountDownTimer(10000, 3000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        takePicMethod.startTakePhoto("TestListActivity_" + timeStamp + "_" + picCount);
                    }

                    @Override
                    public void onFinish() {
                        countDownTimer.cancel();
                        isTakeingPhoto = false;
                        try {
//                            if (picCount == 1) {
//                                ivImg1.setImageBitmap(BitmapFactory.decodeFile(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + picCount + ".jpg"));
//                            } else if (picCount == 2) {
//                                ivImg2.setImageBitmap(BitmapFactory.decodeFile(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + picCount + ".jpg"));
//                            } else {
//                                ivImg3.setImageBitmap(BitmapFactory.decodeFile(FileSystemManager.getSlientFilePath(TinyWindowPlayActivity.this) + File.separator + "TinyWindowPlayActivity" + picCount + ".jpg"));
//                            }
                            if (picCount == 3) {
                                List<File> files = null;
                                try {
                                    files = new ArrayList<>();
                                    try {
                                        File file1 = new File(FileSystemManager.getSlientFilePath(TestListActivity.this) + File.separator + "TestListActivity_" + timeStamp + "_" + 1 + ".jpg");
                                        if (file1.exists()) {
                                            files.add(file1);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        File file2 = new File(FileSystemManager.getSlientFilePath(TestListActivity.this) + File.separator + "TestListActivity_" + timeStamp + "_" + 2 + ".jpg");
                                        if (file2.exists()) {
                                            files.add(file2);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        File file3 = new File(FileSystemManager.getSlientFilePath(TestListActivity.this) + File.separator + "TestListActivity_" + timeStamp + "_" + 3 + ".jpg");
                                        if (file3.exists()) {
                                            files.add(file3);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (files.size() >= 0) {
                                    NetLoadingDialog.getInstance().loading(TestListActivity.this, "提交中,请稍后");
                                    UserServiceImpl.instance().uploadPic(files, UploadFileResponse.class.getName());
                                }
                                else {
                                    NetLoadingDialog.getInstance().loading(TestListActivity.this);
                                    UserServiceImpl.instance().finishTest(examFinishID, answerList, null,
                                            FinishTestResponse.class.getName());
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
    public void initViewData() {
        NetLoadingDialog.getInstance().loading(mContext);
        examID = getIntent().getStringExtra(IntentCode.EXAM_ID);
        examFinishID = getIntent().getStringExtra(IntentCode.EXAM_FINISH_ID);
        if (GeneralUtils.isNotNullOrZeroLenght(getIntent().getStringExtra(IntentCode.EXAM_NAME))) {
            examName = getIntent().getStringExtra(IntentCode.EXAM_NAME);
        }
        UserServiceImpl.instance().testDetail(trainId, ExamResponse.class.getName());
    }

    List<OnlineTrainingAnswer> answerList = new ArrayList<>();

    @Override
    public void initEvent() {

        bnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断
                if (judgeAdapter != null) {
                    if (judgeAdapter.isFinishAll()) {
                        answerList.addAll(judgeAdapter.getAnswerList());
                    }
                    else {
                        ToastUtil.makeText(mContext, "请完成所有题目后提交");
                        return;
                    }
                }
                //单选
                if (singleAdapter != null) {
                    if (singleAdapter.isFinishAll()) {
                        answerList.addAll(singleAdapter.getAnswerList());
                    }
                    else {
                        ToastUtil.makeText(mContext, "请完成所有题目后提交");
                        return;
                    }
                }
                //多选
                if (multiltyAdapter != null) {
                    if (multiltyAdapter.isFinishAll()) {
                        answerList.addAll(multiltyAdapter.getAnswerList());
                    }
                    else {
                        ToastUtil.makeText(mContext, "请完成所有题目后提交");
                        return;
                    }
                }
                //获取到所有数据，提交
                if (bnFinish.getText().toString().trim().equals("提交")) {
                    picCount = 3;
                    NetLoadingDialog.getInstance().loading(TestListActivity.this, "提交中，请稍后");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        NetLoadingDialog.getInstance().loading(TestListActivity.this);
                        UserServiceImpl.instance().finishTest(examFinishID, answerList, null,
                                FinishTestResponse.class.getName());
                        bnFinish.setEnabled(false);
                    }
                    else {
                        bnFinish.setEnabled(false);
                        NetLoadingDialog.getInstance().loading(TestListActivity.this, "提交中,请稍后");
                        TakePicture();
                    }
                }
                else {
                    DialogUtil.showDialogOneButton(
                            TestListActivity.this, "您现在还无法完成考核~", "我知道了"
                            , "");
                }
            }
        });
    }

    @Override
    public void netResponse(BaseResponse event) {

    }


    @Override
    public void onClick(View view) {

    }

    private List<ExamBean> examBeanList = new ArrayList<>();

    @Override
    public void onEventMainThread(BaseResponse event) throws JSONException {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();

            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                DialogUtil.showCloseTwoBnttonDialog(mContext,
                        "您确定要中途取消考核？", "取消", "确定");
            }
            if (NotiTag.TAG_CLOSE_ACTIVITY_FROM_DIALOG.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
            if (NotiTag.TAG_CLOSE.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
        }
        else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(ExamResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    ExamResponse mExamResponse = GsonHelper.toType(result, ExamResponse.class);
                    if (Constants.SUCESS_CODE.equals(mExamResponse.getResultCode())) {
                        examBeanList.clear();
                        JSONObject jsonObject = new JSONObject(result);
                        Map<String, List<ExamBean.ExamDetailBean>> map = new Gson().fromJson(jsonObject.getString("typeMap"), new TypeToken<Map<String, List<ExamBean.ExamDetailBean>>>() {
                        }.getType());
                        Iterator entries = map.entrySet().iterator();
                        while (entries.hasNext()) {
                            Map.Entry entry = (Map.Entry) entries.next();
                            String key = (String) entry.getKey();
                            List<ExamBean.ExamDetailBean> valueList = (List<ExamBean.ExamDetailBean>) entry.getValue();
                            examBeanList.add(new ExamBean(key, valueList));
                            if (key.equals("单选题")) {
                                tv2.setVisibility(View.VISIBLE);
                                singleAdapter = new SingleListAdapter(mContext, valueList, examID);
                                listView2.setAdapter(singleAdapter);
                            }
                            if (key.equals("判断题")) {
                                tv1.setVisibility(View.VISIBLE);
                                judgeAdapter = new JudgeAdapter(mContext, valueList, examID);
                                listView1.setAdapter(judgeAdapter);
                            }
                            if (key.equals("多选题")) {
                                tv3.setVisibility(View.VISIBLE);
                                multiltyAdapter = new MultiltyAdapter(mContext, valueList, examID);
                                listView3.setAdapter(multiltyAdapter);
                            }

                        }
                        double d = Double.parseDouble(examBeanList.size() * 4 + "");
                        if (d < 20) {
                            startTime(20d);
                        }
                        else {
                            startTime(d);
                        }
                    }
                    else {
                        ErrorCode.doCode(mContext, mExamResponse.getResultCode(), mExamResponse.getDesc());
                    }
                }
                else {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(FinishTestResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    FinishTestResponse mFinishTestResponse = GsonHelper.toType(result, FinishTestResponse.class);
                    if (Constants.SUCESS_CODE.equals(mFinishTestResponse.getResultCode())) {
                        DialogUtil.showDialogOneButton(mContext, "已完成考核", "我知道了", NotiTag.TAG_CLOSE);
                    }
                    else {
                        bnFinish.setEnabled(true);
                        ErrorCode.doCode(mContext, mFinishTestResponse.getResultCode(), mFinishTestResponse.getDesc());
                    }
                }
                else {
                    bnFinish.setEnabled(true);
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(UploadFileResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    UploadFileResponse uploadFileResponse = GsonHelper.toType(result, UploadFileResponse.class);
                    if (Constants.SUCESS_CODE.equals(uploadFileResponse.getResultCode())) {
                        NetLoadingDialog.getInstance().loading(TestListActivity.this, "提交中，请稍后");
                        UserServiceImpl.instance().finishTest(examFinishID, answerList, uploadFileResponse.getUrlList(),
                                FinishTestResponse.class.getName());
                    }
                    else {
                        NetLoadingDialog.getInstance().dismissDialog();
                        bnFinish.setEnabled(true);
                        ErrorCode.doCode(mContext, uploadFileResponse.getResultCode(), uploadFileResponse.getDesc());
                    }
                }
                else {
                    NetLoadingDialog.getInstance().dismissDialog();
                    bnFinish.setEnabled(true);
                    ToastUtil.showError(mContext);
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        DialogUtil.showCloseTwoBnttonDialog(mContext,
                "您确定要中途取消考核？", "取消", "确定");
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
            bnFinish.setText("提交");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            bnFinish.setEnabled(false);
            bnFinish.setText("提交(" + GeneralUtils.splitToSecondTime((millisUntilFinished / 1000) + "") + ")");
        }
    }

    private void startTime(Double time) {
        cancelTime();
        bnFinish.setEnabled(false);
        myTime = new MyTime(time.longValue() * 1000, Constants.Countdown_end);
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
//        FileUtil.deleteDirectory(FileSystemManager.getSlientFilePath(TestListActivity.this));
    }

}
