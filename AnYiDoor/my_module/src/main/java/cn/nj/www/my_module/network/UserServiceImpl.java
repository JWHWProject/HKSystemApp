package cn.nj.www.my_module.network;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.URLUtil;

/**
 * 用户相关的接口实现类
 */
public class UserServiceImpl {
    private UserServiceImpl() {
    }

    private static Context mContext;


    private static class UserServiceImplServiceHolder {
        private static UserServiceImpl userServiceImplSingleton = new UserServiceImpl();
    }

    public static UserServiceImpl instance() {
        return UserServiceImplServiceHolder.userServiceImplSingleton;
    }

    /**
     * 初始化
     *
     * @param lastUpdateTime
     * @param tag
     */
    public void init(String lastUpdateTime, String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("lastUpdateTime", lastUpdateTime);
        param.put("versionCode", "1");
        param.put("gpsLong", "");
        param.put("gpsLati", "");
        new NetWork()
                .startPost(URLUtil.INIT, param, tag);
    }

    public void getBanner(String tag) {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork()
                .startPost(URLUtil.BANNER, param, tag);
    }

    public void login(String userName, String password, String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("userName", userName);
        param.put("password", password);
        param.put("loginType", "1");
        new NetWork()
                .startPost(URLUtil.LOGIN, param, tag);
    }

    public void indexData(String userName, String password, String tag) {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork()
                .startPost(URLUtil.INDEX_DATA, param, tag);
    }

    public void giveCard(String cardNo, String name, int gender, String department,String jobNum,String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("cardNo", cardNo);
        param.put("name", name);
        param.put("gender", gender+"");
        param.put("department", department);
        param.put("jobNum", jobNum);
        new NetWork()
                .startPost(URLUtil.GIVE_CARD, param, tag);
    }

    public void giveCard(String cardNo, String name, int gender, String phone,String fromCompany,String idCard,String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("cardNo", cardNo);
        param.put("name", name);
        param.put("gender", gender+"");
        param.put("phone", phone);
        param.put("fromCompany", fromCompany);
        param.put("idCard", idCard);
        new NetWork()
                .startPost(URLUtil.GIVE_OUT_CARD, param, tag);
    }
    public void returnCard(String cardNo, String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("cardNo", cardNo);
        new NetWork()
                .startPost(URLUtil.RETURN_CARD, param, tag);
    }
    public void trainList(String keyword,String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("keyword", keyword);
        new NetWork()
                .startPost(URLUtil.TRAINLIST, param, tag);
    }
    public void trainList(String tag) {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork()
                .startPost(URLUtil.TRAINLIST, param, tag);
    }
    public void trainContent(String trainingID,String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("trainingID", trainingID);
        new NetWork()
                .startPost(URLUtil.TRAIN_CONTENT, param, tag);
    }
    public void startTrain(String trainingID,String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("trainingID", trainingID);
        new NetWork()
                .startPost(URLUtil.START_TRAIN, param, tag);
    }
    public void finishTrain(String trainingID,String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("trainingID", trainingID);
        new NetWork()
                .startPost(URLUtil.FINISH_TRAIN, param, tag);
    }
    public void testDetail(String examID,String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("examID", examID);
        new NetWork()
                .startPost(URLUtil.TEST_DETAIL, param, tag);
    }
    public void startOnlineTest(String examID,String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("examID", examID);
        new NetWork()
                .startPost(URLUtil.ONLINE_TEST, param, tag);
    }
    public void finishTest(String examID,String answerList,String picUrlList,String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("examID", examID);
        param.put("answerList", answerList);
        param.put("picUrlList", picUrlList);
        new NetWork()
                .startPost(URLUtil.FINISH_TEST, param, tag);
    }


    /**
     * 上传附件
     */
    public void upLoadFile(String attachmentPacketId, File file, String tag) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("sessionUserId", Global.getUserId());//当前用户id
        if (!attachmentPacketId.equals("")) {
            param.put("attachmentPacketId", attachmentPacketId);
        }
        Map<String, List<File>> fileparams = new HashMap<String, List<File>>();
        List<File> files = new ArrayList<>();
        files.add(file);
        fileparams.put("file", files);
        new NetWork()
                .startPost(URLUtil.uploaderFile, param, fileparams, tag);
    }


}
