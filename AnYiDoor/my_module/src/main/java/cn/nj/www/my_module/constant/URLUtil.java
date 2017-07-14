package cn.nj.www.my_module.constant;

public class URLUtil {
    /**
     * 测试环境
     */
    public static final String SERVER_BASE = "http://112.53.156.10:9999/gmis/androidMgmt/";
    public static final String IMAGE_BASE = "http://112.53.156.10:9999/gmis/";

    //    public static final String SERVER_BASE = "http://snmis.xicp.net:8989/gmis/androidMgmt/";
//    public static final String IMAGE_BASE = "http://snmis.xicp.net:8989/gmis/";
    public static final String IMAGE_BASE2 = "http://112.53.156.10:9999/";


    /**
     * 默认url
     */
    public static final String DEFAULT_WEB_URL = "http://baidu.com/";

    /************************************************************************************/
    /*******************************    项目接口    *************************************/
    /************************************************************************************/


    /**
     * 登录
     */
    public static final String LOGIN = SERVER_BASE + "login.do";
    public static final String UPDATE_VERSION = SERVER_BASE + "updateVersion.do";

    /**
     *
     */
    public static final String BANNER = SERVER_BASE + "listBannerImage.do";
    public static final String listVideoNode = SERVER_BASE + "listVideoNode.do";
    /**
     * 3.5.单位信息接口
     */
    public static final String INSTITUTION_INFO = SERVER_BASE + "listEnterprise.do";
    /**
     * 3.6.指标查询接口
     */
    public static final String TARGET_INFO = SERVER_BASE + "listIndicatorData.do";
    /**
     * 3.6.指标查询接口线性图表烟气
     */
    public static final String TARGET_INFO2 = SERVER_BASE + "listIndicatorData2Charts.do";
    /**
     * 3.6.指标查询接口线性图表二恶英
     */
    public static final String TARGET_INFO3 = SERVER_BASE + "listDioxinData2Charts.do";
    /**
     * 3.7.超标记录接口
     */
    public static final String OVER_RECORD = SERVER_BASE + "listExceedRecord.do";
    /**
     * 3.8.超标统计接口
     */
    public static final String OVER_RECORD_HISTORY = SERVER_BASE + "listExceedRecordForCharts.do";
    /**
     * 3.9.问题登记接口
     */
    public static final String UPLOAD_PROBLEM = SERVER_BASE + "assessmentRegister.do";
    /**
     * 3.10.历史登记查询接口
     */
    public static final String PROBLEM_HISTORY = SERVER_BASE + "listHistoryRegister.do";
    /**
     * 3.11.考核项查询接口
     */
    public static final String TEST_PROJECT = SERVER_BASE + "listAssessItemObject.do";
    /**
     * 3.12.监管考核上报接口
     */
    public static final String uploadTestProjects = SERVER_BASE + "assessmentRegister.do";
    /**
     * 3.14.修改用户信息（用户名，密码）接口
     */
    public static final String editUserInfo = SERVER_BASE + "changeUserInfo.do";
    /**
     * 上传附件
     */
    public static final String uploaderFile = SERVER_BASE + "uploadAttachments.do";
    /**
     * 上传附件后列表
     */
    public static final String uploaderFileList = SERVER_BASE + "listAttachments.do";
    /**
     * 删除附件
     */
    public static final String deleteFile = SERVER_BASE + "deleteAttachment.do";
}
