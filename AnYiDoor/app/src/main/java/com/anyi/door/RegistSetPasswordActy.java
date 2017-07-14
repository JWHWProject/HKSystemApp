package com.anyi.door;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;

/**
 * 设置密码
 */
public class RegistSetPasswordActy extends BaseActivity implements View.OnClickListener {


    private EditText psdAgainEt, psdEt;
    private Button comfirmBn;
    private String phoneNum;
    private EditText etCode;

    private boolean hasSendMsg = false;

    private String yzmCode = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_set_password);
        phoneNum = getIntent().getStringExtra(IntentCode.REGISTER_PHONE);
        initAll();
    }


    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("手机快速注册");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }


    @Override
    public void initView() {
        initTitle();
        psdEt = (EditText) findViewById(R.id.app_regist_psd_et);
        psdAgainEt = (EditText) findViewById(R.id.app_regist_psd_again_et);
        comfirmBn = (Button) findViewById(R.id.app_register_bn);
        etCode = (EditText) findViewById(R.id.app_code_et);
        comfirmBn.setOnClickListener(this);
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (GeneralUtils.isNotNullOrZeroLenght(etCode.getText().toString())) {
                    comfirmBn.setEnabled(true);
                }
            }
        });

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
//            //检验短信验证码
//            if (tag.equals(CheckYZMResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
//                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
//                    CheckYZMResponse mCheck = GsonHelper.toType(result, CheckYZMResponse.class);
//                    if (Constants.SUCESS_CODE.equals(mCheck.getResultCode())) {
//                        //注册
//                        UserServiceImpl.instance().register( phoneNum, psdAgainEt.getText().toString().trim(), etCode.getText().toString().trim(), RegisterResponse.class.getName());
//                    } else {
//                        ErrorCode.doCode(mContext, mCheck.getResultCode(), mCheck.getDesc());
//                        NetLoadingDialog.getInstance().dismissDialog();
//                    }
//                } else {
//                    NetLoadingDialog.getInstance().dismissDialog();
//                    ToastUtil.showError(mContext);
//                }
//            } else if (tag.equals(RegisterResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
//                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
//                    RegisterResponse mRegisterResponse = GsonHelper.toType(result, RegisterResponse.class);
//                    if (Constants.SUCESS_CODE.equals(mRegisterResponse.getResultCode())) {
//                        Global.saveLoginUserData(mContext, mRegisterResponse.getUser());
//                        ToastUtil.makeText(mContext,"注册成功");
//                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_LOGIN_SUCCESS));
//                        finish();
//                    } else {
//                        ErrorCode.doCode(mContext, mRegisterResponse.getResultCode(), mRegisterResponse.getDesc());
//                    }
//                } else {
//                    ToastUtil.showError(mContext);
//                }
//            }

        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_register_bn:
                String psd = psdAgainEt.getText().toString().trim();
                if (GeneralUtils.isNullOrZeroLenght(etCode.getText().toString())) {
                    ToastUtil.makeText(mContext, "请输入验证码");
                } else if (psd.length() < 6) {
                    ToastUtil.makeText(mContext, "请输入大于六位数的密码！");
                    return;
                } else if (!psdEt.getText().toString().trim().equals(psdAgainEt.getText().toString().trim())) {
                    ToastUtil.makeText(mContext, "两次输入密码不一致，请重新输入");
                    return;
                } else {
                    NetLoadingDialog.getInstance().loading(mContext);
                    //验证短信验证码
//                    UserServiceImpl.instance().checkYZMCode(mContext, "1", phoneNum, etCode.getText().toString(), CheckYZMResponse.class.getName());
                    //注册
//                    UserServiceImpl.instance().register( phoneNum, psdAgainEt.getText().toString().trim(), etCode.getText().toString().trim(), RegisterResponse.class.getName());

                }
                break;
        }
    }
}
