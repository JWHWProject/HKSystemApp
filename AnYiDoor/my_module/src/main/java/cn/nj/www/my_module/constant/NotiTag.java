package cn.nj.www.my_module.constant;

import android.content.Context;

import cn.nj.www.my_module.main.base.BaseApplication;


/**
 * Created by Administrator on 2015/11/5.
 */
public class NotiTag
{
    /**
     * 关闭堆栈界面
     */
    public static final String TAG_CLOSE = "close";

    /**
     * 关闭当前界面
     */
    public static final String TAG_CLOSE_ACTIVITY = "close_activity";
    public static final String TAG_CLOSE_ACTIVITY_FROM_DIALOG = "TAG_CLOSE_ACTIVITY_FROM_DIALOG";
    public static final String TAG_COMFIRM_CLOSE = "TAG_COMFIRM_CLOSE";
    public static final String TAG_BACK_ACTIVITY = "TAG_BACK_ACTIVITY";

    /**
     * 错误页面
     */
    public static final String TAG_ERROR_VIEW = "TAG_ERROR_VIEW";
    public static final String TAG_TRAIN_DETAIL = "TAG_ERROR_VIEW";


    /**
     * 右边按钮操作
     */
    public static final String TAG_DO_RIGHT = "do_right";
    /**
     * 右边按钮操作
     */
    public static final String TAG_DLG_OK = "TAG_DLG_OK";
    /**
     * 右边按钮操作
     */
    public static final String TAG_DLG_CANCEL = "TAG_DLG_CANCEL";

    /**
     * 登录成功
     */
    public static final String TAG_LOGIN_SUCCESS = "TAG_LOGIN_SUCCESS";

    public static final String TAG_USER_EXIT_APP = "TAG_USER_EXIT_APP";

    /**
     * 时间选择成功
     */
    public static final String TAG_CHOOSE_TIME_OK = "TAG_CHOOSE_TIME_OK";

    /**
     * webView tag,开始 ,错误 , 结束,刷新
     */
    public static final String TAG_WEB_VIEW_START = "web_view_start";

    public static final String TAG_WEB_VIEW_ERROR = "web_view_error";

    public static final String TAG_WEB_VIEW_FINISH = "web_view_finish";

    public static final String TAG_WEB_VIEW_REFRESH = "web_view_refresh";
    public static final String TAG_DELETE_IMG = "TAG_DELETE_IMG";

    /**
     *  初始化成功
     */
    public static final String TAG_APP_INIT_SUCCESS= "TAG_APP_INIT_SUCCESS";
    public static final String TAG_CANCEL_UPDATE= "TAG_CANCEL_UPDATE";

    public static final String TAG_START_TRAIN_DIALOG= "TAG_START_TRAIN_DIALOG";
    public static final String TAG_START_TEST_DIALOG= "TAG_START_TEST_DIALOG";

    /**
     * 在当前界面操作tag
     */
    public static boolean equalsTags(Context context, String tagOne, String tagTwo)
    {
        return tagOne.equals(tagTwo) && BaseApplication.currentActivity.equals(context.getClass().getName());
    }
}
