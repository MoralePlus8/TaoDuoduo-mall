package ltd.common.cloud.taoduoduo.util;

import ltd.common.cloud.taoduoduo.pojo.UserInfo;

public class UserContextUtil {

    private UserContextUtil(){}

    private static final ThreadLocal<UserInfo> userThreadLocal = new ThreadLocal<>();



    public static void setUser(UserInfo userInfo){
        userThreadLocal.set(userInfo);
    }

    public static UserInfo getUser(){
        return userThreadLocal.get();
    }

    public static Long getUserId(){
        UserInfo userInfo = userThreadLocal.get();
        if (userInfo != null) {
            return userInfo.getUserId();
        }
        return null;
    }

    public static String getUserName(){
        UserInfo userInfo = userThreadLocal.get();
        if (userInfo != null && userInfo.getUsername() != null) {
            return userInfo.getUsername();
        }
        return "";
    }

    public static void removeUser(){
        userThreadLocal.remove();
    }

}
