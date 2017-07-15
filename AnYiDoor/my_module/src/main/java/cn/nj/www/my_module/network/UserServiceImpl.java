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
