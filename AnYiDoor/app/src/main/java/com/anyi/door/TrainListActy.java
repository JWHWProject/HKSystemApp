package com.anyi.door;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyi.door.utils.card.CardManager;
import com.anyi.door.utils.card.Util;
import com.anyi.door.video.base.TinyWindowPlayActivity;
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
import cn.nj.www.my_module.bean.index.BaseTrainListResponse;
import cn.nj.www.my_module.bean.index.OuterPeopleResponse;
import cn.nj.www.my_module.bean.index.StartTestResponse;
import cn.nj.www.my_module.bean.index.StartTrainResponse;
import cn.nj.www.my_module.bean.index.TrainBean;
import cn.nj.www.my_module.bean.index.TrainContentResponse;
import cn.nj.www.my_module.bean.index.TrainListResponse;
import cn.nj.www.my_module.bean.index.TrainVideoResponse;
import cn.nj.www.my_module.bean.index.UserListByNameResponse;
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
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;

import static cn.nj.www.my_module.constant.IntentCode.TRAIN_ID;

/**
 * train list
 */
public class TrainListActy extends BaseActivity implements View.OnClickListener {

    public String tagStr = "";

    private ExpandableListView listView;

    private List<TrainBean> trainBeanList = new ArrayList<>();

    private List<TrainBean> trainBeanListCopy = new ArrayList<>();

    private String trainID = "";

    private String fromTest = "";

    private String recordID = "";

    private String fileType = "";

    @Bind(R.id.iv_search_clear)
    ImageView ivSearchClear;

    @Bind(R.id.tv_search)
    TextView tvSearch;

    @Bind(R.id.et_search)
    EditText etSearch;

    private View topView;

    private String outId = "";

    private String selectedTestName;


    private NfcAdapter nfcAdapter;

    private PendingIntent pendingIntent;

    private IntentFilter[] mFilters;

