package com.anyi.door;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anyi.door.utils.Config;
import com.anyi.door.utils.DbTOPxUtils;
import com.anyi.door.utils.MyGridView;
import com.anyi.door.utils.card.CardManager;
import com.anyi.door.utils.card.Util;
import com.anyi.door.utils.signature.HandWriteActivity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.GiveInnerCardResponse;
import cn.nj.www.my_module.bean.index.GiveOutterCardResponse;
import cn.nj.www.my_module.bean.index.LoginResponse;
import cn.nj.www.my_module.bean.index.OuterTypeResponse;
import cn.nj.www.my_module.bean.index.UploadFileResponse;
import cn.nj.www.my_module.bean.index.UserListResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.DisplayUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.ImageLoaderUtil;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.AmountView;
import cn.nj.www.my_module.view.imagepicker.PhotoPreviewActivity;
import cn.nj.www.my_module.view.imagepicker.PhotoSelectorActivity;
import cn.nj.www.my_module.view.imagepicker.model.PhotoModel;
import cn.nj.www.my_module.view.imagepicker.util.CommonUtils;
import cn.nj.www.my_module.view.imagepicker.util.FileUtils;

import static com.anyi.door.R.id.add_IB;


/**
 * 发卡
 */
public class GiveCardActivity extends BaseActivity implements View.OnClickListener
{


    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @Bind(R.id.tv_sex)
    TextView tvSex;

    @Bind(R.id.et_card_number)
    EditText etCardNumber;

    @Bind(R.id.et_name)
    EditText etName;

    @Bind(R.id.tv_user_type)
    TextView tvUserType;

    @Bind(R.id.tv_department_left)
    TextView tvDepartmentLeft;

    @Bind(R.id.tv_department)
    TextView tvDepartment;

    @Bind(R.id.et_number)
    EditText etNumber;

    @Bind(R.id.ll_inner)
    LinearLayout llInner;

    @Bind(R.id.et_jd)
    EditText et_jd;

    @Bind(R.id.et_phone)
    EditText etPhone;

    @Bind(R.id.et_company)
    EditText etCompany;

    @Bind(R.id.et_id)
    EditText etId;

    @Bind(R.id.tv_reason)
    TextView tvReason;

    @Bind(R.id.tv_explain)
    TextView tvExplain;

    @Bind(R.id.tv_jd)
    TextView tvJdDepartment;

    @Bind(R.id.ll_outer)
    LinearLayout llOuter;

    @Bind(R.id.app_submit_bn)
    Button appSubmitBn;

    @Bind(R.id.rl_reason)
    RelativeLayout rlReason;

    @Bind(R.id.rl_user_type)
    RelativeLayout rlUserType;

    @Bind(R.id.rl_jd_department)
    RelativeLayout rlJdDepartment;

    @Bind(R.id.rl_department)
    RelativeLayout rlDepartment;

    @Bind(R.id.tv_reason_detail)
    EditText tvReasonDetail;

    @Bind(R.id.amount_view)
    AmountView amountView;

    @Bind(R.id.tv_user_train)
    TextView tvUserTrain;

    @Bind(R.id.rl_train)
    RelativeLayout rlTrain;

    private String signatureUrl;

    /**
     * 图片最多上传两张，身份证正反面
     */
    private int maxSize = 2;

    private NfcAdapter nfcAdapter;

    private PendingIntent pendingIntent;

    private IntentFilter[] mFilters;

