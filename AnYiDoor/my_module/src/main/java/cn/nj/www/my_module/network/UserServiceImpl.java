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


    public void update(String username,  String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("oldVersion", username);
        new NetWork()
                .startPost(URLUtil.UPDATE_VERSION, param, tag);
    }
    public void LoginApp(String username, String password, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("loginName", username);
        param.put("password", password);
        param.put("type", "1");
        new NetWork()
                .startPost(URLUtil.LOGIN, param, tag);
    }

    public void banner(String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        new NetWork()
                .startPost(URLUtil.BANNER, param, tag);
    }

    public void listVideoNode(String id,String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("enterpriseId",id);
        new NetWork()
                .startPost(URLUtil.listVideoNode, param, tag);
    }


    /**
     * 3.5.单位信息接口
     */
    public void getInstitutionInfo(String sessionUserId, String type, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("sessionUserId", sessionUserId);//当前登录人Id
        param.put("type", type);//1：监管人员 2：其他人员（电厂，填埋，运输等）
        new NetWork()
                .startPost(URLUtil.INSTITUTION_INFO, param, tag);
    }
    /**
     * 3.5.下拉单位信息接口
     */
    public void getInstitutionInfo2(String sessionUserId, String type, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("sessionUserId", sessionUserId);//当前登录人Id
        param.put("type", type);//1：监管人员 2：其他人员（电厂，填埋，运输等）
        param.put("enterpriseType", "1");
        new NetWork()
                .startPost(URLUtil.INSTITUTION_INFO, param, tag);
    }
    /**
     * 3.6.指标查询接口
     */
    public void getTargetInfo(String enterpriseId,String type
//            , String codeList, String startTime, String endTime, boolean isCurrent
        , String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("enterpriseId",enterpriseId); //电厂
        param.put("type", type);//2 二噁英  3 烟气

//        param.put("codeList", codeList);//String 测点/指标分类编码，逗号隔开
//        param.put("startTime", startTime);//calendar 查询开始时间
//        param.put("endTime", endTime);//查询结束时间  非必选
//        param.put("isCurrent", isCurrent + "");//boolean 是否查询最新数据
        new NetWork()
                .startPost(URLUtil.TARGET_INFO, param, tag);
    }
    /**
     * 3.6.指标查询接口烟气
     */
    public void getTargetInfoYQ(String enterpriseId
//            ,String type
            , String codeList
//            , String startTime, String endTime, boolean isCurrent
            , String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("enterpriseId",enterpriseId);
//        param.put("type", type);//查询数据类型1：测点数据2：二噁英数据3：指标数据
        param.put("codeList", codeList);//String 测点/指标分类编码，逗号隔开
//        param.put("startTime", startTime);//calendar 查询开始时间
//        param.put("endTime", endTime);//查询结束时间  非必选
//        param.put("isCurrent", isCurrent + "");//boolean 是否查询最新数据
        new NetWork()
                .startPost(URLUtil.TARGET_INFO2, param, tag);
    }
    /**
     * 3.6.指标查询接口二恶英
     */
    public void getTargetInfoEEY(String enterpriseId
//            ,String type
//            , String codeList
            , String startTime
// , String endTime, boolean isCurrent
            , String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("enterpriseId",enterpriseId);
//        param.put("type", type);//查询数据类型1：测点数据2：二噁英数据3：指标数据
//        param.put("codeList", codeList);//String 测点/指标分类编码，逗号隔开
        param.put("statDateNum", startTime.replace("-",""));//calendar 查询开始时间
//        param.put("endTime", endTime);//查询结束时间  非必选
//        param.put("isCurrent", isCurrent + "");//boolean 是否查询最新数据
        new NetWork()
                .startPost(URLUtil.TARGET_INFO3, param, tag);
    }
    /**
     * 3.7.超标记录接口
     */
    public void getOverRecord(String enterpriseId, String type, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("enterpriseId", enterpriseId);//long 当前默认垃圾发电厂
        param.put("type", type);//1:二噁英/2:烟气类型
        new NetWork()
                .startPost(URLUtil.OVER_RECORD, param, tag);
    }

    /**
     * 3.8.超标统计接口
     */
    public void getOverRecordTotal(String enterpriseId, String type, String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("enterpriseId", enterpriseId);//long 当前默认垃圾发电厂
        param.put("type", type);//1:二噁英/2:烟气类型
        new NetWork()
                .startPost(URLUtil.OVER_RECORD_HISTORY, param, tag);
    }

    /**
     * 3.9.问题登记接口
     */
    public void upLoadProblem(String enterpriseId, String problemDec, String discoveryTime, String durationTime, String solution,
                              String imagePacketId, String assessItemInsId, String score,
                              String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("enterpriseId", enterpriseId);//long 当前默认垃圾发电厂
        param.put("problemDec", problemDec);//String  问题描述
        param.put("discoveryTime", discoveryTime);//String  发现世界
        param.put("durationTime", durationTime);//String  发现世界
        param.put("solution", solution);//String  解决方案
        param.put("imagePacketId", imagePacketId);//long  附件包
        param.put("assessItemInsId", assessItemInsId);//long  考核项目类型
        param.put("score", score);//long  新打分
        new NetWork()
                .startPost(URLUtil.UPLOAD_PROBLEM, param, tag);
    }

    /**
     * 3.10.历史登记查询接口
     */
    public void problemHistory(String createUserId,String page,String num,
                               String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("sessionUserId", createUserId);//创建人Id
        param.put("needsPaginate", "true");//需要分页
        param.put("startPos", page);//页数
        param.put("amount", num);//一页多少个
        new NetWork()
                .startPost(URLUtil.PROBLEM_HISTORY, param, tag);
    }

    /**
     * 3.11.考核项查询接口
     */
    public void testProjects(String type,String enterpriseId, String assessTempId, String assessTime,
                             String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("type", type);//考核电厂id
        if(type.equals("2")){
            param.put("enterpriseId", enterpriseId);//考核电厂id
            param.put("templateId", assessTempId);//考核模板id
            param.put("assessTime", assessTime);//考核模板id
        }
        new NetWork()
                .startPost(URLUtil.TEST_PROJECT, param, tag);
    }

    /**
     * 3.12.监管考核上报接口
     */
    public void uploadTestProjects(String enterpriseId
            ,String problemDec
            ,String discoveryTime
            ,String durationTime
            ,String solution
            ,String opinions
            ,String imagePacketId
            ,String assessItemInsId
            ,String score,String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("sessionUserId", Global.getUserId());
        param.put("enterpriseId", enterpriseId);
        param.put("problemDec", problemDec);
        param.put("discoveryTime", discoveryTime);
        param.put("durationTime", durationTime);
        param.put("solution", solution);
        param.put("opinions", opinions);
        param.put("imagePacketId", imagePacketId);
        if(!assessItemInsId.equals("")) {
            param.put("assessItemInsId", assessItemInsId);
            param.put("score", score);
        }
        new NetWork()
                .startPost(URLUtil.uploadTestProjects, param, tag);
    }

    /**
     * 3.14.修改用户信息（用户名，密码）接口
     */
    public void editUserInfo(String password,
                             String newPwd,
                             String type,
                             String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("userName", Global.getLoginName());//
        param.put("password", password);//
        param.put("sessionUserId", Global.getUserId());//当前用户id
        param.put("hisPwd", password);//
        param.put("newPwd", newPwd);//
        param.put("repeatPwd", newPwd);//
        param.put("type", type);//android终端的请求方式。1代表用户名密码登录；2代表使用手机号、验证码登录；3代表修改密码；4代表绑定手机号
        new NetWork()
                .startPost(URLUtil.editUserInfo, param, tag);
    }
    /**
     * 3.14.修改用户信息（用户名，密码）接口
     */
    public void editUserInfoName(String userName,String loginName,
                             String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("userName", userName);//
        param.put("loginName", loginName);//
        param.put("password", Global.getPassword());//
        param.put("sessionUserId", Global.getUserId());//当前用户id
        param.put("hisPwd", Global.getPassword());//
        param.put("newPwd", Global.getPassword());//
        param.put("repeatPwd", Global.getPassword());//
        param.put("type", "5");//android终端的请求方式。1代表用户名密码登录；2代表使用手机号、验证码登录；3代表修改密码；4代表绑定手机号
        new NetWork()
                .startPost(URLUtil.editUserInfo, param, tag);
    }
    /**
     * 3.14.修改用户信息（用户名，密码）接口
     */
    public void editUserInfo2(String userName,
                              String loginName,
                             String tag)
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("userName", userName);//
        param.put("loginName",loginName);
        param.put("password", Global.getPassword());//
        param.put("sessionUserId", Global.getUserId());//当前用户id
        param.put("hisPwd", Global.getPassword());//
        param.put("newPwd", Global.getPassword());//
        param.put("repeatPwd", Global.getPassword());//
        param.put("type", Global.getuserType());//android终端的请求方式。1代表用户名密码登录；2代表使用手机号、验证码登录；3代表修改密码；4代表绑定手机号
        new NetWork()
                .startPost(URLUtil.editUserInfo, param, tag);
    }
    /**
     * 上传附件
     */
    public void upLoadFile(String attachmentPacketId,File file, String tag){
        Map<String, String> param = new HashMap<String, String>();
        param.put("sessionUserId", Global.getUserId());//当前用户id
        if(!attachmentPacketId.equals("")) {
            param.put("attachmentPacketId", attachmentPacketId);
        }
        Map<String, List<File>> fileparams = new HashMap<String, List<File>>();
        List<File> files= new ArrayList<>();
        files.add(file);
        fileparams.put("file",files);
        new NetWork()
                .startPost(URLUtil.uploaderFile, param,fileparams, tag);
    }
    /**
     * 上传附件
     */
    public void upLoadFileList(String attachmentPacketId, String tag){
        Map<String, String> param = new HashMap<String, String>();
        param.put("attachmentPacketId", attachmentPacketId);
        new NetWork()
                .startPost(URLUtil.uploaderFileList, param, tag);
    }
    /**
     * 上传附件
     */
    public void deleteFile(String attachmentId, String tag){
        Map<String, String> param = new HashMap<String, String>();
        param.put("attachmentId", attachmentId);
        new NetWork()
                .startPost(URLUtil.deleteFile, param, tag);
    }
}