    private String[][] mTechLists;

    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);
        ButterKnife.bind(this);
        if (GeneralUtils.isNotNullOrZeroLenght(getIntent().getStringExtra(IntentCode.TEST_INTENT))) {
            fromTest = getIntent().getStringExtra(IntentCode.TEST_INTENT);
        }
        if (GeneralUtils.isNotNullOrZeroLenght(getIntent().getStringExtra(TRAIN_ID))) {
            trainID = getIntent().getStringExtra(IntentCode.TRAIN_ID);
            outId = getIntent().getStringExtra(IntentCode.CARD_NUM);
        }


        initAll();
        // 获取nfc适配器，判断设备是否支持NFC功能
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            return;
        }
        else if (!nfcAdapter.isEnabled()) {
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
        }
        else if (!nfcAdapter.isEnabled()) {
            return;
        }
        nfcAdapter.enableForegroundDispatch(TrainListActy.this, pendingIntent, mFilters,
                mTechLists);
        if (isFirst) {
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
                String result = processIntent(getIntent());
                String str = Util.hex2Decimal(result);
                if (dialog != null) {
                    EditText etCard = (EditText) dialog.findViewById(R.id.et_card);
                    etCard.setText(str + "");
                }
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
            String str = Util.hex2Decimal(result);
            if (dialog != null) {
                EditText etCard = (EditText) dialog.findViewById(R.id.et_card);
                etCard.setText(str + "");
            }
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
        Intent intent1 = intent;
        String intentActionStr = intent1.getAction();
        String strId = "";
        Log.e("sub", "intentActionStr=" + intentActionStr);
//        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intentActionStr)
//                ||NfcAdapter.ACTION_TECH_DISCOVERED.equals(intentActionStr)
//                ||NfcAdapter.ACTION_TAG_DISCOVERED.equals(intentActionStr)) {
        byte[] bytesId = intent1.getByteArrayExtra(NfcAdapter.EXTRA_ID);
//            Tag tag = intent1.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//            byte[] bytesId = tag.getId();
        strId = Util.toHexString(bytesId, 0, bytesId.length);
//        }
        if (strId != null && !strId.equals("")) {
            return strId;
        }
        Parcelable[] rawmsgs = intent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawmsgs != null && rawmsgs.length > 0) {
            NdefMessage msg = (NdefMessage) rawmsgs[0];
            NdefRecord[] records = msg.getRecords();
            String resultStr = new String(records[0].getPayload());
            return resultStr;
        }
        else {
            final Parcelable p = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String str = (p != null) ? CardManager.load(p, getResources()) : null;
            return str;
        }
    }

    private void initTitle() {
        topView = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) topView);
        headView.setTitleText("培训");
        headView.setLeftImage(R.mipmap.app_title_back);
        if (fromTest.equals("1")) {
            headView.setRightText("搜索");
            headView.setTitleText("考核列表");
        }
        else {
            headView.setTitleText("培训");
            headView.setRightText("搜索");
            //从发卡成功界面进入的
            if (GeneralUtils.isNotNullOrZeroLenght(trainID) && GeneralUtils.isNotNullOrZeroLenght(outId)) {
                DialogUtil.showNoTipTwoBnttonDialog(mContext, "确定开始培训", "取消", "确定", NotiTag.TAG_DLG_CANCEL, NotiTag.TAG_DLG_OK);
            }
        }
    }


    @Override
    public void initView() {
        initTitle();
        findViewById(R.id.finish_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.search_view).setVisibility(View.GONE);
                topView.setVisibility(View.VISIBLE);
                trainList();
            }
        });
        ivSearchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearch.setText("");
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GeneralUtils.isNotNullOrZeroLenght(etSearch.getText().toString())) {
                    NetLoadingDialog.getInstance().loading(mContext);
                    UserServiceImpl.instance().trainList(etSearch.getText().toString(),
                            TrainListResponse.class.getName());
                }
                else {
                    ToastUtil.makeText(mContext, "请输入搜索内容");
                }
            }
        });

        listView = (ExpandableListView) findViewById(R.id.list);
        listView.setGroupIndicator(null);

        //只展开一个
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < 4; i++) {
                    if (groupPosition != i) {
                        listView.collapseGroup(i);
                    }
                }

            }

        });

        //点击跳转
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                trainID = trainBeanList.get(groupPosition).getTrainBeanDetailList().get(childPosition).getId();
                fileType = trainBeanList.get(groupPosition).getTrainBeanDetailList().get(childPosition).getFileType();
                if (fromTest.equals("")) {
                    dialog = DialogUtil.startTrainDialog(mContext, NotiTag.TAG_START_TRAIN_DIALOG);

                }
                else if (fromTest.equals("1")) {
                    selectedTestName = trainBeanList.get(groupPosition).getTrainBeanDetailList().get(childPosition).getTrainingName();
                    dialog = DialogUtil.startTestDialog(mContext, NotiTag.TAG_START_TEST_DIALOG);
                }
                return false;
            }
        });
    }

    Dialog dialog;

    private void trainList() {
        if (GeneralUtils.isNullOrZeroLenght(MainActivity.trainListDateResult)) {
            UserServiceImpl.instance().trainList(BaseTrainListResponse.class.getName());
        }
        else {
            trainBeanList.clear();
            try {
                JSONObject jsonObject = new JSONObject(MainActivity.trainListDateResult);
                Map<String, List<TrainBean.TrainBeanDetail>> map = new Gson().fromJson(jsonObject.getString("typeMap"), new TypeToken<Map<String, List<TrainBean.TrainBeanDetail>>>() {
                }.getType());
                Iterator entries = map.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    String key = (String) entry.getKey();
                    List<TrainBean.TrainBeanDetail> valueList = (List<TrainBean.TrainBeanDetail>) entry.getValue();
                    trainBeanList.add(new TrainBean(key, valueList));
                }
                final MyExpandableListAdapter adapter = new MyExpandableListAdapter(mContext, trainBeanList);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initViewData() {
        trainList();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event) {

    }

    private String[] nameArr;

    private int nameIndex = 0;

    @Override
    public void onEventMainThread(BaseResponse event) {
        if (event instanceof NoticeEvent) {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                finish();
            }
            if (NotiTag.TAG_DO_RIGHT.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                topView.setVisibility(View.GONE);
                findViewById(R.id.search_view).setVisibility(View.VISIBLE);

            }
            if ("BTN_SEL_NAME".equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                List<OuterPeopleResponse.OutsidersListBean> outpeoples = GeneralUtils.getOuterPeopleList();
                final EditText editText = ((NoticeEvent) event).getTempTV();
                try {
                    nameArr = new String[outpeoples.size()];
                    for (int i = 0; i < outpeoples.size(); i++) {
                        nameArr[i] = outpeoples.get(i).getUserName();
                    }
                    new AlertDialog.Builder(mContext).setTitle("请选择")
                            .setSingleChoiceItems(
                                    nameArr, nameIndex,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            editText.setText(nameArr[which]);
                                            dialog.dismiss();
                                        }

                                    }).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (NotiTag.TAG_START_TRAIN_DIALOG.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                NetLoadingDialog.getInstance().loading(mContext);
                int type = ((NoticeEvent) event).getPosition();//0内部人员 1外来人员
                String card = ((NoticeEvent) event).getUrl1();
                String name = ((NoticeEvent) event).getUrl2();
                NetLoadingDialog.getInstance().loading(mContext);
                if (type == 1) {//外来人员
                    UserServiceImpl.instance().startTrain(trainID, card, name, StartTrainResponse.class.getName());
                }
                else {
                    UserServiceImpl.instance().getUserListByName(name, UserListByNameResponse.class.getName());
                }

            }
            if (NotiTag.TAG_START_TEST_DIALOG.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                NetLoadingDialog.getInstance().loading(mContext);
                int type = ((NoticeEvent) event).getPosition();//0内部人员 1外来人员
                String card = ((NoticeEvent) event).getUrl1();
                String name = ((NoticeEvent) event).getUrl2();
                NetLoadingDialog.getInstance().loading(mContext);
                if (type == 1) {
                    UserServiceImpl.instance().startOnlineTest(trainID, card, name, StartTestResponse.class.getName());
                }
                else {
                    UserServiceImpl.instance().getUserListByName(name, UserListByNameResponse.class.getName());
                }
            }
            if (NotiTag.TAG_DLG_OK.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                //调用开始培训的接口
                NetLoadingDialog.getInstance().loading(mContext);
                if (fromTest.equals("")) {
                    UserServiceImpl.instance().startTrain(trainID, outId, "", StartTrainResponse.class.getName());
                }
                else if (fromTest.equals("1")) {
                    UserServiceImpl.instance().startOnlineTest(trainID, outId, "", StartTestResponse.class.getName());
                }
            }
        }
        else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(StartTestResponse.class.getName())) {
                StartTestResponse mStartTestResponse = GsonHelper.toType(result, StartTestResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mStartTestResponse.getResultCode())) {
                        Intent testIntent = new Intent(mContext, TestListActivity.class);
                        testIntent.putExtra(IntentCode.EXAM_ID, mStartTestResponse.getExamID());
                        testIntent.putExtra(IntentCode.TRAIN_ID, trainID);
                        testIntent.putExtra(IntentCode.EXAM_FINISH_ID, trainID);
                        testIntent.putExtra(IntentCode.EXAM_NAME, selectedTestName);
                        startActivity(testIntent);
                    }
                    else {
                        ErrorCode.doCode(this, mStartTestResponse.getResultCode(), mStartTestResponse.getDesc());
                    }
                }
                else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(StartTrainResponse.class.getName())) {
                StartTrainResponse mStartTrainResponse = GsonHelper.toType(result, StartTrainResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mStartTrainResponse.getResultCode())) {
                        //因为视频页面没有集成EventBus，就在该页面获取到数据后再传过去
                        recordID = mStartTrainResponse.getRecordID();
                        if (GeneralUtils.isNotNullOrZeroLenght(recordID)) {
                            NetLoadingDialog.getInstance().loading(mContext);
                            UserServiceImpl.instance().trainContent(trainID, TrainContentResponse.class.getName());
                        }
                        else {
                            ToastUtil.makeText(mContext, "无培训记录的ID");
                        }

                    }
                    else {
                        ErrorCode.doCode(this, mStartTrainResponse.getResultCode(), mStartTrainResponse.getDesc());
                    }
                }
                else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(TrainContentResponse.class.getName())) {
                TrainContentResponse mTrainContentResponse = GsonHelper.toType(result, TrainContentResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mTrainContentResponse.getResultCode())) {

                        if (mTrainContentResponse.getTraining().getFileType() == 1) {//图片
                            Intent intent = new Intent(mContext, TrainPicActivity.class);
                            intent.putExtra(IntentCode.CHOOSE_ID, result);
                            intent.putExtra(IntentCode.RECORD_ID, recordID);
                            startActivity(new Intent(intent));
                        }
                        else if (mTrainContentResponse.getTraining().getFileType() == 2) {
                            Intent intent = new Intent(mContext, TinyWindowPlayActivity.class);
                            intent.putExtra(IntentCode.CHOOSE_ID, result);
                            intent.putExtra(IntentCode.TRAIN_ID, recordID);
                            startActivity(new Intent(intent));
                        }
                        else {
                            Intent intent = new Intent(mContext, TrainH5Activity.class);
                            intent.putExtra(IntentCode.CHOOSE_ID, result);
                            intent.putExtra(IntentCode.TRAIN_ID, recordID);
                            intent.putExtra(IntentCode.COMMON_WEB_VIEW_URL, mTrainContentResponse.getFileUrl());
                            startActivity(new Intent(intent));
                        }

                    }
                    else {
                        ErrorCode.doCode(this, mTrainContentResponse.getResultCode(), mTrainContentResponse.getDesc());
                    }
                }
                else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(TrainVideoResponse.class.getName())) {
                TrainVideoResponse mTrainContentResponse = GsonHelper.toType(result, TrainVideoResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mTrainContentResponse.getResultCode())) {

                    }
                    else {
                        ErrorCode.doCode(this, mTrainContentResponse.getResultCode(), mTrainContentResponse.getDesc());
                    }
                }
                else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(TrainListResponse.class.getName())) {
                TrainListResponse mTrainListResponse = GsonHelper.toType(result, TrainListResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mTrainListResponse.getResultCode())) {
                        trainBeanList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            Map<String, List<TrainBean.TrainBeanDetail>> map = new Gson().fromJson(jsonObject.getString("typeMap"), new TypeToken<Map<String, List<TrainBean.TrainBeanDetail>>>() {
                            }.getType());
                            Iterator entries = map.entrySet().iterator();
                            while (entries.hasNext()) {
                                Map.Entry entry = (Map.Entry) entries.next();
                                String key = (String) entry.getKey();
                                List<TrainBean.TrainBeanDetail> valueList = (List<TrainBean.TrainBeanDetail>) entry.getValue();
                                trainBeanList.add(new TrainBean(key, valueList));
                            }
                            final MyExpandableListAdapter adapter = new MyExpandableListAdapter(mContext, trainBeanList);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            if (trainBeanList.size() == 0) {
                                ToastUtil.makeText(mContext, "无相关记录");
                            }
                        }
                    }
                    else {
                        ErrorCode.doCode(this, mTrainListResponse.getResultCode(), mTrainListResponse.getDesc());
                    }
                }
                else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(BaseTrainListResponse.class.getName())) {
                BaseTrainListResponse mTrainListResponse = GsonHelper.toType(result, BaseTrainListResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mTrainListResponse.getResultCode())) {
                        trainBeanList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            Map<String, List<TrainBean.TrainBeanDetail>> map = new Gson().fromJson(jsonObject.getString("typeMap"), new TypeToken<Map<String, List<TrainBean.TrainBeanDetail>>>() {
                            }.getType());
                            Iterator entries = map.entrySet().iterator();
                            while (entries.hasNext()) {
                                Map.Entry entry = (Map.Entry) entries.next();
                                String key = (String) entry.getKey();
                                List<TrainBean.TrainBeanDetail> valueList = (List<TrainBean.TrainBeanDetail>) entry.getValue();
                                trainBeanList.add(new TrainBean(key, valueList));
                            }
                            final MyExpandableListAdapter adapter = new MyExpandableListAdapter(mContext, trainBeanList);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            if (trainBeanList.size() == 0) {
                                ToastUtil.makeText(mContext, "无相关记录");
                            }
                        }
                    }
                    else {
                        ErrorCode.doCode(this, mTrainListResponse.getResultCode(), mTrainListResponse.getDesc());
                    }
                }
                else {
                    ToastUtil.showError(this);
                }
            }
            if (tag.equals(UserListByNameResponse.class.getName())) {
                final UserListByNameResponse userListByNameResponse = GsonHelper.toType(result, UserListByNameResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(userListByNameResponse.getResultCode())) {
                        if (userListByNameResponse.getUserList() != null && userListByNameResponse.getUserList().size() > 0) {
                            if (userListByNameResponse.getUserList().size() == 1) {
                                if (fromTest.equals("1")) {
                                    UserServiceImpl.instance().startOnlineTest(trainID, "", userListByNameResponse.getUserList().get(0).getUserID(), StartTestResponse.class.getName());
                                }else {
                                    UserServiceImpl.instance().startTrain(trainID, "", userListByNameResponse.getUserList().get(0).getUserID(), StartTrainResponse.class.getName());
                                }
                            }
                            else if (userListByNameResponse.getUserList().size() > 1) {
                                try {
                                    nameArr = new String[userListByNameResponse.getUserList().size()];
                                    for (int i = 0; i < userListByNameResponse.getUserList().size(); i++) {
                                        String jobNum ="";
                                        if (GeneralUtils.isNotNullOrZeroLenght(userListByNameResponse.getUserList().get(i).getJobNumber())){
                                            jobNum = "("+userListByNameResponse.getUserList().get(i).getJobNumber()+")";
                                        }
                                        nameArr[i] = userListByNameResponse.getUserList().get(i).getNickName()+jobNum;
                                    }
                                    new AlertDialog.Builder(mContext).setTitle("请选择")
                                            .setSingleChoiceItems(
                                                    nameArr, nameIndex,
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            //这里判断是培训或者是考核
                                                            if (fromTest.equals("1")) {
                                                                UserServiceImpl.instance().startOnlineTest(trainID, "", userListByNameResponse.getUserList().get(nameIndex).getUserID(), StartTestResponse.class.getName());
                                                            }else {
                                                                UserServiceImpl.instance().startTrain(trainID, "", userListByNameResponse.getUserList().get(nameIndex).getUserID(), StartTrainResponse.class.getName());
                                                            }

                                                            dialog.dismiss();
                                                        }

                                                    }).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                ToastUtil.makeText(mContext, "不存在该人员信息");
                            }
                        }
                        else {
                            ToastUtil.makeText(mContext, "不存在该人员信息");
                        }

                    }
                    else {
                        ErrorCode.doCode(this, userListByNameResponse.getResultCode(), userListByNameResponse.getDesc());
                    }
                }
                else {
                    ToastUtil.showError(this);
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (topView.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        }
        else {
            findViewById(R.id.search_view).setVisibility(View.GONE);
            topView.setVisibility(View.VISIBLE);
            trainList();
        }
    }

    @Override
    public void onClick(View v) {
    }
}
