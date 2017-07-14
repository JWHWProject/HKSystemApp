package com.anyi.door;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.tools.DialogUtil;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;

/**
 * train list
 */
public class SearchTrainListActy extends BaseActivity implements View.OnClickListener {
    public String tagStr="";

    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_train_list);
        initAll();
    }


    @Override
    public void initView() {
        listView = (ExpandableListView) findViewById(R.id.list);
        final MyExpandableListAdapter adapter = new MyExpandableListAdapter(mContext);
        listView.setAdapter(adapter);
        listView.setGroupIndicator(null);
        //只展开一个
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i <4; i++) {
                    if (groupPosition != i) {
                        listView.collapseGroup(i);
                    }
                }
            }

        });


        //点击跳转
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {


            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,	int groupPosition, int childPosition, long id) {

                DialogUtil.showNoTipTwoBnttonDialog(mContext,"确定开始培训","取消","确定",NotiTag.TAG_DLG_CANCEL,NotiTag.TAG_DLG_OK);
                tagStr=groupPosition+"  "+childPosition;
                return false;
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
            if (NotiTag.TAG_DLG_OK.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName())) {
                ToastUtil.makeText(mContext,tagStr);
            }
        } else if (event instanceof NetResponseEvent) {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();

        }

    }



    @Override
    public void onClick(View v) {
    }
}
