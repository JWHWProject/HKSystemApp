package cn.nj.www.my_module.bean;

/**
 * <网络请求返回体> <功能详细描述>
 *
 * @author wangtao
 * @version [版本号, 2014-3-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BaseResponse
{
    /**
     * 返回状态
     */
    private boolean success;
    /**
     * 返回信息
     */
    private String message;

    public boolean getResultCode() {
      return success;
    }

    public void setResultCode(boolean resultCode) {
        this.success = resultCode;
    }

    public String getDesc() {
        return message;
    }

    public void setDesc(String desc) {
        this.message = desc;
    }
}
