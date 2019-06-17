package com.example.demo;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {

    private String getPwdByUserNameFromDB(String name) {

        return userInfoMap.get(name);
    }

    private final Map<String,String> userInfoMap = new HashMap<>();
    {
        userInfoMap.put("test","123");
        userInfoMap.put("jack","456");
    }

    //当用户登陆的时候会调用
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("认证 doGetAuthenticationInfo");

        //从token获取身份信息，token代表用户输入的信息
        String name = (String)token.getPrincipal();

        //模拟从数据库中取密码
        String pwd = getPwdByUserNameFromDB(name);

        if( pwd == null || "".equals(pwd)){
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, pwd, this.getName());

        return simpleAuthenticationInfo;
    }




    /**
     * 模拟从数据库获取用户角色集合
     * @param name
     * @return
     */
    private Set<String> getRolesByNameFromDB(String name) {
        return roleMap.get(name);
    }

    //user -> role
    private final Map<String,Set<String>> roleMap = new HashMap<>();
    {

        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        set1.add("role1");
        set1.add("role2");

        set2.add("root");
        set2.add("admin");

        roleMap.put("jack",set1);
        roleMap.put("test",set2);

    }

    /**
     *  模拟从数据库获取权限集合
     * @param name
     * @return
     */
    private Set<String> getPermissionsByNameFromDB(String name) {
        return permissionMap.get(name);
    }

    //role -> permission
    private final Map<String,Set<String>> permissionMap = new HashMap<>();
    {

        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        set1.add("goods:find");
        set1.add("goods:buy");

        set2.add("goods:add");
        set2.add("goods:delete");

        permissionMap.put("jack",set1);
        permissionMap.put("test",set2);

    }



    //进行权限校验的时候会调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限 doGetAuthorizationInfo");

        //从token获取身份信息，token代表用户输入的信息
        String name = (String)principals.getPrimaryPrincipal();

        //模拟从数据库中取权限
        Set<String> permissions = getPermissionsByNameFromDB(name);

        //模拟从数据库中取角色
        Set<String> roles = getRolesByNameFromDB(name);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.setRoles(roles);

        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }


















}
