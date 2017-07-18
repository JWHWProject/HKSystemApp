package com.anyi.door;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.StartTrainResponse;
import cn.nj.www.my_module.bean.index.TrainBean;
import cn.nj.www.my_module.bean.index.TrainListResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;

/**
 * train list
 */
public class TrainListCopyActy extends BaseActivity implements View.OnClickListener
{

    public String tagStr = "";
    private List<TrainBean> trainBeanList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_listn);
        initAll();
    }


    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("培训");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setRightText("搜索");
    }


    @Override
    public void initView()
    {
        initTitle();

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
                ToastUtil.makeText(mContext, tagStr);
                //调用开始培训的接口
                UserServiceImpl.instance().startTrain(tagStr, "", StartTrainResponse.class.getName());
            }
        }
        else if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(TrainListResponse.class.getName()))
            {
                TrainListResponse mTrainListResponse = GsonHelper.toType(result, TrainListResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mTrainListResponse.getResultCode()))
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(result);
                            Map<String, Object> map = new Gson().fromJson(jsonObject.getString("typeMap"), new TypeToken<HashMap<String, Object>>()
                            {
                            }.getType());
                            Iterator entries = map.entrySet().iterator();
                            while (entries.hasNext())
                            {
                                Map.Entry entry = (Map.Entry) entries.next();
                                String key = (String) entry.getKey();
                                List<TrainBean.TrainBeanDetail> list = (List<TrainBean.TrainBeanDetail>) entry.getValue();

                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
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

    public Map jsonToObject(JSONObject jsonObj) throws Exception
    {
        Iterator<String> nameItr = jsonObj.keys();
        String name;
        Map<String, String> outMap = new HashMap<String, String>();
        while (nameItr.hasNext())
        {
            name = nameItr.next();
            outMap.put(name, jsonObj.getString(name));
        }
        return outMap;
    }

    @Override
    public void onClick(View v)
    {
    }
}
