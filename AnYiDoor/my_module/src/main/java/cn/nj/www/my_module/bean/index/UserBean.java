package cn.nj.www.my_module.bean.index;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2016/7/9.
 */
public class UserBean {
    private String loginName;

    private int userId;

    private String userName;

    private String password;

    private String mobile;

    private String userRole;

    private int type;

    private String captcha;

    private String newPassword;

    private String confirmPassword;


    /**
     * dataId : 1893
     * name : 手机端超标记录
     * menuText : 超标记录
     * url :
     */

    private List<PermInstanceDtosBean> permInstanceDtos;

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getUserRole()
    {
        return userRole;
    }

    public void setUserRole(String userRole)
    {
        this.userRole = userRole;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getCaptcha()
    {
        return captcha;
    }

    public void setCaptcha(String captcha)
    {
        this.captcha = captcha;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }

    public List<PermInstanceDtosBean> getPermInstanceDtos()
    {
        return permInstanceDtos;
    }

    public void setPermInstanceDtos(List<PermInstanceDtosBean> permInstanceDtos)
    {
        this.permInstanceDtos = permInstanceDtos;
    }

    public static class PermInstanceDtosBean implements Serializable
    {

        private int dataId;

        private String name;

        private String menuText;

        private String url;

        public int getDataId()
        {
            return dataId;
        }

        public void setDataId(int dataId)
        {
            this.dataId = dataId;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getMenuText()
        {
            return menuText;
        }

        public void setMenuText(String menuText)
        {
            this.menuText = menuText;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }
    }
}