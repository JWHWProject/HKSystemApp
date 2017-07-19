package com.anyi.door;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.anyi.door.test_utils.MultiltyAdapter;
import com.anyi.door.test_utils.SingleListAdapter;
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
import cn.nj.www.my_module.bean.index.ExamBean;
import cn.nj.www.my_module.bean.index.ExamResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import cn.nj.www.my_module.view.MyListView;

/**
 * test
 */
public class TestListActivity extends BaseActivity implements View.OnClickListener
{

    @Bind(R.id.tv1)
    TextView tv1;

    @Bind(R.id.ListView01)
    MyListView listView1;

    @Bind(R.id.tv2)
    TextView tv2;

    @Bind(R.id.ListView02)
    MyListView listView2;

    @Bind(R.id.tv3)
    TextView tv3;

    @Bind(R.id.ListView03)
    MyListView listView3;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sheet);
        ButterKnife.bind(this);
        initAll();

        final MultiltyAdapter adap = new MultiltyAdapter(this);
        listView1.setAdapter(adap);



//        findViewById(R.id.getBn).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                String answer = "";
//                for (int i = 0; i < cadapter.getCount(); i++)
//                {
//                    answer += cadapter.getItem(i).getCurrent() + "  ";
//                }
//                adap.getcheckMap();
//            }
//        });

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
        NetLoadingDialog.getInstance().loading(mContext);
        UserServiceImpl.instance().testDetail(getIntent().getStringExtra(IntentCode.EXAM_ID), ExamResponse.class.getName());
    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }



    @Override
    public void onClick(View view)
    {

    }

    private List<ExamBean> examBeanList = new ArrayList<>();

    @Override
    public void onEventMainThread(BaseResponse event) throws JSONException
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                finish();
            }
        }
        else if (event instanceof NetResponseEvent)
        {
            NetLoadingDialog.getInstance().dismissDialog();
            String tag = ((NetResponseEvent) event).getTag();
            String result = ((NetResponseEvent) event).getResult();
            if (tag.equals(ExamResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    ExamResponse mExamResponse = GsonHelper.toType(result, ExamResponse.class);
                    if (Constants.SUCESS_CODE.equals(mExamResponse.getResultCode()))
                    {
                        examBeanList.clear();
                        JSONObject jsonObject = new JSONObject(result);
                        Map<String, List<ExamBean.ExamDetailBean>> map = new Gson().fromJson(jsonObject.getString("typeMap"), new TypeToken<Map<String, List<ExamBean.ExamDetailBean>>>()
                        {
                        }.getType());
                        Iterator entries = map.entrySet().iterator();
                        while (entries.hasNext())
                        {
                            Map.Entry entry = (Map.Entry) entries.next();
                            String key = (String) entry.getKey();
                            List<ExamBean.ExamDetailBean> valueList = (List<ExamBean.ExamDetailBean>) entry.getValue();
                            examBeanList.add(new ExamBean(key, valueList));
                            if (key.equals("单选题")){

                                final SingleListAdapter cadapter = new SingleListAdapter(mContext, valueList);
                                listView2.setAdapter(cadapter);
                            }
                        }
                        //获取到3个list

                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mExamResponse.getResultCode(), mExamResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
        }

    }
}