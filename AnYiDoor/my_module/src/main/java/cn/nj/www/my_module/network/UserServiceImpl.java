package cn.nj.www.my_module.network;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nj.www.my_module.bean.index.OnlineTrainingAnswer;
import cn.nj.www.my_module.constant.Constants;
import cn.nj.www.my_module.constant.Global;
import cn.nj.www.my_module.constant.URLUtil;
import cn.nj.www.my_module.tools.SharePref;

/**
 * 用户相关的接口实现类
 */
public class UserServiceImpl
{
    private UserServiceImpl()
    {
    }

    private static Context mContext;


    private static class UserServiceImplServiceHolder
    {
        private static UserServiceImpl userServiceImplSingleton = new UserServiceImpl();
    }

    public static UserServiceImpl instance()
    {
        return UserServiceImplServiceHolder.userServiceImplSingleton;
    }

    /**
     * 初始化
     *
     * @param tag
     */
    public void init(String gpsLong, String gpsLati, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("lastUpdateTime", SharePref.getString(Constants.LAST_UPDATE_TIME, ""));
        param.put("versionCode", Constants.VERSION_NAME);
        param.put("gpsLong", gpsLong);
        param.put("gpsLati", gpsLati);
        param.put("appFlag", "OnlineTraining");
        new NetWork()
                .startPost(URLUtil.INIT, param, tag);
    }

    public void getBanner(String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork()
                .startPost(URLUtil.BANNER, param, tag);
    }
    public void getUserList(String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork()
                .startPost(URLUtil.USER_LIST, param, tag);
    }

    public void getOuterType(String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork()
                .startPost(URLUtil.outerType, param, tag);
    }

    public void login(String userName, String password, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("userName", userName);
        param.put("password", password);
        param.put("loginType", "1");
        param.put("smsCode", "");
        new NetWork()
                .startPost(URLUtil.LOGIN, param, tag);
    }

    public void indexData(String userName, String password, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork()
                .startPost(URLUtil.INDEX_DATA, param, tag);
    }

    public void giveCard(String cardNo, String name, int gender, String department, String jobNum, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("cardNo", cardNo);
        param.put("name", name);
        param.put("gender", gender + "");
        param.put("department", department);
        param.put("jobNum", jobNum);
        new NetWork()
                .startPost(URLUtil.GIVE_CARD, param, tag);
    }

    public void giveCard(String cardNo, String name, int gender, String phone, String fromCompany, String idCard
            , String outsidersType, String reason, String needTraining, String validTime
            , List<String> urls, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("cardNo", cardNo);
        param.put("name", name);
        param.put("gender", gender + "");
        param.put("phone", phone);
        param.put("fromCompany", fromCompany);
        param.put("idCard", idCard);
        if (!outsidersType.equals(""))
        {
            param.put("outsidersType", outsidersType);
        }
        if (!reason.equals(""))
        {
            param.put("reason", reason);
        }
        if (!needTraining.equals(""))
        {
            param.put("needTraining", needTraining);
        }
        if (!validTime.equals(""))
        {
            param.put("validTime", validTime);
        }
        if (urls != null && urls.size() > 0)
        {
            param.put("picUrlList", urls);
        }
        new NetWork()
                .startPost2(URLUtil.GIVE_OUT_CARD, param, tag);
    }

    /**
     * 上传图片
     */
    public void uploadPic(List<File> files, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        Map<String, List<File>> fileparams = new HashMap<String, List<File>>();
        fileparams.put("file", files);
//        fileparams.put("file", files);
        new NetWork().startPost(URLUtil.UPLOAD_PIC, param, fileparams, tag);
    }
    public void uploadPic(List<File> files, String tag, NetWorkResponse.NetCallBack callBack)
    {
        Map<String, String> param = new HashMap<String, String>();
        Map<String, List<File>> fileparams = new HashMap<String, List<File>>();
        fileparams.put("file", files);
//        fileparams.put("file", files);
        new NetWork().startPost(URLUtil.UPLOAD_PIC, param, fileparams, tag,callBack);
    }
    public void returnCard(String cardNo, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("cardNo", cardNo);
        new NetWork()
                .startPost(URLUtil.RETURN_CARD, param, tag);
    }

    public void trainList(String keyword, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("keyword", keyword);
        new NetWork()
                .startPost(URLUtil.TRAINLIST, param, tag);
    }

    public void trainList(String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork()
                .startPost(URLUtil.TRAINLIST, param, tag);
    }

    public void trainContent(String trainingID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("trainingID", trainingID);
        new NetWork()
                .startPost(URLUtil.TRAIN_CONTENT, param, tag);
    }

    public void startTrain(String trainingID, String cardNo,String userID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("trainingID", trainingID);
        param.put("cardNo", cardNo);
        param.put("userID",userID);
        new NetWork()
                .startPost(URLUtil.START_TRAIN, param, tag);
    }


    public void finishTrain(String recordID, List<String> picUrlList, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("recordID", recordID);
        if (picUrlList != null)
        {
            param.put("picUrlList", picUrlList);
        }
        new NetWork()
                .startPost2(URLUtil.FINISH_TRAIN, param, tag);
    }
    public void finishTrain(String recordID, List<String> picUrlList, String tag, NetWorkResponse.NetCallBack callBack)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("recordID", recordID);
        if (picUrlList != null)
        {
            param.put("picUrlList", picUrlList);
        }
        new NetWork()
                .startPost2(URLUtil.FINISH_TRAIN, param, tag,callBack);
    }
    public void testDetail(String examID, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("trainingID", examID);
        new NetWork()
                .startPost(URLUtil.TEST_DETAIL, param, tag);
    }

    public void startOnlineTest(String trainingID, String cardNo, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("trainingID", trainingID);
        param.put("cardNo", cardNo);
        param.put("userID", Global.getUserId());
        new NetWork()
                .startPost(URLUtil.ONLINE_TEST, param, tag);
    }

    public void finishTest(String examID, List<OnlineTrainingAnswer> answerList, List<String> picUrlList, String tag)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("examID", examID);
        param.put("answerList", answerList);
        param.put("picUrlList", picUrlList);
        new NetWork()
                .startPost2(URLUtil.FINISH_TEST, param, tag);
    }


    /**
     * 上传附件
     */
    public void upLoadFile(String attachmentPacketId, File file, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("sessionUserId", Global.getUserId());//当前用户id
        if (!attachmentPacketId.equals(""))
        {
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
