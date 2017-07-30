package com.anyi.door;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyi.door.choosecity.ClearEditText;
import com.anyi.door.choosecity.adapter.CityAdapter;
import com.anyi.door.choosecity.model.CityItem;
import com.anyi.door.choosecity.widget.ContactItemInterface;
import com.anyi.door.choosecity.widget.ContactListViewImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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

public class OuterPeopleActivity extends BaseActivity implements TextWatcher {

    @Bind(R.id.my_list_view)
    ContactListViewImpl myListView;
    @Bind(R.id.hint_txt)
    TextView hintTxt;
    @Bind(R.id.input_search_query)
    ClearEditText inputSearchQuery;
    @Bind(R.id.chang_bg)
    FrameLayout changBg;
    @Bind(R.id.searchBarContainer)
    LinearLayout searchBarContainer;
    private CityAdapter mAdapter;
    private List<OuterPeopleResponse.OutsidersListBean> pList = new ArrayList<>();
    List<ContactItemInterface> contactList = new ArrayList<ContactItemInterface>();
    List<ContactItemInterface> filterList=new ArrayList<>();
    private SearchListTask curSearchTask = null;
    private String searchString;

    private Object searchLock = new Object();

    boolean inSearchMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer_people);
        ButterKnife.bind(this);
        initAll();
    }

    private void initTitle() {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("外来人员选择");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    @Override
    public void initView() {
        initTitle();
        pList = GeneralUtils.getOuterPeopleList();
        if (pList == null) pList = new ArrayList<>();
        contactList.clear();
        for (int i = 0; i < pList.size(); i++) {
            OuterPeopleResponse.OutsidersListBean item = pList.get(i);
            contactList.add(new CityItem(item.getUserName(), item.getUserName(), i, 0));
        }
        mAdapter = new CityAdapter(this, R.layout.city_item,
                contactList);
        myListView.setFastScrollEnabled(true);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position,
                                    long id) {
                List<ContactItemInterface> searchList = inSearchMode ? filterList
                        : contactList;
//                toastMy.toshow(searchList.get(position).getDisplayId() + "=" + searchList.get(position).getDisplayType() + "=" + searchList.get(position).getDisplayInfo());
                EventBus.getDefault().post(new NoticeEvent("SEL_OUT_PEOPLE", searchList.get(position).getDisplayInfo()));
                finish();
            }
        });
        inputSearchQuery.addTextChangedListener(this);
        if (pList == null || pList.size() == 0) {
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
            if (tag.equals(OuterPeopleResponse.class.getName())) {
                OuterPeopleResponse mOuterPeopleResponse = GsonHelper.toType(result, OuterPeopleResponse.class);
                if (GeneralUtils.isNotNullOrZeroLenght(result)) {
                    if (Constants.SUCESS_CODE.equals(mOuterPeopleResponse.getResultCode())) {
                        SharePref.saveString(Constants.OUTER_PEOPLE, result);
                        pList = mOuterPeopleResponse.getOutsidersList();
                        if (pList == null) pList = new ArrayList<>();
                        contactList.clear();
                        for (int i = 0; i < mOuterPeopleResponse.getOutsidersList().size(); i++) {
                            OuterPeopleResponse.OutsidersListBean item = mOuterPeopleResponse.getOutsidersList().get(i);
                            contactList.add(new CityItem(item.getUserName(), item.getUserName(), i, 0));
                        }
                        mAdapter = new CityAdapter(this, R.layout.city_item,
                                contactList);
                        myListView.setFastScrollEnabled(true);
                        myListView.setAdapter(mAdapter);
                    } else {
                        ErrorCode.doCode(this, mOuterPeopleResponse.getResultCode(), mOuterPeopleResponse.getDesc());
                    }
                } else {
                    ToastUtil.showError(this);
                }
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            if (s.toString().equals("")) {
                changBg.setBackgroundResource(R.drawable.search_puin);
                hintTxt.setVisibility(View.VISIBLE);
            } else {
                changBg.setBackgroundResource(R.mipmap.shuru);
                hintTxt.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        searchString = inputSearchQuery.getText().toString().trim().toUpperCase();

        if (curSearchTask != null
                && curSearchTask.getStatus() != AsyncTask.Status.FINISHED) {
            try {
                curSearchTask.cancel(true);
            } catch (Exception e) {
                Log.i("jw", "Fail to cancel running search task");
            }

        }
        curSearchTask = new SearchListTask();
        curSearchTask.execute(searchString);
    }

    private class SearchListTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            filterList.clear();

            String keyword = params[0];

            inSearchMode = (keyword.length() > 0);

            if (inSearchMode) {
                // get all the items matching this
                for (ContactItemInterface item : contactList) {
                    CityItem contact = (CityItem) item;

                    boolean isPinyin = contact.getFullName().toUpperCase()
                            .indexOf(keyword) > -1;
                    boolean isChinese = contact.getNickName().indexOf(keyword) > -1;

                    if (isPinyin || isChinese) {
                        filterList.add(item);
                    }

                }

            }
            return null;
        }

        protected void onPostExecute(String result) {

            synchronized (searchLock) {

                if (inSearchMode) {

                    CityAdapter adapter = new CityAdapter(OuterPeopleActivity.this,
                            R.layout.city_item, filterList);
                    adapter.setInSearchMode(true);
                    myListView.setInSearchMode(true);
                    myListView.setAdapter(adapter);
                    myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            List<ContactItemInterface> searchList = inSearchMode ? filterList
                                    : contactList;
//                            toastMy.toshow(searchList.get(position).getDisplayId()+"="+searchList.get(position).getDisplayType()+"="+searchList.get(position).getDisplayInfo());
                            EventBus.getDefault().post(new NoticeEvent("SEL_OUT_PEOPLE", searchList.get(position).getDisplayInfo()));
                            finish();
                        }
                    });
                } else {
                    CityAdapter adapter = new CityAdapter(OuterPeopleActivity.this,
                            R.layout.city_item, contactList);
                    adapter.setInSearchMode(false);
                    myListView.setInSearchMode(false);
                    myListView.setAdapter(adapter);
                }
            }

        }
    }
}
