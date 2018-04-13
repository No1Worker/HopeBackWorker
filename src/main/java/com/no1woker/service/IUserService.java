package com.no1woker.service;


import com.no1woker.common.ServerResponse;
import com.no1woker.pojo.User;

/**
 * Created by YangKai on 2017/6/15.
 */
public interface IUserService {
    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    ServerResponse<User> login(String phone, String password);

    /**
     * 注册
     *
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 检验用户名或邮箱
     *
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    ServerResponse<User> updateInformation(User user);
}
