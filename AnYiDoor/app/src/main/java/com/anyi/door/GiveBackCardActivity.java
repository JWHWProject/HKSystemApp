package com.anyi.door;

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
import cn.nj.www.my_module.bean.index.ReturnCardResponse;
import cn.nj.www.my_module.bean.index.YZMResponse;
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
 * 还卡
 */
public class GiveBackCardActivity extends BaseActivity  {


    @Bind(R.id.app_login_name_et)
    EditText appLoginNameEt;

    @Bind(R.id.app_back_bn)
    Button appBackBn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_card);
        ButterKnife.bind(this);
        initAll();
    }


    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("还卡");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }


    @Override
    public void initView() {
        initTitle();

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {
        appBackBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GeneralUtils.isNotNullOrZeroLenght(appLoginNameEt.getText().toString())){
                    UserServiceImpl.instance().returnCard(appLoginNameEt.getText().toString(), ReturnCardResponse.class.getName());
                }
            }
        });
    }

    @Override
    public void netResponse(BaseResponse event) {

    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            NetLoadingDialog.getInstance().dismissDialog();
            if (tag.equals(ReturnCardResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    ReturnCardResponse mReturnCardResponse = GsonHelper.toType(result, ReturnCardResponse.class);
                    if (Constants.SUCESS_CODE.equals(mReturnCardResponse.getResultCode())) {
                    }
                    else {
                        ErrorCode.doCode(mContext, mReturnCardResponse.getResultCode(), mReturnCardResponse.getDesc());
                    }
                }
                else {
                    ToastUtil.showError(mContext);
                }
            }
        }
    }




}
