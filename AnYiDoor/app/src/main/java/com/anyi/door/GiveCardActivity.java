package com.anyi.door;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
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
import com.zzti.fengyongge.imagepicker.PhotoPreviewActivity;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;
import com.zzti.fengyongge.imagepicker.model.PhotoModel;
import com.zzti.fengyongge.imagepicker.util.CommonUtils;
import com.zzti.fengyongge.imagepicker.util.FileUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.ImageLoaderUtil;
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

    @Bind(R.id.et_card_number)
    EditText etCardNumber;

    @Bind(R.id.et_name)
    EditText etName;

    @Bind(R.id.tv_sex_left)
    TextView tvSexLeft;

    @Bind(R.id.tv_department_left)
    TextView tvDepartmentLeft;

    @Bind(R.id.tv_department)
    TextView tvDepartment;

    @Bind(R.id.et_number)
    EditText etNumber;

    @Bind(R.id.ll_inner)
    LinearLayout llInner;

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

    @Bind(R.id.ll_outer)
    LinearLayout llOuter;

    @Bind(R.id.app_submit_bn)
    Button appSubmitBn;

    @Bind(R.id.rl_reason)
    RelativeLayout rlReason;

    @Bind(R.id.tv_reason_detail)
    TextView tvReasonDetail;

    private int maxSize = 4;

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

            holder.add_IB = (ImageView) convertView.findViewById(R.id.add_IB);
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
        rlReason.setOnClickListener(this);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()

                                            {
                                                @Override
                                                public void onTabSelected(TabLayout.Tab tab)
                                                {
                                                    switch (tab.getPosition())
                                                    {
                                                        case 0:
                                                            llInner.setVisibility(View.VISIBLE);
                                                            llOuter.setVisibility(View.GONE);
                                                            break;
                                                        case 1:
                                                            llInner.setVisibility(View.GONE);
                                                            llOuter.setVisibility(View.VISIBLE);
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
            case R.id.rl_reason:
            case R.id.tv_reason_detail:
                Intent intent = new Intent(mContext, OuterGetReasonActivity.class);
                intent.putExtra(IntentCode.GIVE_OUTER_CARD_REASON, tvReasonDetail.getText().toString());
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
        {
            if (resultCode == 1)
            {
                String result = data.getStringExtra("reason");
                tvReasonDetail.setText(result);
                if (GeneralUtils.isNotNullOrZeroLenght(tvReasonDetail.getText().toString()))
                {
                    tvReasonDetail.setVisibility(View.VISIBLE);
                }
                else
                {
                    tvReasonDetail.setVisibility(View.GONE);
                }
                return;
            }
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

    private void upLoadData()
    {
        if (GeneralUtils.isNotNullOrZeroLenght(etCardNumber.getText().toString())){
            ToastUtil.makeText(mContext,"请填写卡号");
            return;
        }
        if (GeneralUtils.isNotNullOrZeroLenght(etName.getText().toString())){
            ToastUtil.makeText(mContext,"请填写姓名");    return;
        }
        if (tvSex.getText().toString().equals("请选择")){
            ToastUtil.makeText(mContext,"请选择性别");    return;
        }

        if (llInner.getVisibility() == View.VISIBLE)
        {
            if (tvDepartment.getText().toString().equals("请选择")){
                ToastUtil.makeText(mContext,"请选择部门");
                return;
            }
            if (GeneralUtils.isNotNullOrZeroLenght(etNumber.getText().toString())){
                ToastUtil.makeText(mContext,"请填写工号");
                return;
            }
            //TODO：获取到数据
        }
        else
        {
            if (GeneralUtils.isNotNullOrZeroLenght(etPhone.getText().toString())){
                ToastUtil.makeText(mContext,"请填写联系方式");    return;
            }
            if (GeneralUtils.isNotNullOrZeroLenght(etCompany.getText().toString())){
                ToastUtil.makeText(mContext,"请填写来自单位");    return;
            }
            if (GeneralUtils.isNotNullOrZeroLenght(etId.getText().toString())){
                ToastUtil.makeText(mContext,"请填写身份证号");    return;
            }
            if (tvReasonDetail.getText().toString().length()<30){
                ToastUtil.makeText(mContext,"请输入事由，不少于30字");    return;
            }
            //TODO:
        }
    }


}
