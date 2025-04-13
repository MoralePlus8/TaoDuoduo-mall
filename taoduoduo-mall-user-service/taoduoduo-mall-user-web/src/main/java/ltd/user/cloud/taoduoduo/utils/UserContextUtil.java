package ltd.user.cloud.taoduoduo.utils;


import ltd.user.cloud.taoduoduo.entity.User;

public class UserContextUtil {

    private UserContextUtil(){}

    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void setUser(User user){
        userThreadLocal.set(user);
    }

    public static User getUser(){
        return userThreadLocal.get();
    }

    public static void removeUser(){
        userThreadLocal.remove();
    }

}
