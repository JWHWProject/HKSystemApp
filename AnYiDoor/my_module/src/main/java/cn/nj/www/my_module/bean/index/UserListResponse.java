package cn.nj.www.my_module.bean.index;

import java.util.List;

import cn.nj.www.my_module.bean.BaseResponse;

/**
 * Created by huqing on 2017/7/20.
 */

public class UserListResponse extends BaseResponse
{

    /**
     * userID : 5239
     * userName : 12345678999
     * nickName : 测试平台人员
     */

    private List<UserListBean> userList;

    public List<UserListBean> getUserList()
    {
        return userList;
    }

    public void setUserList(List<UserListBean> userList)
    {
        this.userList = userList;
    }

    public static class UserListBean
    {
        private String userID;

        private String userName;

        private String nickName;

        public String getUserID()
        {
            return userID;
        }

        public void setUserID(String userID)
        {
            this.userID = userID;
        }

        public String getUserName()
        {
            return userName;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
        }

        public String getNickName()
        {
            return nickName;
        }

        public void setNickName(String nickName)
        {
            this.nickName = nickName;
        }
    }
}
