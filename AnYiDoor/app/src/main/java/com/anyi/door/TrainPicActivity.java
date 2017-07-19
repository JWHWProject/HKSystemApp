package com.anyi.door;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.FinishTrainResponse;
import cn.nj.www.my_module.bean.index.TrainContentResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
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
 * 完成培训视频
 */
public class TrainPicActivity extends BaseActivity implements View.OnClickListener
{

    private String trainId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acty_train_pic);
        initAll();
    }


    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }


    @Override
    public void initView()
    {
        initTitle();

    }

    @Override
    public void initViewData()
    {
        UserServiceImpl.instance().trainContent(getIntent().getStringExtra(IntentCode.CHOOSE_ID), TrainContentResponse.class.getName());
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
        }
        else if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
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
        }

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bn_finish:
                UserServiceImpl.instance().finishTrain(trainId, null, FinishTrainResponse.class.getName());
                break;
        }
    }
}
