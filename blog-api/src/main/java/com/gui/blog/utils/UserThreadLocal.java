package com.gui.blog.utils;


import com.gui.blog.dao.pojo.SysUser;

public class UserThreadLocal {
    //线程变量隔离
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<SysUser>();

    private UserThreadLocal(){}

    public static void set(SysUser user){
        LOCAL.set(user);
    }

    public static SysUser get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
