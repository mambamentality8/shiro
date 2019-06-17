package com.example.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class DemoIniRealm {


    @Test
   public void testAuthentication(){
       //创建SecurityManager工厂，通过配置文件ini创建
       Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

       SecurityManager securityManager = factory.getInstance();

       //将securityManager 设置到当前运行环境中
       SecurityUtils.setSecurityManager(securityManager);

       Subject subject = SecurityUtils.getSubject();

       //用户输入的账号密码
       UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jack", "456");

       subject.login(usernamePasswordToken);


       System.out.println(" 认证结果:"+subject.isAuthenticated());

       System.out.println(" 是否有对应的user角色:"+subject.hasRole("user"));

       System.out.println(" getPrincipal=" + subject.getPrincipal());

       subject.checkRole("user");

       subject.checkPermission("goods:find");

       System.out.println( "是否有goods:find 权限："+ subject.isPermitted("goods:find"));


       subject.logout();

       System.out.println("logout后认证结果:"+subject.isAuthenticated());
   }
}
