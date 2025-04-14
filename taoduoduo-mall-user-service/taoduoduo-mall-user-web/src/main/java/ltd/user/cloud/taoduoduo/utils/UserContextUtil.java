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

    public static Long getUserId(){
        User user = userThreadLocal.get();
        if (user != null) {
            return user.getUserId();
        }
        return null;
    }

    public static String getUserName(){
        User user = userThreadLocal.get();
        if (user != null && user.getUsername() != null) {
            return user.getUsername();
        }
        return "";
    }

    public static void removeUser(){
        userThreadLocal.remove();
    }

}
