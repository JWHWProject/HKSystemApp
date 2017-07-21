package cn.nj.www.my_module.constant;

import android.app.Activity;
import android.content.Context;

import java.util.Date;

import cn.nj.www.my_module.bean.index.LoginResponse;
import cn.nj.www.my_module.main.base.BaseApplication;
import cn.nj.www.my_module.network.GsonHelper;
import cn.nj.www.my_module.tools.CMLog;
import cn.nj.www.my_module.tools.CookieUtils;
import cn.nj.www.my_module.tools.GeneralUtils;
import cn.nj.www.my_module.tools.SharePref;
import cn.nj.www.my_module.tools.StringEncrypt;

/**
 * <全局静态缓存数据>
 * <功能详细描述>
 *
 * @version [版本号, 2015-4-23]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Global
{
    /**
     * 启动引导页
     */
    private static final String IS_NEW_USER = "is_new_user";

    /**
     * app是否处于开启状态
     */
    private static final String OPEN_APP = "open_app";

    /**
     * 用户名
     */
    private static final String LOGIN_USERNAME = "login_username";

    /**
     * 用户唯一标识，数据局中自增长序列
     */
    private static final String USERID = "userID";

    /**
     * 用户类型
     * 1-老师；
     * 2-家长
     */
    private static final String USERTYPE = "userType";

    /**
     * 密码
     */
    private static final String PASSWORD = "password";
    private static final String COMPANYID = "COMPANYID";

    private static final String LOGIN_DATA = "LOGIN_DATA";

    /**
     * 昵称
     */
    private static final String NICKNAME = "nickName";

    /**
     * 头像
     */
    private static final String PORTRAIT = "portrait";

    /**
     * 手机号
     */
    private static final String PHONE = "phone";


    /**
     * 状态
     * 1-待审核；2-审核通过
     * 当userType为2时有效
     */
    private static final String STATUS = "status";

    /**
     * 创建时间
     */
    private static final String CREATETIMESTR = "createTimeStr";


    /**
     * 登录名
     */
    private static final String LOGIN_NAME = "LOGIN_NAME";


    private static String PAGE_INDEX = "PAGE_INDEX";

    /**
     * 七牛上传图片
     */
    public static String uptoken = "uptoken";

    public static String home_fragment_index = "home_fragment_index";

    /**
     * 上传图片的token
     */
    public static String getToken()
    {
        return SharePref.getString(uptoken, "");
    }

    public static void saveToken(String upToken)
    {
        SharePref.saveString(uptoken, upToken);
    }

    /**
     * 首页目前展示的Fragment
     */
    public static String getNowIndex()
    {
        return SharePref.getString(home_fragment_index, "");
    }

    public static void saveNowIndex(String upToken)
    {
        SharePref.saveString(home_fragment_index, upToken);
    }


    /**
     * 是否启动引导页
     */
    public static int getUserGuide()
    {
        return SharePref.getInt(IS_NEW_USER, -1);
    }

    public static void saveUserGuide(int newUser)
    {
        SharePref.saveInt(IS_NEW_USER, newUser);
    }

    /**
     * 是否启动引导页
     */
    public static int getPageIndex()
    {
        return SharePref.getInt(PAGE_INDEX, 1);
    }

    public static void savePageIndex(int orderId)
    {
        SharePref.saveInt(PAGE_INDEX, orderId);
    }


    /**
     * 获取登录名
     */
    public static String getLoginName()
    {
        return SharePref.getString(LOGIN_NAME, "");
    }

    public static void saveLoginName(String username)
    {
        SharePref.saveString(LOGIN_NAME, username);
    }


    /**
     * app是否处于开启状态
     */
    public static boolean getOpenApp()
    {
        return SharePref.getBoolean(OPEN_APP, false);
    }

    public static void saveOpenApp(boolean openApp)
    {
        SharePref.saveBoolean(OPEN_APP, openApp);
    }


    public static void saveLoginUserData(Context context, String result)
    {
        LoginResponse loginResponse = GsonHelper.toType(result, LoginResponse.class);
        LoginResponse.UserBean userBean = loginResponse.getUser();
        saveUserName(userBean.getUserName());
        saveUserId(userBean.getUserID() + "");
        savePassword(userBean.getPassword());
        saveCompanyId(userBean.getCompanyID());
        saveloginData(result);
    }


    /**
     * 获取密码
     */
    public static String getXS_PASSWORD_WORD()
    {
        return SharePref.getString(XS_PASSWORD_WORD, "");
    }

    public static void saveXS_PASSWORD_WORD(String username)
    {
        SharePref.saveString(XS_PASSWORD_WORD, username);
    }

    /**
     * 客户端标识密码（x-s-password），加密时使用明文
     */
    private static final String XS_PASSWORD_WORD = "XS_PASSWORD_WORD";

    /**
     * 未加密字符串
     */
    private static String XS_SECURITY = "";

    public static String getXSSecurity()
    {
        if (GeneralUtils.isNullOrZeroLenght(XS_SECURITY))
        {
            String part1 = "webContent";
            String part2 = BaseApplication.DEVICE_TOKEN;
            String part3 = getXS_PASSWORD_WORD();
            String part4 = GeneralUtils.formatDate(new Date(), GeneralUtils.DATE_PATTERN);
            String part5 = getLoginName();
            XS_SECURITY = StringEncrypt.Encrypt(part1 + part2 + part3 + part4 + part5);
        }
        return XS_SECURITY;
    }

    /**
     * 获取用户名
     */
    public static String getUserName()
    {
        return SharePref.getString(LOGIN_USERNAME, "");
    }

    public static void saveUserName(String username)
    {
        SharePref.saveString(LOGIN_USERNAME, username);
    }

    /**
     * 用户唯一标识，数据局中自增长序列
     */
    public static String getUserId()
    {
        return SharePref.getString(USERID, "");
    }

    public static void saveUserId(String username)
    {
        SharePref.saveString(USERID, username);
    }


    /**
     *
     */
    public static String getuserType()
    {
        return SharePref.getString(USERTYPE, "");
    }

    public static void saveuserType(String username)
    {
        SharePref.saveString(USERTYPE, username);
    }

    /**
     * 密码
     */
    public static String getPassword()
    {
        return SharePref.getString(PASSWORD, "");
    }

    public static void savePassword(String username)
    {

        SharePref.saveString(PASSWORD, username);
        saveXS_PASSWORD_WORD(StringEncrypt.Encrypt(username));
    }
    /**
     * 密码
     */
    public static String getCompanyId()
    {
        return SharePref.getString(COMPANYID, "");
    }

    public static void saveCompanyId(String username)
    {

        SharePref.saveString(COMPANYID, username);
        saveXS_PASSWORD_WORD(StringEncrypt.Encrypt(username));
    }

    /**
     * 密码
     */
    public static String getLoginData()
    {
        return SharePref.getString(LOGIN_DATA, "");
    }

    public static void saveloginData(String username)
    {

        SharePref.saveString(LOGIN_DATA, username);
    }

    /**
     * 昵称
     */
    public static String getUserRole()
    {
        return SharePref.getString(NICKNAME, "");
    }

    public static void saveUserRole(String username)
    {
        SharePref.saveString(NICKNAME, username);
    }

    /**
     * 头像
     */
    public static String getcaptcha()
    {
        return SharePref.getString(PORTRAIT, "");
    }

    public static void savecaptcha(String username)
    {
        SharePref.saveString(PORTRAIT, username);
    }

    /**
     * 电话
     */
    public static String getPhone()
    {
        return SharePref.getString(PHONE, "");
    }

    public static void savePhone(String username)
    {
        SharePref.saveString(PHONE, username);
    }


    /**
     * 创建时间 yyyyMMddDDMMSS
     */
    public static String getpermInstanceDtos()
    {
        return SharePref.getString(CREATETIMESTR, "");
    }

    public static void savepermInstanceDtos(String username)
    {
        SharePref.saveString(CREATETIMESTR, username);
    }

    /**
     * <注销登录>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public static void loginOut(Context context)
    {
        if (BaseApplication.cookieStore != null)
        {
            BaseApplication.cookieStore.clear();
            CookieUtils.getInstance(context).saveCookies(BaseApplication.cookieStore);
        }
        saveUserName("");
        saveUserId("");
        saveLoginName("");
        saveuserType("");
        savePassword("");
        saveUserRole("");
        savecaptcha("");
        saveCompanyId("");
        saveloginData("");
    }

    /**
     * <退出应用>
     */
    public static void logoutApplication()
    {
        Global.saveOpenApp(false);
        try
        {
            for (Activity activity : BaseApplication.activitys)
            {
                activity.finish();
            }
        } catch (Exception e)
        {
            CMLog.e("", "finish activity exception:" + e.getMessage());
        } finally
        {
//            ImageLoader.getInstance().clearMemoryCache();
            System.exit(0);
        }
    }


    public static void savelangitude(String name)
    {
        SharePref.saveString(Constants.LANGITUDE, name);
    }

    public static String getlangitude()
    {
        return SharePref.getString(Constants.LANGITUDE, "");
    }

    public static void savelatitude(String name)
    {
        SharePref.saveString(Constants.LATITUDE, name);
    }

    public static String getlatitude()
    {
        return SharePref.getString(Constants.LATITUDE, "");
    }


}
