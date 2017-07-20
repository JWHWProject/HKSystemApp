package cn.nj.www.my_module.network;

import cn.nj.www.my_module.bean.BaseResponse;

/**
 * Created by Administrator on 2017/7/19.
 */

public class NetWorkResponse {
    public interface NetCallBack{
        void showCallback(BaseResponse event);
    };
}
