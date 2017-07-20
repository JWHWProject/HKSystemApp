package com.anyi.door;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.anyi.door.utils.card.CardManager;
import com.anyi.door.utils.card.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.ReturnCardResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
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
 * 还卡
 */
public class GiveBackCardActivity extends BaseActivity  {


    @Bind(R.id.app_login_name_et)
    EditText appLoginNameEt;

    @Bind(R.id.app_back_bn)
    Button appBackBn;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_card);
        ButterKnife.bind(this);
        initAll();
        // 获取nfc适配器，判断设备是否支持NFC功能
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            return;
        } else if (!nfcAdapter.isEnabled()) {
            return;
        }
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        ndef.addCategory("*/*");
        mFilters = new IntentFilter[]{ndef};// 过滤器
        mTechLists = new String[][]{
                new String[]{MifareClassic.class.getName()},
                new String[]{NfcA.class.getName()}};// 允许扫描的标签类型
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter == null) {
            return;
        } else if (!nfcAdapter.isEnabled()) {
            return;
        }
        nfcAdapter.enableForegroundDispatch(GiveBackCardActivity.this, pendingIntent, mFilters,
                mTechLists);
        if (isFirst) {
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
                String result = processIntent(getIntent());
                String str= Util.hex2Decimal(result);
                appLoginNameEt.setText(str+"");
            }
            isFirst = false;
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            String result = processIntent(intent);
            String str=Util.hex2Decimal(result);
            appLoginNameEt.setText(str+"");
        }
    }
    /**
     * 获取tab标签中的内容
     *
     * @param intent
     * @return
     */
    @SuppressLint("NewApi")
    private String processIntent(Intent intent) {
        Intent intent1=intent;
        String intentActionStr=intent1.getAction();
        String strId="";
        Log.e("sub","intentActionStr="+intentActionStr);
//        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intentActionStr)
//                ||NfcAdapter.ACTION_TECH_DISCOVERED.equals(intentActionStr)
//                ||NfcAdapter.ACTION_TAG_DISCOVERED.equals(intentActionStr)) {
        byte[] bytesId = intent1.getByteArrayExtra(NfcAdapter.EXTRA_ID);
//            Tag tag = intent1.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//            byte[] bytesId = tag.getId();
        strId= Util.toHexString(bytesId,0,bytesId.length);
//        }
        if(strId!=null&&!strId.equals("")){
            return strId;
        }
        Parcelable[] rawmsgs = intent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawmsgs != null && rawmsgs.length > 0) {
            NdefMessage msg = (NdefMessage) rawmsgs[0];
            NdefRecord[] records = msg.getRecords();
            String resultStr = new String(records[0].getPayload());
            return resultStr;
        } else {
            final Parcelable p = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String str = (p != null) ? CardManager.load(p, getResources()) : null;
            return str;
        }
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
                        DialogUtil.showDialogOneButton(mContext,"还卡成功","确定",NotiTag.TAG_CLOSE_ACTIVITY);
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
