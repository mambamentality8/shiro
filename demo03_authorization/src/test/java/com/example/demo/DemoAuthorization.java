package com.example.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class DemoAuthorization {
    //相当于操作数据库的对象类似jdbc
    private SimpleAccountRealm accountRealm = new SimpleAccountRealm();


    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();


    @Before
    public void init() {

        //初始化数据源
        accountRealm.addAccount("test", "123","admin");
        accountRealm.addAccount("jack", "456","user");

        //构建环境
        defaultSecurityManager.setRealm(accountRealm);

    }

    @Test
    public void testAuthentication() {


        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //当前操作主体，可以理解为登录应用
        Subject subject = SecurityUtils.getSubject();

        //用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jack", "456");

        //使用用户输入的账户进行登录操作
        subject.login(usernamePasswordToken);

        //认证,用来判断是否登录成功
        System.out.println(" 认证结果:"+subject.isAuthenticated());

        //用来判断此用户是否有user角色
        System.out.println(" 是否有对应的admin角色:"+subject.hasRole("admin"));

        //获取此用户的账号
        System.out.println(" getPrincipal=" + subject.getPrincipal());
        subject.getPrincipals().getPrimaryPrincipal();

        //获取此用户的账号
        subject.checkRole("user");

        //将此用户退出
        subject.logout();

        System.out.println("logout后认证结果:"+subject.isAuthenticated());

    }

}