    private String[][] mTechLists;

    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_card);
        ButterKnife.bind(this);
        initAll();
        // 获取nfc适配器，判断设备是否支持NFC功能
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null)
        {
            return;
        }
        else if (!nfcAdapter.isEnabled())
        {
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
    protected void onResume()
    {
        super.onResume();
        if (nfcAdapter == null)
        {
            return;
        }
        else if (!nfcAdapter.isEnabled())
        {
            return;
        }
        nfcAdapter.enableForegroundDispatch(GiveCardActivity.this, pendingIntent, mFilters,
                mTechLists);
        if (isFirst)
        {
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction()))
            {
                String result = processIntent(getIntent());
                String str = Util.hex2Decimal(result);
                etCardNumber.setText(str + "");
                etName.requestFocus();
            }
            isFirst = false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()))
        {
            String result = processIntent(intent);
            String str = Util.hex2Decimal(result);
            etCardNumber.setText(str + "");
            etName.requestFocus();
        }
    }

    /**
     * 获取tab标签中的内容
     *
     * @param intent
     * @return
     */
    @SuppressLint("NewApi")
    private String processIntent(Intent intent)
    {
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
        if (strId != null && !strId.equals(""))
        {
            return strId;
        }
        Parcelable[] rawmsgs = intent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawmsgs != null && rawmsgs.length > 0)
        {
            NdefMessage msg = (NdefMessage) rawmsgs[0];
            NdefRecord[] records = msg.getRecords();
            String resultStr = new String(records[0].getPayload());
            return resultStr;
        }
        else
        {
            final Parcelable p = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String str = (p != null) ? CardManager.load(p, getResources()) : null;
            return str;
        }
    }


    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("发卡");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    private int amountDay = 1;

    @Override
    public void initView()
    {
        initTitle();
        tvSex.setOnClickListener(this);
        rlDepartment.setOnClickListener(this);
        rlTrain.setOnClickListener(this);
        rlUserType.setOnClickListener(this);
        rlJdDepartment.setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        amountView.setGoods_storage(100);
        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener()
        {
            @Override
            public void onAmountChange(View view, int amount)
            {
                amountDay = amount;
//                Toast.makeText(getApplicationContext(), "Amount=>  " + amount, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<PhotoModel> single_photos = new ArrayList<PhotoModel>();

    private ArrayList<UploadGoodsBean> img_uri = new ArrayList<UploadGoodsBean>();

    private int screen_widthOffset;

    private MyGridView my_imgs_GV;

    GridImgAdapter gridImgsAdapter;

    @Override
    public void initViewData()
    {
        Config.ScreenMap = Config.getScreenSize(this, this);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screen_widthOffset = (display.getWidth() - (3 * DbTOPxUtils.dip2px(this, 2))) / 3;

        my_imgs_GV = (MyGridView) findViewById(R.id.my_goods_GV);
        gridImgsAdapter = new GridImgAdapter();
        my_imgs_GV.setAdapter(gridImgsAdapter);
        img_uri.add(null);
        gridImgsAdapter.notifyDataSetChanged();
    }

    class GridImgAdapter extends BaseAdapter implements ListAdapter
    {
        @Override
        public int getCount()
        {
            return img_uri.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            convertView = LayoutInflater.from(GiveCardActivity.this).inflate(R.layout.activity_addstory_img_item, null);

            ViewHolder holder;

            if (convertView != null)
            {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(GiveCardActivity.this).inflate(R.layout.activity_addstory_img_item, null);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.add_IB = (ImageView) convertView.findViewById(add_IB);
            int imgWidth = DisplayUtil.getWidth(mContext) / 4;
            ViewGroup.LayoutParams addParams = holder.add_IB.getLayoutParams();
            addParams.width = addParams.height = imgWidth;
            holder.add_IB.setLayoutParams(addParams);
            holder.delete_IV = (ImageView) convertView.findViewById(R.id.delete_IV);

            AbsListView.LayoutParams param = new AbsListView.LayoutParams(screen_widthOffset, screen_widthOffset);
            convertView.setLayoutParams(param);
            if (img_uri.get(position) == null)
            {
                holder.delete_IV.setVisibility(View.GONE);

                ImageLoaderUtil.getInstance().initImage(mContext, "drawable://" + R.drawable.iv_add_the_pic, holder.add_IB, "");
                holder.add_IB.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View arg0)
                    {
                        Log.e("sub", maxSize + "," + (img_uri.size() - 1));
                        if (maxSize - (img_uri.size() - 1) == 0)
                        {
                            ToastUtil.makeText(mContext, "最多添加" + maxSize + "张");
                        }
                        else
                        {
                            Intent intent = new Intent(GiveCardActivity.this, PhotoSelectorActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.putExtra("limit", maxSize - (img_uri.size() - 1));
                            startActivityForResult(intent, 0);
                        }
                    }
                });

            }
            else
            {
                ImageLoaderUtil.getInstance().initImage(mContext, "file://" + img_uri.get(position).getUrl(), holder.add_IB, "");
                holder.delete_IV.setOnClickListener(new View.OnClickListener()
                {
                    private boolean is_addNull;

                    @Override
                    public void onClick(View arg0)
                    {
                        is_addNull = true;
                        String img_url = img_uri.remove(position).getUrl();
                        int postion = -1;
                        for (int i = 0; i < single_photos.size(); i++)
                        {
                            if (single_photos.get(i).getOriginalPath().equals(img_url))
                            {
                                postion = i;
                            }
                        }
                        if (position != -1)
                        {
                            single_photos.remove(postion);
                        }
                        for (int i = 0; i < img_uri.size(); i++)
                        {
                            if (img_uri.get(i) == null)
                            {
                                is_addNull = false;
                                continue;
                            }
                        }
                        if (is_addNull)
                        {
                            img_uri.add(null);
                        }

                        FileUtils.DeleteFolder(img_url);//删除在emulate/0文件夹生成的图片

                        gridImgsAdapter.notifyDataSetChanged();
                    }
                });

                holder.add_IB.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("photos", (Serializable) single_photos);
                        bundle.putInt("position", position);
                        bundle.putBoolean("isSave", false);
                        CommonUtils.launchActivity(GiveCardActivity.this, PhotoPreviewActivity.class, bundle);
                    }
                });

            }
            return convertView;
        }

        class ViewHolder
        {
            ImageView add_IB;

            ImageView delete_IV;
        }
    }

    @Override
    public void initEvent()
    {
        appSubmitBn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                    upLoadData();
            }
        });
