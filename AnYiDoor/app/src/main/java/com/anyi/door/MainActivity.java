package com.anyi.door;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.view.banner.ConvenientBanner;
import cn.nj.www.my_module.view.banner.demo.LocalImageHolderView;
import cn.nj.www.my_module.view.banner.holder.CBViewHolderCreator;


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
        bannerFirstInit();
        initAll();
    }

    @Override
    public void initView()
    {

    }

    @Override
    public void initViewData()
    {

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
        String result = SharePref.getString(Constants.BANNER_RESULT, "");
//        if (GeneralUtils.isNotNullOrZeroLenght(result))
//        {
//            BannerResponse mBannerResponse = GsonHelper.toType(result, BannerResponse.class);
//            initBanner(mBannerResponse.getData());
//        }
    }


    /**
     * Banner展示网络数据
     */
//    private synchronized void initBanner(final List<BannerListBean> ad)
//    {
//        if (ad == null || ad.size() < 1)
//        {
//            return;
//        }
//        networkImages.clear();
//        for (int i = 0; i < ad.size(); i++)
//        {
//            if (!networkImages.contains(ad.get(i).getImageUrl()))
//            {
//                networkImages.add(URLUtil.IMAGE_BASE + ad.get(i).getImageUrl());
//                CMLog.e(Constants.HTTP_TAG, URLUtil.IMAGE_BASE + ad.get(i).getImageUrl());
//            }
//        }
//        mBanner.stopTurning();
//        mBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>()
//        {
//            @Override
//            public NetworkImageHolderView createHolder()
//            {
//                return new NetworkImageHolderView();
//            }
//        }, networkImages).setOnItemClickListener(new OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(int position)
//            {
//                if (GeneralUtils.isNotNullOrZeroLenght(ad.get(position).getImageLink()))
//                {
//                    Intent intentDoFile = new Intent(getActivity(), CommonWebViewActivity.class);
//                    intentDoFile.putExtra(IntentCode.COMMON_WEB_VIEW_TITLE, ad.get(position).getImageDesc());
//                    intentDoFile.putExtra(IntentCode.COMMON_WEB_VIEW_URL, ad.get(position).getImageLink());
//                    startActivity(intentDoFile);
//                }
//            }
//        });
//    }

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
        switch (view.getId()){
            case R.id.ll_back:
                startActivity(new Intent(mContext,GiveBackCardActivity.class));
                break;
            //发卡
            case R.id.ll_fk:
                break;
            //测试
            case R.id.ll_test:
                break;
            case R.id.ll_train:
                startActivity(new Intent(mContext,TrainListActy.class));
                break;
        }
    }
}
