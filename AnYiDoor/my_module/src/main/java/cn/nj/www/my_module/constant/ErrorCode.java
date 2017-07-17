package cn.nj.www.my_module.constant;

import android.content.Context;

import cn.nj.www.my_module.tools.ToastUtil;


/**
 * Created by Administrator on 2015/11/5.
 */
public class ErrorCode {


    /**
     * 必选参数为空
     */
    public static String LACK_PARAM = "200001";
    /**
     * 参数格式错误
     */
    public static String PARAM_FORMAT_ERROR = "200002";
    /**
     * 请求头参数格式不正确
     */
    public static String PARAM_HEAD_ERROR = "200003";


    public static void doCode(Context context,  String info) {
        ToastUtil.makeText(context, info);
    }


    public static void doCode(Context context, String code, String info) {
            ToastUtil.makeText(context, info);
    }
    public static void doCode(Context context, String code, String info, boolean close) {

    }
}