//        rlReason.setOnClickListener(this);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()

                                            {
                                                @Override
                                                public void onTabSelected(TabLayout.Tab tab)
                                                {
                                                    switch (tab.getPosition())
                                                    {
                                                        case 0:
                                                            llInner.setVisibility(View.GONE);
                                                            llOuter.setVisibility(View.VISIBLE);
                                                            etName.setHint("请输入外来人员姓名");
                                                            break;
                                                        case 1:
                                                            llInner.setVisibility(View.VISIBLE);
                                                            llOuter.setVisibility(View.GONE);
                                                            etName.setHint("请输入企业人员姓名");
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
            if (NotiTag.TAG_SIGNATURE_URL.equals(tag))
            {
                signatureUrl = ((NoticeEvent) event).getText();
            }
        }
        if (event instanceof NetResponseEvent)
        {

            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();

            if (tag.equals(GiveInnerCardResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                NetLoadingDialog.getInstance().dismissDialog();
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    GiveInnerCardResponse mGiveInnerCardResponse = GsonHelper.toType(result, GiveInnerCardResponse.class);
                    if (Constants.SUCESS_CODE.equals(mGiveInnerCardResponse.getResultCode()))
                    {
                        MainActivity.getNewBannerAndDataShow();
                        DialogUtil.showDialogOneButton(mContext, "发卡成功", "我知道了", NotiTag.TAG_CLOSE_ACTIVITY);
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mGiveInnerCardResponse.getResultCode(), mGiveInnerCardResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(GiveOutterCardResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    GiveOutterCardResponse mGiveInnerCardResponse = GsonHelper.toType(result, GiveOutterCardResponse.class);
                    if (Constants.SUCESS_CODE.equals(mGiveInnerCardResponse.getResultCode()))
                    {
//                        1、外来人员发卡成功后，如果接口里直接返回培训ID，需要直接进入培训页面，确认开始培训，关联当前的cardNo；
                        if (GeneralUtils.isNotNullOrZeroLenght(mGiveInnerCardResponse.getTrainingID()))
                        {
                            MainActivity.getNewBannerAndDataShow();
                            if (GeneralUtils.isNullOrZeroLenght(SharePref.getString(Constants.USER_LIST, "")))
                            {
                                UserServiceImpl.instance().getUserList(UserListResponse.class.getName());
                            }
                            Intent trainIntent = new Intent(mContext, TrainListActy.class);
                            trainIntent.putExtra(IntentCode.TRAIN_ID, mGiveInnerCardResponse.getTrainingID());
                            trainIntent.putExtra(IntentCode.CARD_NUM, etCardNumber.getText().toString());
                            startActivity(trainIntent);
                            finish();
                        }
                        else
                        {
                            // 2、如果没有返回培训ID，那么只是提示发卡成功，通过首页的培训选择培训后确认开始培训前，需要输入（识别）卡号或者选择内部人员（提供了user/list）接口
                            DialogUtil.showDialogOneButton(mContext, "发卡成功", "我知道了", NotiTag.TAG_CLOSE_ACTIVITY);
                        }
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mGiveInnerCardResponse.getResultCode(), mGiveInnerCardResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
            if (tag.equals(UploadFileResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    UploadFileResponse uploadFileResponse = GsonHelper.toType(result, UploadFileResponse.class);
                    if (Constants.SUCESS_CODE.equals(uploadFileResponse.getResultCode()))
                    {
                        NetLoadingDialog.getInstance().loading(mContext);
                        String jdDepartment = tvJdDepartment.getText().toString();
                        if (jdDepartment.equals("请选择"))
                        {
                            jdDepartment = "";
                        }
                        UserServiceImpl.instance().giveCard(signatureUrl,jdDepartment, et_jd.getText().toString(), etCardNumber.getText().toString(), etName.getText().toString(), sexIndex,
                                etPhone.getText().toString(), etCompany.getText().toString(), etId.getText().toString()
                                , userTypeArr[userTypeIndex], tvReasonDetail.getText().toString(), (userTrainIndex + 1) + "", amountDay + "",
                                uploadFileResponse.getUrlList(), GiveOutterCardResponse.class.getName());
                    }
                    else
                    {
                        NetLoadingDialog.getInstance().dismissDialog();
                        ErrorCode.doCode(mContext, uploadFileResponse.getResultCode(), uploadFileResponse.getDesc());
                    }
                }
                else
                {
                    NetLoadingDialog.getInstance().dismissDialog();
                    ToastUtil.showError(mContext);
                }
            }

        }
    }

    private String[] resonArr = new String[]{"男", "女"};

    private String[] trainArr = new String[]{"需要培训", "不培训"};

    List departList = GsonHelper.toType(Global.getLoginData(), LoginResponse.class).getDepartmentList();

    private String[] departmentArr = (String[]) departList.toArray(new String[departList.size()]);

    List userTypeList = GsonHelper.toType(SharePref.getString(Constants.OUTER_TYPE, ""), OuterTypeResponse.class).getTypeList();

    private String[] userTypeArr = (String[]) userTypeList.toArray(new String[userTypeList.size()]);

    private String refundReson = resonArr[0];

    private int whitchIndex = 0;

    private int departmentIndex = 0;

    private int userTypeIndex = 0;

    private int userTrainIndex = 0;

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
            case R.id.rl_department:
            case R.id.rl_jd_department:
                new AlertDialog.Builder(mContext).setTitle("请选择")
                        .setSingleChoiceItems(
                                departmentArr, departmentIndex,
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        tvDepartment.setText(departmentArr[which]);
                                        tvJdDepartment.setText(departmentArr[which]);
                                        departmentIndex = which;
                                        dialog.dismiss();
                                    }

                                }).show();
                break;
            case R.id.rl_train:
                new AlertDialog.Builder(mContext).setTitle("请选择")
                        .setSingleChoiceItems(
                                trainArr, userTrainIndex,
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        tvUserTrain.setText(trainArr[which]);
                                        userTrainIndex = which;
                                        if (trainArr[which].equals("不培训"))
                                        {
                                            findViewById(R.id.rl_date).setVisibility(View.GONE);
                                        }
                                        else
                                        {
                                            findViewById(R.id.rl_date).setVisibility(View.VISIBLE);
                                        }
                                        dialog.dismiss();
                                    }

                                }).show();
                break;
            case R.id.rl_user_type:
                new AlertDialog.Builder(mContext).setTitle("请选择")
                        .setSingleChoiceItems(
                                userTypeArr, userTypeIndex,
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        tvUserType.setText(userTypeArr[which]);
                                        userTypeIndex = which;
                                        dialog.dismiss();
                                    }

                                }).show();
                break;
            case R.id.rl_reason:
            case R.id.tv_reason_detail:
