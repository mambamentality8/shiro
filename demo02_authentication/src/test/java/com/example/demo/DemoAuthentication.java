package com.example.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class DemoAuthentication {

    private SimpleAccountRealm accountRealm = new SimpleAccountRealm();


    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();


    @Before
    public void init() {

        //初始化数据源
        accountRealm.addAccount("test", "123");

        //构建环境
        defaultSecurityManager.setRealm(accountRealm);

    }

    @Test
    public void testAuthentication() {

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //当前操作主体， application user
        Subject subject = SecurityUtils.getSubject();

        //用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken("test", "123");

        subject.login(usernamePasswordToken);


        System.out.println("认证结果:"+subject.isAuthenticated());


    }

}
