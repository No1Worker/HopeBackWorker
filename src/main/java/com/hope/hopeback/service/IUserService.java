package com.hope.hopeback.service;

import com.hope.hopeback.common.ServerResponse;
import com.hope.hopeback.pojo.User;

/**
 * Created by YangKai on 2017/6/15.
 */
public interface IUserService {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return User
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 注册
     *
     * @param user 用户信息
     * @return String
     */
    ServerResponse<String> register(User user);

    /**
     * 检验用户名或邮箱
     *
     * @param str  校验字符串
     * @param type 校验类型
     * @return String
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 找回密码-问题
     *
     * @param username 用户名
     * @return String
     */
    ServerResponse selectQuestion(String username);

    /**
     * 找回密码-答案校验
     *
     * @param username 用户名
     * @param question 校验问题
     * @param answer   校验答案
     * @return String
     */
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 找回密码-修改密码
     *
     * @param username    用户名
     * @param passwordNew 新密码
     * @param forgetToken token
     * @return String
     */
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 在线修改密码
     *
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @param user        用户信息
     * @return String
     */
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return User
     */
    ServerResponse<User> updateInformation(User user);

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return User
     */
    ServerResponse<User> getInformation(Integer userId);
}
