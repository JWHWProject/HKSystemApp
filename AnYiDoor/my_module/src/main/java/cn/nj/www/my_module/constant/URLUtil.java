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




    public static final String INIT = SERVER_BASE + "app/init";
    public static final String BANNER = SERVER_BASE + "door/banner/list";
    public static final String LOGIN = SERVER_BASE + "user/login";
    public static final String INDEX_DATA = SERVER_BASE + "door/user/stat";
    public static final String GIVE_CARD = SERVER_BASE + "door/user/addCard";
    public static final String GIVE_OUT_CARD = SERVER_BASE + "door/outsiders/addCard";
    public static final String RETURN_CARD = SERVER_BASE + "card/return";
    public static final String TRAINLIST = SERVER_BASE + "onlineTraining/list";
    public static final String TRAIN_CONTENT = SERVER_BASE + "onlineTraining/detail";
    public static final String START_TRAIN = SERVER_BASE + "onlineTraining/start";
    public static final String FINISH_TRAIN = SERVER_BASE + "onlineTraining/finish";
    public static final String TEST_DETAIL = SERVER_BASE + "onlineTrainingExam/detail";
    public static final String ONLINE_TEST = SERVER_BASE + "onlineTrainingExam/start";
    public static final String FINISH_TEST = SERVER_BASE + "onlineTrainingExam/finish";
    public static final String uploaderFile = SERVER_BASE + "onlineTrainingExam/finish";





}
