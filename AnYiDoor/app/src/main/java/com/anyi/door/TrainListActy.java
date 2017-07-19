package com.anyi.door;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.StartTestResponse;
import cn.nj.www.my_module.bean.index.StartTrainResponse;
import cn.nj.www.my_module.bean.index.TrainBean;
import cn.nj.www.my_module.bean.index.TrainContentResponse;
import cn.nj.www.my_module.bean.index.TrainListResponse;
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

/**
 * train list
 */
public class TrainListActy extends BaseActivity implements View.OnClickListener
{

    public String tagStr = "";

    private ExpandableListView listView;

    private List<TrainBean> trainBeanList = new ArrayList<>();

    private String chooseID = "";

    private String fromTest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);
        if (GeneralUtils.isNotNullOrZeroLenght(getIntent().getStringExtra(IntentCode.TEST_INTENT))){
            fromTest = getIntent().getStringExtra(IntentCode.TEST_INTENT);
        }
        initAll();
    }


    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("培训");
        headView.setLeftImage(R.mipmap.app_title_back);
        if (fromTest.equals("1")){
            headView.setHiddenRight();
        }else {
            headView.setRightText("搜索");
        }
    }


    @Override
    public void initView()
    {
        initTitle();
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
                chooseID = trainBeanList.get(groupPosition).getTrainBeanDetailList().get(childPosition).getId();
                if (fromTest.equals("")){
                    DialogUtil.showNoTipTwoBnttonDialog(mContext, "确定开始培训", "取消", "确定", NotiTag.TAG_DLG_CANCEL, NotiTag.TAG_DLG_OK);
                }else if (fromTest.equals("1")){
                    DialogUtil.showNoTipTwoBnttonDialog(mContext, "确定开始考核", "取消", "确定", NotiTag.TAG_DLG_CANCEL, NotiTag.TAG_DLG_OK);
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
                startActivity(new Intent(mContext, SearchTrainListActy.class));
            }
            if (NotiTag.TAG_DLG_OK.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                //调用开始培训的接口
                NetLoadingDialog.getInstance().loading(mContext);
                if (fromTest.equals("")){
                    UserServiceImpl.instance().startTrain(chooseID, "", StartTrainResponse.class.getName());
                }else if (fromTest.equals("1")){
                    UserServiceImpl.instance().startOnlineTest(chooseID,"",  StartTestResponse.class.getName());
                }
            }
        }
        else if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
        }
        String tag = ((NetResponseEvent) event).getTag();
        String result = ((NetResponseEvent) event).getResult();
        if (tag.equals(StartTestResponse.class.getName()))
        {
            StartTestResponse mStartTestResponse = GsonHelper.toType(result, StartTestResponse.class);
            if (GeneralUtils.isNotNullOrZeroLenght(result))
            {
                if (Constants.SUCESS_CODE.equals(mStartTestResponse.getResultCode()))
                {
                    Intent testIntent = new Intent(mContext,TestListActivity.class);
                    testIntent.putExtra(IntentCode.EXAM_ID,mStartTestResponse.getExamID());
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
                    UserServiceImpl.instance().trainContent(chooseID, TrainContentResponse.class.getName());
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


    @Override
    public void onClick(View v)
    {
    }
}