//                Intent intent = new Intent(mContext, OuterGetReasonActivity.class);
//                intent.putExtra(IntentCode.GIVE_OUTER_CARD_REASON, tvReasonDetail.getText().toString());
//                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100)
        {
            signatureUrl =data.getStringExtra("url");
            if (GeneralUtils.isNullOrZeroLenght(signatureUrl))
            {
                startActivityForResult(new Intent(mContext, HandWriteActivity.class), 1);
            }
            else
            {
                upLoadData();
            }
            return;
        }
        switch (requestCode)
        {

            case 0:
                if (data != null)
                {
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");
                    if (img_uri.size() > 0)
                    {
                        img_uri.remove(img_uri.size() - 1);
                    }

                    for (int i = 0; i < paths.size(); i++)
                    {
                        img_uri.add(new UploadGoodsBean(paths.get(i), false));
                        //上传参数
                    }
                    for (int i = 0; i < paths.size(); i++)
                    {
                        PhotoModel photoModel = new PhotoModel();
                        photoModel.setOriginalPath(paths.get(i));
                        photoModel.setChecked(true);
                        single_photos.add(photoModel);
                    }
                    if (img_uri.size() < 9)
                    {
                        img_uri.add(null);
                    }
                    gridImgsAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    int sexIndex = 1;

    private void upLoadData()
    {
        sexIndex = 1;
        if (GeneralUtils.isNullOrZeroLenght(etCardNumber.getText().toString()))
        {
            ToastUtil.makeText(mContext, "请填写卡号");
            return;
        }
        if (GeneralUtils.isNullOrZeroLenght(etName.getText().toString()))
        {
            ToastUtil.makeText(mContext, "请填写姓名");
            return;
        }
        if (tvSex.getText().toString().equals("请选择"))
        {
            ToastUtil.makeText(mContext, "请选择性别");
            return;
        }
        else
        {
            if (tvSex.getText().toString().equals("男"))
            {
                sexIndex = 1;
            }
            else
            {
                sexIndex = 2;
            }
        }

        if (llInner.getVisibility() == View.VISIBLE)
        {
//            if (tvDepartment.getText().toString().equals("请选择"))
//            {
//                ToastUtil.makeText(mContext, "请选择部门");
//                return;
//            }
            if (GeneralUtils.isNullOrZeroLenght(etNumber.getText().toString()))
            {
                ToastUtil.makeText(mContext, "请填写工号");
                return;
            }
            if (GeneralUtils.isNullOrZeroLenght(signatureUrl))
            {
                startActivityForResult(new Intent(mContext, HandWriteActivity.class), 1);

            }else {
                UserServiceImpl.instance().giveCard(signatureUrl,etCardNumber.getText().toString(), etName.getText().toString(), sexIndex, tvDepartment.getText().toString(), etNumber.getText().toString(), GiveInnerCardResponse.class.getName());
            }
        }
        else
        {
            if (GeneralUtils.isNullOrZeroLenght(etPhone.getText().toString()))
            {
                ToastUtil.makeText(mContext, "请填写联系方式");
                return;
            }
            if (GeneralUtils.isNullOrZeroLenght(etCompany.getText().toString()))
            {
                ToastUtil.makeText(mContext, "请填写来自单位");
                return;
            }
//            if (GeneralUtils.isNullOrZeroLenght(etId.getText().toString()))
//            {
//                ToastUtil.makeText(mContext, "请填写身份证号");
//                return;
//            }
            if (GeneralUtils.isNullOrZeroLenght(tvReasonDetail.getText().toString()))
            {
                ToastUtil.makeText(mContext, "请输入事由");
                return;
            }
            if (tvUserTrain.getText().toString().equals("请选择"))
            {
                ToastUtil.makeText(mContext, "请选择是否培训");
                return;
            }
            if (tvUserType.getText().toString().equals("请选择"))
            {
                ToastUtil.makeText(mContext, "请选择外来事由");
                return;
            }
            if (tvDepartment.getText().toString().equals("请选择"))
            {
                ToastUtil.makeText(mContext, "请选择接待部门");
                return;
            }
            if (GeneralUtils.isNotNullOrZeroLenght(et_jd.getText().toString()) && GeneralUtils.isUserExistBackUserId(et_jd.getText().toString()).equals(""))
            {
                ToastUtil.makeText(mContext, "请填写正确的接待人员");
                return;
            }


            List<File> files = new ArrayList<>();
            if (img_uri != null && img_uri.size() > 0)
            {
                for (UploadGoodsBean item : img_uri)
                {
                    if (item != null && item.getUrl() != null)
                    {
                        files.add(new File(item.getUrl()));
                    }
                }
            }
            if (files.size() >= 2)
            {
                NetLoadingDialog.getInstance().loading(mContext);
                UserServiceImpl.instance().uploadPic(files, UploadFileResponse.class.getName());
            }
            else
            {
                String jdDepartment = tvJdDepartment.getText().toString();
                if (jdDepartment.equals("请选择"))
                {
                    jdDepartment = "";
                }
                if (GeneralUtils.isNullOrZeroLenght(signatureUrl))
                {
                    startActivityForResult(new Intent(mContext, HandWriteActivity.class), 1);

                }else {
                    NetLoadingDialog.getInstance().loading(mContext);
                    UserServiceImpl.instance().giveCard(signatureUrl,jdDepartment, et_jd.getText().toString(), etCardNumber.getText().toString(), etName.getText().toString(), sexIndex,
                            etPhone.getText().toString(), etCompany.getText().toString(), etId.getText().toString()
                            , userTypeArr[userTypeIndex], tvReasonDetail.getText().toString(), (userTrainIndex + 1) + "", amountDay + "",
                            null, GiveOutterCardResponse.class.getName());
                }
            }
        }
    }


}
