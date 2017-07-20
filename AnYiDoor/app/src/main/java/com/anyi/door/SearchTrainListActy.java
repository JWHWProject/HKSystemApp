package com.anyi.door;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

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
import cn.nj.www.my_module.bean.index.SearchTrainListResponse;
import cn.nj.www.my_module.bean.index.StartTrainResponse;
import cn.nj.www.my_module.bean.index.TrainBean;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;

/**
 * train list
 */
public class SearchTrainListActy extends BaseActivity implements View.OnClickListener
{
    public String tagStr = "";

    @Bind(R.id.finish_iv)
    ImageView finishIv;

    @Bind(R.id.iv_search_clear)
    ImageView ivSearchClear;

    @Bind(R.id.tv_search)
    TextView tvSearch;

    @Bind(R.id.et_search)
    EditText etSearch;

    private ExpandableListView listView;

    private List<TrainBean> trainBeanList = new ArrayList<>();

    private MyExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_train_list);
        ButterKnife.bind(this);
        initAll();
    }


    @Override
    public void initView()
    {
        listView = (ExpandableListView) findViewById(R.id.list);
        listView.setGroupIndicator(null);
        finishIv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
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
                            SearchTrainListResponse.class.getName());
                }
                else
                {
                    ToastUtil.makeText(mContext, "请输入搜索内容");
                }
            }
        });
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

                DialogUtil.showNoTipTwoBnttonDialog(mContext, "确定开始培训", "取消", "确定", NotiTag.TAG_DLG_CANCEL, NotiTag.TAG_DLG_OK);
                tagStr = groupPosition + "  " + childPosition;
                return false;
            }
        });
    }

    @Override
    public void initViewData()
    {

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
            if (NotiTag.TAG_DLG_OK.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                ToastUtil.makeText(mContext, tagStr);
//                UserServiceImpl.instance().startTrain(tagStr, "", StartTrainResponse.class.getName());
            }
        }
        else if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
        }
        String tag = ((NetResponseEvent) event).getTag();
        String result = ((NetResponseEvent) event).getResult();
        if (tag.equals(SearchTrainListResponse.class.getName()))
        {
            SearchTrainListResponse mTrainListResponse = GsonHelper.toType(result, SearchTrainListResponse.class);
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
                        adapter = new MyExpandableListAdapter(mContext, trainBeanList);
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
