package com.no1woker.service.impl;


import com.no1woker.common.Const;
import com.no1woker.common.ServerResponse;
import com.no1woker.dao.UserMapper;
import com.no1woker.pojo.User;
import com.no1woker.service.IUserService;
import com.no1woker.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by YangKai on 2017/6/15.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    public ServerResponse login(String phone, String password) {
        int resuleCount = userMapper.checkPhone(phone);
        if (resuleCount > 0) {
            String md5Password = MD5Util.MD5EncodeUtf8(password);
            User user = userMapper.selectLogin(phone, md5Password);
            if (user != null) {
                user.setPassword(StringUtils.EMPTY);
                user.setAnswer(StringUtils.EMPTY);
                return ServerResponse.createBySuccess("登录成功", user);
            } else {
                return ServerResponse.createByErrorMessage("密码错误");
            }
        }
        return ServerResponse.createByErrorMessage("用户不存在");
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = this.checkValid(user.getPhone(), Const.PHONE);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    /**
     * 检验手机号或邮箱或用户名
     *
     * @param str
     * @param type
     * @return
     */
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            //开始校验
            if (Const.PHONE.equals(type)) {
                int resultCount = userMapper.checkPhone(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("手机号已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    public ServerResponse<User> updateInformation(User user) {
        int resuleCount = userMapper.updateByPrimaryKeySelective(user);
        if (resuleCount > 0) {
            return ServerResponse.createBySuccess("修改成功",user);
        }
        return ServerResponse.createBySuccessMessage("修改失败");
    }


}
