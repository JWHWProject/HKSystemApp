package cn.nj.www.my_module.main.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import cn.nj.www.my_module.R;
import cn.nj.www.my_module.bean.BaseResponse;
import cn.nj.www.my_module.bean.NoticeEvent;
import cn.nj.www.my_module.constant.IntentCode;
import cn.nj.www.my_module.constant.NotiTag;
import cn.nj.www.my_module.constant.URLUtil;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.NetLoadingDialog;
import cn.nj.www.my_module.tools.WebViewUtil;


public class CommonWebViewActivity extends BaseActivity
{
    private WebView webView;

    private String title;

    private String url;

    private String mTag;


    private boolean showError;

    private String instruction;

    private boolean clear;

    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_web_view);
        initAll();
    }

    @Override
    public void initView()
    {
        initTitle();
//        NetLoadingDialog.getInstance().loading(this);
        webView = (WebView) findViewById(R.id.common_web_view);
//        WebViewUtil.initWebView(this, webView, "https://www.baidu.com/?tn=47018152_dg");
        CMLog.e("hq",url);
        WebViewUtil.initWebView(this, webView, url);
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

    private void initTitle()
    {
        title = getIntent().getStringExtra(IntentCode.COMMON_WEB_VIEW_TITLE);
        url = getIntent().getStringExtra(IntentCode.COMMON_WEB_VIEW_URL);
        instruction = getIntent().getStringExtra(IntentCode.COMMON_WEB_VIEW_URL_INSTRUCTION);
        mTag = getIntent().getStringExtra(IntentCode.COMMON_WEB_VIEW_URL_TAG);

        if (GeneralUtils.isNullOrZeroLenght(title))
        {
            title = getResources().getString(R.string.app_name);
        }
        if (GeneralUtils.isNullOrZeroLenght(url))
        {
            url = URLUtil.DEFAULT_WEB_URL;
        }

        View view = findViewById(R.id.common_back);
        HeadView headView = new HeadView((ViewGroup) view);
        headView.setLeftImage(R.mipmap.app_title_back);
        headView.setHiddenRight();
        headView.setTitleText(title);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        BaseApplication.urlTag = url;
    }

    @Override
    public void onEventMainThread(BaseResponse event)
    {
        if (event instanceof NoticeEvent)
        {
            String tag = ((NoticeEvent) event).getTag();
            if (NotiTag.TAG_CLOSE_ACTIVITY.equals(tag) && BaseApplication.currentActivity.equals(this.getClass().getName()) && BaseApplication.urlTag.equals(url))
            {
                if (webView.canGoBack())
                {
                    webView.goBack();
                }
                else
                {
                    finish();
                }
            }
            if (NotiTag.TAG_WEB_VIEW_ERROR.equals(tag))
            {
                showError = true;
                NetLoadingDialog.getInstance().dismissDialog();
            }
            if (NotiTag.TAG_WEB_VIEW_START.equals(tag))

            {
                NetLoadingDialog.getInstance().loading(this);
            }
            if (NotiTag.TAG_WEB_VIEW_FINISH.equals(tag))
            {

                NetLoadingDialog.getInstance()
                        .dismissDialog();
                if (clear)
                {
                    webView.clearHistory();
                    clear = false;
                }
            }
            if (NotiTag.TAG_WEB_VIEW_REFRESH.equals(tag) || NotiTag.TAG_ERROR_VIEW.equals(tag))
            {
                clear = true;
                if (BaseApplication.cookieStore != null)
                {
                    WebViewUtil.synCookies(this, url);
                }
                webView.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        webView.clearHistory();
                        NetLoadingDialog.getInstance().loading(mContext);
                        webView.loadUrl(url);// 载入网页
                    }
                }, 500);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        if (webView.canGoBack())
        {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
