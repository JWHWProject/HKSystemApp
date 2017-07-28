package com.anyi.door.utils.signature;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anyi.door.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NetResponseEvent;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.bean.index.UploadFileResponse;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.ErrorCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.main.base.BaseActivity;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.main.base.HeadView;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.network.UserServiceImpl;
import cn.nj.www.my_module.tools.FileSystemManager;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.ToastUtil;
import de.greenrobot.event.EventBus;

public class HandWriteActivity extends BaseActivity
{

    @Bind(R.id.view)
    LinePathView view;

    @Bind(R.id.clear1)
    Button clear1;

    @Bind(R.id.save1)
    Button save1;

    @Bind(R.id.change)
    Button change;

    @Bind(R.id.changewidth)
    Button changewidth;

    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;

    @Bind(R.id.ll)
    RelativeLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_write);
        ButterKnife.bind(this);
        initTitle();
        setResult(50);
        //设置保存监听
        save1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (view.getTouched())
                {
                    try
                    {
                        view.save(FileSystemManager.getSignaturePath(HandWriteActivity.this), true, 10);
//                        setResult(100);
//                        finish();
                        //此时已经获取到图片了，上传服务器后，将图片的地址传到上级页面
                        NetLoadingDialog.getInstance().loading(mContext, "提交中,请稍后");
                        List<File> files =new ArrayList<File>();
                        File file = new File(FileSystemManager.getSignaturePath(mContext));
                        if (file.exists()){
                            files.add(file);
                            UserServiceImpl.instance().uploadPic(files, UploadFileResponse.class.getName());
                        }

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {

                    Toast.makeText(HandWriteActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view.clear();
            }
        });

    }

    private void initTitle()
    {
        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setTitleText("签名");
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

    }

    @Override
    public void initEvent()
    {

    }

    @Override
    public void netResponse(BaseResponse event)
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

            if (tag.equals(UploadFileResponse.class.getName()) && BaseApplication.currentActivity.equals(this.getClass().getName()))
            {
                if (GeneralUtils.isNotNullOrZeroLenght(result))
                {
                    UploadFileResponse uploadFileResponse = GsonHelper.toType(result, UploadFileResponse.class);
                    if (Constants.SUCESS_CODE.equals(uploadFileResponse.getResultCode()))
                    {
                        EventBus.getDefault().post(new NoticeEvent(NotiTag.TAG_SIGNATURE_URL,uploadFileResponse.getUrl()));
                        Intent intent=new Intent();
                        intent.putExtra("url", uploadFileResponse.getUrl());
                        setResult(100, intent);
                        finish();
                    }
                    else
                    {
                        ErrorCode.doCode(mContext, uploadFileResponse.getResultCode(), uploadFileResponse.getDesc());
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
    protected void onDestroy()
    {

        super.onDestroy();
    }
}
