package com.anyi.door;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.YZMResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;


/**
 * 发卡
 */
public class GiveCardActivity extends BaseActivity implements View.OnClickListener
{


    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @Bind(R.id.tv_sex)
    TextView tvSex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_card);
        ButterKnife.bind(this);
        initAll();
    }


    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("发卡");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }


    @Override
    public void initView()
    {
        initTitle();
        tvSex.setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));

    }

    @Override
    public void initViewData()
    {

    }

    @Override
    public void initEvent()
    {

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()

                                            {
                                                @Override
                                                public void onTabSelected(TabLayout.Tab tab)
                                                {
                                                    switch (tab.getPosition())
                                                    {
                                                        case 0:
                                                            ToastUtil.makeText(mContext, "企业人员");
                                                            break;
                                                        case 1:
                                                            ToastUtil.makeText(mContext, "外来人员");
                                                            break;
                                                    }
                                                }

                                                @Override
                                                public void onTabUnselected(TabLayout.Tab tab)
                                                {

                                                }

                                                @Override
                                                public void onTabReselected(TabLayout.Tab tab)
                                                {

                                                }
                                            }

        );
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
    private String[] resonArr = new String[]{"男", "女"};

    private String refundReson = resonArr[0];

    private int whitchIndex = 0;

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_sex:
                AlertDialog Builder = new AlertDialog.Builder(mContext).setTitle("请选择")
                        .setSingleChoiceItems(
                                resonArr, whitchIndex,
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        refundReson = resonArr[which];
                                        tvSex.setText(refundReson);
                                        whitchIndex = which;
                                        dialog.dismiss();
                                    }

                                }).show();
                break;
        }
    }


}
