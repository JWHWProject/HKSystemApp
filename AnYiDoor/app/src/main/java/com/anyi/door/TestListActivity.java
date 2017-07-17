package com.anyi.door;

import android.os.Bundle;
import android.view.View;

import com.anyi.door.test_utils.MultiltyAdapter;
import com.anyi.door.test_utils.SingleListAdapter;
import com.anyi.door.test_utils.Model;

import java.util.ArrayList;

import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.view.MyListView;

import static com.anyi.door.R.id.ListView02;

/**
 * test
 */
public class TestListActivity extends BaseActivity implements View.OnClickListener
{

    /**
     * Called when the activity is first created.
     */
    private MyListView listView,listView1;

    private ArrayList<Model> dataList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sheet);
        dataList = new ArrayList<Model>();
        setData();
        listView = (MyListView) findViewById(R.id.ListView01);
        listView1 = (MyListView) findViewById(ListView02);
        final MultiltyAdapter adap = new MultiltyAdapter(this);
        listView1.setAdapter(adap);
        final SingleListAdapter cadapter = new SingleListAdapter(mContext, dataList);
        listView.setAdapter(cadapter);
        findViewById(R.id.getBn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String answer = "";
                for (int i = 0; i < cadapter.getCount(); i++)
                {
                    answer += cadapter.getItem(i).getCurrent() + "  ";
                }
                adap.getcheckMap();
                CMLog.e("hq", "data: " + answer);
            }
        });

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

    }

    @Override
    public void netResponse(BaseResponse event)
    {

    }

    private void setData()
    {

        Model m = new Model("Ik word op de hoogte gehouden van de ontwikkelingen binnen onze organisatie.");

        dataList.add(m);

        Model m1 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m1);
        Model m2 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m2);
        Model m3 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m3);
        Model m4 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m4);
        Model m5 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m5);
        Model m6 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m6);
        Model m7 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m7);
        Model m8 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m8);
        Model m9 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m9);
        Model m10 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m10);
        Model m11 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m11);
        Model m12 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m12);
        Model m13 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m13);
        Model m14 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m14);
        Model m15 = new Model("Ik sta achter de huidige koers van onze organisatie.");
        dataList.add(m15);
    }

    @Override
    public void onClick(View view)
    {

    }
}