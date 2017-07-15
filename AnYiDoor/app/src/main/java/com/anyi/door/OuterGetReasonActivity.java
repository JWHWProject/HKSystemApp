package com.anyi.door;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.YZMResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;


/**
 * 外来人员事由
 */
public class OuterGetReasonActivity extends BaseActivity implements View.OnClickListener
{


    @Bind(R.id.app_login_name_et)
    EditText appLoginNameEt;

    @Bind(R.id.app_login_bn)
    Button appLoginBn;

    private String reasonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_get_reason);
        ButterKnife.bind(this);
        reasonStr = getIntent().getStringExtra(IntentCode.GIVE_OUTER_CARD_REASON);
        initAll();
    }


    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("事由");
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
        appLoginNameEt.setText(reasonStr);

    }

    @Override
    public void initEvent()
    {
        appLoginBn.setOnClickListener(this);
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
                comfirmExit();
            }
            if (NotiTag.TAG_COMFIRM_CLOSE.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
               finish();
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(YZMResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    YZMResponse mYZMResponse = GsonHelper.toType(result, YZMResponse.class);
                    if (Constants.SUCESS_CODE.equals(mYZMResponse.getResultCode()))
                    {
                        //获取验证码成功后，跳转到注册页面
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mYZMResponse.getResultCode(), mYZMResponse.getDesc());
                    }
                }
                else
                {
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
            case R.id.app_login_bn:
                if (GeneralUtils.isNotNullOrZeroLenght(appLoginNameEt.getText().toString())&& appLoginNameEt.getText().toString().length()>=30){
                    Intent data = new Intent();
                    data.putExtra("reason",appLoginNameEt.getText().toString());
                    setResult(1,data);
                    finish();
                }else {
                    ToastUtil.makeText(mContext,"请输入事由，不少于30字");
                }
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        comfirmExit();
    }

    private void comfirmExit()
    {
        DialogUtil.showNoTipTwoBnttonDialog(mContext,"是否放弃编辑","确定","取消",NotiTag.TAG_COMFIRM_CLOSE,"");
    }
}
