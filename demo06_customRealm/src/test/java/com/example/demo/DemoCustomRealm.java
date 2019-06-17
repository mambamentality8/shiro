package com.example.demo;

import com.example.demo.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;


/**
 * 单元测试用例执行顺序
 *
 */
public class DemoCustomRealm {

    private CustomRealm customRealm = new CustomRealm();

    private DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();


    @Before
    public void init(){
        //构建环境
        defaultSecurityManager.setRealm(customRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
    }


    @Test
    public void testAuthentication() {

        //获取当前操作的主体

        Subject subject = SecurityUtils.getSubject();

        //用户输入的账号密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jack", "456");

        subject.login(usernamePasswordToken);


        //登录
        System.out.println(" 认证结果:"+subject.isAuthenticated());

        //拿到主体标示属性
        System.out.println(" getPrincipal=" + subject.getPrincipal());

        subject.checkRole("role1");


        System.out.println("是否有对应的角色:"+subject.hasRole("role1"));

        System.out.println("是否有对应的权限:"+subject.isPermitted("video:add"));

    }

}
