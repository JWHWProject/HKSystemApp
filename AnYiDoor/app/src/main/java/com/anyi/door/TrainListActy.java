package com.anyi.door;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyi.door.video.base.TinyWindowPlayActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.StartTestResponse;
import cn.nj.www.my_module.bean.index.StartTrainResponse;
import cn.nj.www.my_module.bean.index.TrainBean;
import cn.nj.www.my_module.bean.index.TrainContentResponse;
import cn.nj.www.my_module.bean.index.TrainListResponse;
import cn.nj.www.my_module.bean.index.TrainVideoResponse;
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
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;

import static cn.nj.www.my_module.constant.IntentCode.TRAIN_ID;

/**
 * train list
 */
public class TrainListActy extends BaseActivity implements View.OnClickListener
{

    public String tagStr = "";

    private ExpandableListView listView;

    private List<TrainBean> trainBeanList = new ArrayList<>();

    private List<TrainBean> trainBeanListCopy = new ArrayList<>();

    private String trainID = "";

    private String fromTest = "";

    private String recordID = "";

    private String fileType = "";

    @Bind(R.id.iv_search_clear)
    ImageView ivSearchClear;

    @Bind(R.id.tv_search)
    TextView tvSearch;

    @Bind(R.id.et_search)
    EditText etSearch;

    private View topView;

    private String cardNum = "";

