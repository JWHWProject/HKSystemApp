package com.anyi.door;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyi.door.utils.ForceUpdateDialog;
import com.anyi.door.video.base.TinyWindowPlayActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.BannerResponse;
import cn.nj.www.my_module.bean.index.LoginResponse;
import cn.nj.www.my_module.bean.index.OuterTypeResponse;
import cn.nj.www.my_module.bean.index.UpdateResponse;
import cn.nj.www.my_module.bean.index.UserListResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.CommonWebViewActivity;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.DisplayUtil;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.banner.ConvenientBanner;
import cn.nj.www.my_module.view.banner.demo.LocalImageHolderView;
import cn.nj.www.my_module.view.banner.demo.NetworkImageHolderView;
import cn.nj.www.my_module.view.banner.holder.CBViewHolderCreator;
import cn.nj.www.my_module.view.banner.listener.OnItemClickListener;

import static cn.nj.www.my_module.constant.Constants.BANNER_RESULT;


public class MainActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.index_banner)
    ConvenientBanner indexBanner;

    @Bind(R.id.bn_card_number)
    Button bnCardNumber;

    @Bind(R.id.bn_train_number)
    Button bnTrainNumber;

    @Bind(R.id.bn_test_number)
    Button bnTestNumber;

    @Bind(R.id.ll_fk)
    LinearLayout llFk;

    @Bind(R.id.ll_train)
    LinearLayout llTrain;

    @Bind(R.id.ll_test)
    LinearLayout llTest;

    @Bind(R.id.ll_back)
    LinearLayout llBack;

    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    @Bind(R.id.top_view_right_tv)
    TextView topViewRightTv;

    @Bind(R.id.tv_title)
    TextView tvTitle;

    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    /**
     * 网络图片地址
     */
    private List<String> networkImages = new ArrayList<>();

    private ConvenientBanner mBanner;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setLastUpdateTime();
        bannerFirstInit();
        initAll();

    }

    private void setLastUpdateTime()
    {
        if (GeneralUtils.isNullOrZeroLenght(SharePref.getString(Constants.LAST_UPDATE_TIME, "")))
        {
            SharePref.saveString(Constants.LAST_UPDATE_TIME, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        }
    }

    @Override
    public void initView()
    {
        if (GeneralUtils.isNotNullOrZeroLenght(Global.getLoginData()))
        {
            tvTitle.setText(GsonHelper.toType(Global.getLoginData(), LoginResponse.class).getUser().getCompanyName());
        }
        topViewRightTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DialogUtil.exitAccountDialog(mContext);
            }
        });
    }

    @Override
    public void initViewData()
    {
        NetLoadingDialog.getInstance().loading(mContext);
        UserServiceImpl.instance().getBanner(BannerResponse.class.getName());
        UserServiceImpl.instance().getOuterType(OuterTypeResponse.class.getName());
        UserServiceImpl.instance().getUserList(UserListResponse.class.getName());
        String lngAndLat = getLngAndLat(MainActivity.this);
        UserServiceImpl.instance().init(lngAndLat.split(",")[0], lngAndLat.split(",")[1], UpdateResponse.class.getName());
    }

    @Override
    public void initEvent()
    {
        llBack.setOnClickListener(this);
        llFk.setOnClickListener(this);
        llTest.setOnClickListener(this);
        llTrain.setOnClickListener(this);
    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    /**
     * 初始化Banner
     */
    private void bannerFirstInit()
    {
        //第一次展示默认本地图片
        localImages.add(R.mipmap.ic_launcher);//默认图片
        localImages.add(R.mipmap.about_icon);//默认图片
        mBanner = (ConvenientBanner) findViewById(R.id.index_banner);
        mBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>()
                {
                    @Override
                    public LocalImageHolderView createHolder()
                    {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.icon_banner_focus, R.mipmap.icon_banner_nofocus});
        String result = SharePref.getString(BANNER_RESULT, "");
        if (GeneralUtils.isNotNullOrZeroLenght(result))
        {
            BannerResponse mBannerResponse = GsonHelper.toType(result, BannerResponse.class);
            initBanner(mBannerResponse.getBannerList());
            //显示头部三个数据
            bnCardNumber.setText(mBannerResponse.getCardCount() + "");
            bnTrainNumber.setText(mBannerResponse.getTrainingCount() + "");
            bnTestNumber.setText(mBannerResponse.getExamCount() + "");
        }
    }


    @Override
    public void onEventMainThread(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                startActivity(new Intent(mContext, LoginActy.class));
                finish();
            }
            if (NotiTag.TAG_DLG_OK.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
//                UserServiceImpl.instance().startOnlineTest(tagStr,StartTrainResponse.class.getName());
            }
            if (NotiTag.TAG_CANCEL_UPDATE.equals(tag))
            {
                //取消更新
            }
        }
        if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(UserListResponse.class.getName()))
            {
                UserListResponse mUserListResponse = GsonHelper.toType(result, UserListResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mUserListResponse.getResultCode()))
                    {
                        SharePref.saveString(Constants.USER_LIST, result);
                        CMLog.e("hq", SharePref.getString(Constants.USER_LIST, ""));
                    }
                }
            }
            if (tag.equals(BannerResponse.class.getName()))
            {
                BannerResponse mBannerResponse = GsonHelper.toType(result, BannerResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mBannerResponse.getResultCode()))
                    {
                        initBanner(mBannerResponse.getBannerList());
                        SharePref.saveString(BANNER_RESULT, result);
                        //显示头部三个数据
                        bnCardNumber.setText(mBannerResponse.getCardCount() + "");
                        bnTrainNumber.setText(mBannerResponse.getTrainingCount() + "");
                        bnTestNumber.setText(mBannerResponse.getExamCount() + "");

                    }
                    else
                    {
                        ErrorCode.doCode(this, mBannerResponse.getResultCode(), mBannerResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }

            else if (tag.equals(OuterTypeResponse.class.getName()))
            {
                OuterTypeResponse mOuterTypeResponse = GsonHelper.toType(result, OuterTypeResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mOuterTypeResponse.getResultCode()))
                    {
                        SharePref.saveString(Constants.OUTER_TYPE, result);
                    }
                    else
                    {
                        ErrorCode.doCode(this, mOuterTypeResponse.getResultCode(), mOuterTypeResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }

            else if (tag.equals(UpdateResponse.class.getName()))
            {
                UpdateResponse mUpdateResponse = GsonHelper.toType(result, UpdateResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mUpdateResponse.getResultCode()))
                    {
                        if (mUpdateResponse.getAppVersionInfo().getVersionCode() > Constants.VERSION_CODE)
                        {
                            ForceUpdateDialog mForceUpdateDialog = new ForceUpdateDialog(MainActivity.this, mUpdateResponse.getAppVersionInfo().getUpdateType() + "");
                            mForceUpdateDialog
                                    .setDownloadUrl(mUpdateResponse.getAppVersionInfo().getRequestUrl())
                                    .setTitle(tvTitle.getText().toString() + "有更新啦")
                                    .setReleaseTime(mUpdateResponse.getAppVersionInfo().getCreateTime())
                                    .setVersionName(mUpdateResponse.getAppVersionInfo().getVersionCode() + "")
                                    .setUpdateDesc(mUpdateResponse.getAppVersionInfo().getUpdateDescription())
                                    .setFileName("anyi.apk")
                                    .setFilePath(Environment.getExternalStorageDirectory().getPath() + "/checkupdatelib").show();
                            setDialogWindowAttr(mForceUpdateDialog);
                        }

                    }
                    else
                    {
                        ErrorCode.doCode(this, mUpdateResponse.getResultCode(), mUpdateResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
        }

    }

    public void setDialogWindowAttr(Dialog dlg)
    {
        Window window = dlg.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = DisplayUtil.getWidth(mContext) * 3 / 5;//宽高可设置具体大小

        dlg.getWindow().setAttributes(lp);
    }

    /**
     * Banner展示网络数据
     */
    private synchronized void initBanner(final List<BannerResponse.BannerListBean> ad)
    {
        if (ad == null || ad.size() < 1)
        {
            return;
        }
        networkImages.clear();
        for (int i = 0; i < ad.size(); i++)
        {
            if (!networkImages.contains(ad.get(i).getRequestUrl()))
            {
                networkImages.add(ad.get(i).getRequestUrl());
//                CMLog.e(Constants.HTTP_TAG, URLUtil.IMAGE_BASE + ad.get(i).getRequestUrl());
            }
        }
        mBanner.stopTurning();
        mBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>()
        {
            @Override
            public NetworkImageHolderView createHolder()
            {
                return new NetworkImageHolderView();
            }
        }, networkImages).setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                if (GeneralUtils.isNotNullOrZeroLenght(ad.get(position).getLink()))
                {
                    Intent intentDoFile = new Intent(mContext, CommonWebViewActivity.class);
                    intentDoFile.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, ad.get(position).getTitle());
                    intentDoFile.putExtra(IntentCode.COMMON_WEB_VIEW_URL, ad.get(position).getLink());
                    startActivity(intentDoFile);
                }
            }
        });
    }

    // 停止自动翻页
    @Override
    public void onPause()
    {
        super.onPause();
        //停止翻页
        mBanner.stopTurning();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mBanner.startTurning(Constants.BANNER_TURN_TIME);
    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ll_back:
                startActivity(new Intent(mContext, GiveBackCardActivity.class));
                break;
            //发卡
            case R.id.ll_fk:
                if (GeneralUtils.isNotNullOrZeroLenght(SharePref.getString(Constants.OUTER_TYPE, "")))
                {
                    startActivity(new Intent(mContext, GiveCardActivity.class));
                }
                else
                {
                    NetLoadingDialog.getInstance().loading(mContext);
                    UserServiceImpl.instance().getOuterType(OuterTypeResponse.class.getName());
                }
                break;
            //测试
            case R.id.ll_test:
                if (GeneralUtils.isNullOrZeroLenght(SharePref.getString(Constants.USER_LIST, "")))
                {
                    UserServiceImpl.instance().getUserList(UserListResponse.class.getName());
                }
                Intent testIntent = new Intent(mContext, TrainListActy.class);
                testIntent.putExtra(IntentCode.TEST_INTENT, "1");
                startActivity(testIntent);
                break;
            case R.id.ll_train:
                if (GeneralUtils.isNullOrZeroLenght(SharePref.getString(Constants.USER_LIST, "")))
                {
                    UserServiceImpl.instance().getUserList(UserListResponse.class.getName());
                }
                startActivity(new Intent(mContext, TrainListActy.class));
                break;
        }
    }

    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    private String getLngAndLat(Context context)
    {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {  //从gps获取经纬度
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null)
            {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            else
            {
                return getLngAndLatWithNetwork();
            }
        }
        else
        {    //从网络获取经纬度
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null)
            {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        return longitude + "," + latitude;
    }

    //从网络获取经纬度
    public String getLngAndLatWithNetwork()
    {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null)
        {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        return longitude + "," + latitude;
    }


    LocationListener locationListener = new LocationListener()
    {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider)
        {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider)
        {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location)
        {
        }
    };


}
