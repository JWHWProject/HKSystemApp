package com.anyi.door;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.LoginResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.StringEncrypt;
import cn.nj.www.my_module.tools.ToastUtil;

public class LoginActy extends BaseActivity implements View.OnClickListener {

    private Button commitBn;
    private EditText nameET, psdET;
    private TextView forgetTv, registTv;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(mContext);
        setContentView(R.layout.activity_login_acty);
        initAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("登录");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    @Override
    public void initView() {
        initTitle();
        nameET = (EditText) findViewById(R.id.app_login_name_et);
        psdET = (EditText) findViewById(R.id.app_login_psd_et);
        commitBn = (Button) findViewById(R.id.app_login_bn);
        registTv = (TextView) findViewById(R.id.app_register_tv);
        forgetTv = (TextView) findViewById(R.id.app_forget_tv);

    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {
        commitBn.setOnClickListener(this);
        registTv.setOnClickListener(this);
        forgetTv.setOnClickListener(this);
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
        }
        if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(LoginResponse.class.getName())) {
                LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(loginResponse.getResultCode())) {
//                        Global.saveLoginUserData(mContext, loginResponse.getUser());
                        Global.savePassword(psdET.getText().toString());
                        Global.saveLoginName(nameET.getText().toString());
                        ToastUtil.makeText(mContext, "登录成功");
                        startActivity(new Intent(mContext,MainActivity.class));
                        finish();
                    } else {
//                        ErrorCode.doCode(this, loginResponse.getResultCode(), loginResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_login_bn:
                startActivity(new Intent(mContext,MainActivity.class));
                if (GeneralUtils.isNotNullOrZeroLenght(psdET.getText().toString())) {
                    if (GeneralUtils.isNotNullOrZeroLenght(nameET.getText().toString())) {
                        NetLoadingDialog.getInstance().loading(mContext);
                        UserServiceImpl.instance().login(nameET.getText().toString().trim(), StringEncrypt.Encrypt(psdET.getText().toString().trim()),
                                LoginResponse.class.getName());

                    } else {
                        ToastUtil.makeText(mContext, "请输入用户名");
                    }
                } else {
                    ToastUtil.makeText(mContext, "请输入密码");
                }
                break;
            case R.id.app_forget_tv:
                startActivity(new Intent(mContext, FindPasswordCodeActy.class));
                break;
            case R.id.app_register_tv:
                break;
        }
    }


}
