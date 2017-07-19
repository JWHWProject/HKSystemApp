package com.anyi.door;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyi.door.test_utils.JudgeAdapter;
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
import cn.nj.www.my_module.bean.index.FinishTestResponse;
import cn.nj.www.my_module.bean.index.OnlineTrainingAnswer;
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

    private SingleListAdapter singleAdapter;

    private MultiltyAdapter multiltyAdapter;

    private JudgeAdapter judgeAdapter;


    private String examID;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sheet);
        ButterKnife.bind(this);
        initTitle();
        initAll();


    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("考核");
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
    }

    @Override
    public void initView()
    {

    }

    @Override
    public void initViewData()
    {
        NetLoadingDialog.getInstance().loading(mContext);
        examID = getIntent().getStringExtra(IntentCode.EXAM_ID);
        UserServiceImpl.instance().testDetail(examID, ExamResponse.class.getName());
    }

    List<OnlineTrainingAnswer> answerList = new ArrayList<>();

    @Override
    public void initEvent()
    {

        findViewById(R.id.getBn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //判断
                if (judgeAdapter != null)
                {
                    if (judgeAdapter.isFinishAll())
                    {
                        answerList.addAll(judgeAdapter.getAnswerList());
                    }
                    else
                    {
                        ToastUtil.makeText(mContext, "请完成所有题目后提交");
                        return;
                    }
                }
                //单选
                if (singleAdapter != null)
                {
                    if (singleAdapter.isFinishAll())
                    {
                        answerList.addAll(singleAdapter.getAnswerList());
                    }
                    else
                    {
                        ToastUtil.makeText(mContext, "请完成所有题目后提交");
                        return;
                    }
                }
                //多选
                if (multiltyAdapter != null)
                {
                    if (multiltyAdapter.isFinishAll())
                    {
                        answerList.addAll(multiltyAdapter.getAnswerList());
                    }
                    else
                    {
                        ToastUtil.makeText(mContext, "请完成所有题目后提交");
                        return;
                    }
                }
                //获取到所有数据，提交
                if (answerList.size() > 0)
                {
                    UserServiceImpl.instance().finishTest(examID, answerList, null,
                            FinishTestResponse.class.getName());
                }
            }
        });
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
                DialogUtil.showCloseTwoBnttonDialog(mContext,
                        "您确定要中途取消考核？", "取消", "确定");
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
                            if (key.equals("单选题"))
                            {

                                singleAdapter = new SingleListAdapter(mContext, valueList, examID);
                                listView2.setAdapter(singleAdapter);
                            }
                            if (key.equals("判断题"))
                            {

                                judgeAdapter = new JudgeAdapter(mContext, valueList, examID);
                                listView1.setAdapter(judgeAdapter);
                            }
                            if (key.equals("多选题"))
                            {

                                multiltyAdapter = new MultiltyAdapter(mContext, valueList, examID);
                                listView3.setAdapter(multiltyAdapter);
                            }
                        }

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
            if (tag.equals(FinishTestResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                NetLoadingDialog.getInstance().dismissDialog();
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    FinishTestResponse mFinishTestResponse = GsonHelper.toType(result, FinishTestResponse.class);
                    if (Constants.SUCESS_CODE.equals(mFinishTestResponse.getResultCode()))
                    {
                        DialogUtil.showDialogOneButton(mContext, "已完成考核", "我知道了", NotiTag.TAG_CLOSE_ACTIVITY);
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, mFinishTestResponse.getResultCode(), mFinishTestResponse.getDesc());
                    }
                }
                else
                {
                    ToastUtil.showError(mContext);
                }
            }
        }

    }

    @Override
    public void onBackPressed()
    {
        DialogUtil.showCloseTwoBnttonDialog(mContext,
                "您确定要中途取消考核？", "取消", "确定");
    }
}