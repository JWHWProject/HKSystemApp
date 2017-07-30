package com.anyi.door;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.adapter.CommonAdapter;
import cn.nj.www.my_module.adapter.ViewHolder;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.OuterPeopleResponse;
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
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.ToastUtil;
import de.greenrobot.event.EventBus;

public class OuterPeopleActivity extends BaseActivity {

    @Bind(R.id.my_list_view)
    ListView myListView;
    private CommonAdapter<OuterPeopleResponse.OutsidersListBean> mAdapter;
    private List<OuterPeopleResponse.OutsidersListBean> pList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer_people);
        ButterKnife.bind(this);
        initAll();
    }
    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("外来人员选择");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }
    @Override
    public void initView() {
        initTitle();
        pList= GeneralUtils.getOuterPeopleList();
        if(pList==null)pList=new ArrayList<>();
        mAdapter=new CommonAdapter<OuterPeopleResponse.OutsidersListBean>(OuterPeopleActivity.this,pList,R.layout.item_name) {
            @Override
            public void convert(ViewHolder helper, OuterPeopleResponse.OutsidersListBean item) {
                helper.setText(R.id.tv_name,item.getUserName());
            }
        };
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OuterPeopleResponse.OutsidersListBean item=(OuterPeopleResponse.OutsidersListBean)adapterView.getItemAtPosition(i);
                EventBus.getDefault().post(new NoticeEvent("SEL_OUT_PEOPLE",item.getUserName()));
                finish();
            }
        });
        if(pList==null||pList.size()==0){
            UserServiceImpl.instance().getOutCommerList(OuterPeopleResponse.class.getName());
        }
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void netResponse(BaseResponse event) {

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
            if (tag.equals(OuterPeopleResponse.class.getName()))
            {
                OuterPeopleResponse mOuterPeopleResponse = GsonHelper.toType(result, OuterPeopleResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    if (Constants.SUCESS_CODE.equals(mOuterPeopleResponse.getResultCode()))
                    {
                        SharePref.saveString(Constants.OUTER_PEOPLE, result);
                        pList=mOuterPeopleResponse.getOutsidersList();
                        if(pList==null)pList=new ArrayList<>();
                        mAdapter.setData(pList);
                        mAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        ErrorCode.doCode(this, mOuterPeopleResponse.getResultCode(), mOuterPeopleResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(this);
                }
            }
        }

    }
}