    private String selectedTestName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);
        ButterKnife.bind(this);
        if (GeneralUtils.isNotNullOrZeroLenght(getIntent().getStringExtra(IntentCode.TEST_INTENT)))
        {
            fromTest = getIntent().getStringExtra(IntentCode.TEST_INTENT);
        }
        if (GeneralUtils.isNotNullOrZeroLenght(getIntent().getStringExtra(TRAIN_ID)))
        {
            trainID = getIntent().getStringExtra(IntentCode.TRAIN_ID);
            cardNum = getIntent().getStringExtra(IntentCode.CARD_NUM);
        }


        initAll();
    }


    private void initTitle()
    {
        topView = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) topView);
        headView.setTitleText("培训");
        headView.setLeftImage(R.mipmap.app_title_back);
        if (fromTest.equals("1"))
        {
            headView.setHiddenRight();
            headView.setTitleText("考核列表");
        }
        else
        {
            headView.setTitleText("培训");
            headView.setRightText("搜索");
            //从发卡成功界面进入的
            if (GeneralUtils.isNotNullOrZeroLenght(trainID) && GeneralUtils.isNotNullOrZeroLenght(cardNum))
            {
                DialogUtil.showNoTipTwoBnttonDialog(mContext, "确定开始培训", "取消", "确定", NotiTag.TAG_DLG_CANCEL, NotiTag.TAG_DLG_OK);
            }
        }
    }


    @Override
    public void initView()
    {
        initTitle();
        findViewById(R.id.finish_iv).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                findViewById(R.id.search_view).setVisibility(View.GONE);
                topView.setVisibility(View.VISIBLE);
                UserServiceImpl.instance().trainList(TrainListResponse.class.getName());
            }
        });
        ivSearchClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                etSearch.setText("");
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (GeneralUtils.isNotNullOrZeroLenght(etSearch.getText().toString()))
                {
                    NetLoadingDialog.getInstance().loading(mContext);
                    UserServiceImpl.instance().trainList(etSearch.getText().toString(),
                            TrainListResponse.class.getName());
                }
                else
                {
                    ToastUtil.makeText(mContext, "请输入搜索内容");
                }
            }
        });

        listView = (ExpandableListView) findViewById(R.id.list);
        listView.setGroupIndicator(null);

        //只展开一个
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener()
        {

            @Override
            public void onGroupExpand(int groupPosition)
            {
                for (int i = 0; i < 4; i++)
                {
                    if (groupPosition != i)
                    {
                        listView.collapseGroup(i);
                    }
                }

            }

        });

        //点击跳转
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {

            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id)
            {
                trainID = trainBeanList.get(groupPosition).getTrainBeanDetailList().get(childPosition).getId();
                fileType = trainBeanList.get(groupPosition).getTrainBeanDetailList().get(childPosition).getFileType();
                if (fromTest.equals(""))
                {
                    DialogUtil.startTrainDialog(mContext, NotiTag.TAG_START_TRAIN_DIALOG);

                }
                else if (fromTest.equals("1"))
                {
                    selectedTestName = trainBeanList.get(groupPosition).getTrainBeanDetailList().get(childPosition).getTrainingName();
                    DialogUtil.startTestDialog(mContext, NotiTag.TAG_START_TEST_DIALOG);
                }
                return false;
            }
        });
    }

    @Override
    public void initViewData()
    {
        NetLoadingDialog.getInstance().loading(mContext);
        UserServiceImpl.instance().trainList(TrainListResponse.class.getName());
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
                finish();
            }
            if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                topView.setVisibility(View.GONE);
                findViewById(R.id.search_view).setVisibility(View.VISIBLE);

            }
            if (NotiTag.TAG_START_TRAIN_DIALOG.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                NetLoadingDialog.getInstance().loading(mContext);
                String card = ((NoticeEvent) event).getUrl1();
                String name = ((NoticeEvent) event).getUrl2();
                NetLoadingDialog.getInstance().loading(mContext);
                String userId = GeneralUtils.isUserExistBackUserId(name);
                //判断人名是否存在
                if (GeneralUtils.isNotNullOrZeroLenght(userId))
                {
                    UserServiceImpl.instance().startTrain(trainID, card, userId, StartTrainResponse.class.getName());
                }
                else
                {
                    NetLoadingDialog.getInstance().dismissDialog();
                    ToastUtil.makeText(mContext, "不存在该人员信息");
                }
            }
            if (NotiTag.TAG_START_TEST_DIALOG.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                NetLoadingDialog.getInstance().loading(mContext);
                String card = ((NoticeEvent) event).getUrl1();
                String name = ((NoticeEvent) event).getUrl2();
                NetLoadingDialog.getInstance().loading(mContext);
                String userId = GeneralUtils.isUserExistBackUserId(name);
                //判断人名是否存在
                if (GeneralUtils.isNotNullOrZeroLenght(userId))
                {
                    UserServiceImpl.instance().startOnlineTest(trainID, card, StartTestResponse.class.getName());
                }
                else
                {
                    NetLoadingDialog.getInstance().dismissDialog();
                    ToastUtil.makeText(mContext, "不存在该人员信息");
                }
            }
            if (NotiTag.TAG_DLG_OK.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                //调用开始培训的接口
                NetLoadingDialog.getInstance().loading(mContext);
                if (fromTest.equals(""))
                {
                    UserServiceImpl.instance().startTrain(trainID, cardNum, "", StartTrainResponse.class.getName());
                }
                else if (fromTest.equals("1"))
                {
                    UserServiceImpl.instance().startOnlineTest(trainID, "", StartTestResponse.class.getName());
                }
            }
        }
        else if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(StartTestResponse.class.getName()))
            {
                StartTestResponse mStartTestResponse = GsonHelper.toType(result, StartTestResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mStartTestResponse.getResultCode()))
                    {
                        Intent testIntent = new Intent(mContext, TestListActivity.class);
                        testIntent.putExtra(IntentCode.EXAM_ID, mStartTestResponse.getExamID());
                        testIntent.putExtra(IntentCode.TRAIN_ID, trainID);
                        testIntent.putExtra(IntentCode.EXAM_FINISH_ID, trainID);
                        testIntent.putExtra(IntentCode.EXAM_NAME, selectedTestName);
                        startActivity(testIntent);
                    }
                    else
                    {
                        ErrorCode.doCode(this, mStartTestResponse.getResultCode(), mStartTestResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(StartTrainResponse.class.getName()))
            {
                StartTrainResponse mStartTrainResponse = GsonHelper.toType(result, StartTrainResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mStartTrainResponse.getResultCode()))
                    {
                        //因为视频页面没有集成EventBus，就在该页面获取到数据后再传过去
                        recordID = mStartTrainResponse.getRecordID();
                        if (GeneralUtils.isNotNullOrZeroLenght(recordID))
                        {
                            NetLoadingDialog.getInstance().loading(mContext);
                            UserServiceImpl.instance().trainContent(trainID, TrainContentResponse.class.getName());
                        }
                        else
                        {
                            ToastUtil.makeText(mContext, "无培训记录的ID");
                        }

                    }
                    else
                    {
                        ErrorCode.doCode(this, mStartTrainResponse.getResultCode(), mStartTrainResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(TrainContentResponse.class.getName()))
            {
                TrainContentResponse mTrainContentResponse = GsonHelper.toType(result, TrainContentResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mTrainContentResponse.getResultCode()))
                    {

                        if (mTrainContentResponse.getTraining().getFileType() == 1)
                        {//图片
                            Intent intent = new Intent(mContext, TrainPicActivity.class);
                            intent.putExtra(IntentCode.CHOOSE_ID, result);
                            intent.putExtra(IntentCode.RECORD_ID, recordID);
                            startActivity(new Intent(intent));
                        }
                        else if (mTrainContentResponse.getTraining().getFileType() == 2)
                        {
                            Intent intent = new Intent(mContext, TinyWindowPlayActivity.class);
                            intent.putExtra(IntentCode.CHOOSE_ID, result);
                            intent.putExtra(IntentCode.TRAIN_ID, recordID);
                            startActivity(new Intent(intent));
                        }else {
                            Intent intent = new Intent(mContext, TrainH5Activity.class);
                            intent.putExtra(IntentCode.CHOOSE_ID, result);
                            intent.putExtra(IntentCode.TRAIN_ID, recordID);
                            intent.putExtra(IntentCode.COMMON_WEB_VIEW_URL, mTrainContentResponse.getFileUrl());
                            startActivity(new Intent(intent));
                        }

                    }
                    else
                    {
                        ErrorCode.doCode(this, mTrainContentResponse.getResultCode(), mTrainContentResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(TrainVideoResponse.class.getName()))
            {
                TrainVideoResponse mTrainContentResponse = GsonHelper.toType(result, TrainVideoResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mTrainContentResponse.getResultCode()))
                    {

                    }
                    else
                    {
                        ErrorCode.doCode(this, mTrainContentResponse.getResultCode(), mTrainContentResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(TrainListResponse.class.getName()))
            {
                TrainListResponse mTrainListResponse = GsonHelper.toType(result, TrainListResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mTrainListResponse.getResultCode()))
                    {
                        trainBeanList.clear();
                        try
                        {
                            JSONObject jsonObject = new JSONObject(result);
                            Map<String, List<TrainBean.TrainBeanDetail>> map = new Gson().fromJson(jsonObject.getString("typeMap"), new TypeToken<Map<String, List<TrainBean.TrainBeanDetail>>>()
                            {
                            }.getType());
                            Iterator entries = map.entrySet().iterator();
                            while (entries.hasNext())
                            {
                                Map.Entry entry = (Map.Entry) entries.next();
                                String key = (String) entry.getKey();
                                List<TrainBean.TrainBeanDetail> valueList = (List<TrainBean.TrainBeanDetail>) entry.getValue();
                                trainBeanList.add(new TrainBean(key, valueList));
                            }
                            final MyExpandableListAdapter adapter = new MyExpandableListAdapter(mContext, trainBeanList);
                            listView.setAdapter(adapter);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        } finally
                        {
                            if (trainBeanList.size() == 0)
                            {
                                ToastUtil.makeText(mContext, "无相关记录");
                            }
                        }
                    }
                    else
                    {
                        ErrorCode.doCode(this, mTrainListResponse.getResultCode(), mTrainListResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
        }

    }

    @Override
    public void onBackPressed()
    {
        if (topView.getVisibility() == View.VISIBLE)
        {
            super.onBackPressed();
        }
        else
        {
            findViewById(R.id.search_view).setVisibility(View.GONE);
            topView.setVisibility(View.VISIBLE);
            UserServiceImpl.instance().trainList(TrainListResponse.class.getName());
        }
    }

    @Override
    public void onClick(View v)
    {
    }
}
