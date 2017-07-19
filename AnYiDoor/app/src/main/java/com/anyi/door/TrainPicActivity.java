package com.anyi.door;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
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
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.ImageLoaderUtil;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;

/**
 * 完成培训视频
 */
public class TrainPicActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.bn_finish)
    Button bnFinish;

    @Bind(R.id.myListView)
    ListView myListView;

    private TrainContentResponse mTrainContentResponse;

    private String trainId;

    private CommonAdapter<TrainContentResponse.ImageBean> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acty_train_pic);
        ButterKnife.bind(this);
        bnFinish.setOnClickListener(this);
        mTrainContentResponse = GsonHelper.toType(getIntent().getStringExtra(IntentCode.CHOOSE_ID), TrainContentResponse.class);
        trainId = getIntent().getStringExtra(IntentCode.TRAIN_ID);
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

        myListView.setAdapter(adapter = new CommonAdapter<TrainContentResponse.ImageBean>(mContext, mTrainContentResponse.getImageBeans(), R.layout.item_pic)
        {
            @Override
            public void convert(ViewHolder helper, TrainContentResponse.ImageBean item)
            {
                ImageView iv = helper.getView(R.id.ivImg);
                ImageLoaderUtil.getInstance().initImage(mContext,item.getUrl(),iv,"");
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
                        DialogUtil.showDialogOneButton(mContext, "完成培训", "我知道了", NotiTag.TAG_CLOSE_ACTIVITY);
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
        }

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bn_finish:
                //这边需要添加图片
                UserServiceImpl.instance().finishTrain(trainId, null, FinishTrainResponse.class.getName());
                break;
        }
    }
}
