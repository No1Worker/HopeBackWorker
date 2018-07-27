package com.no1woker.controller;

import com.no1woker.common.Const;
import com.no1woker.common.ServerResponse;
import com.no1woker.pojo.User;
import com.no1woker.service.IUserService;
import com.no1woker.util.JsonUtil;
import com.no1woker.util.RedisShardedPoolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * User: JavaE
 * Date: 2018/4/3
 * Time: 11:51
 */
@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     *
     * @param phone
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(String phone, String password, HttpSession session) {
        String access_token = session.getId();
        ServerResponse response = iUserService.login(phone, password);
        if (response.isSuccess()) {
            RedisShardedPoolUtil.setEx(access_token, JsonUtil.obj2String(response.getData()), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return ServerResponse.createBySuccess("登录成功", access_token);
    }

    /**
     * 用户登出
     *
     * @param access_token
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(String access_token) {
        RedisShardedPoolUtil.del(access_token);
        return ServerResponse.createBySuccess();
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 检验手机或邮箱
     *
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取用户信息
     *
     * @param access_token
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(String access_token) {

        if (StringUtils.isEmpty(access_token)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(access_token);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);

        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }

    /**
     * 修改用户信息
     *
     * @param access_token
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(String access_token, User user) {
        if (StringUtils.isEmpty(access_token)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(access_token);
        User currentUser = JsonUtil.string2Obj(userJsonStr, User.class);

        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()) {
            response.getData().setUsername(currentUser.getUsername());
            RedisShardedPoolUtil.setEx(access_token, JsonUtil.obj2String(response.getData()), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

}
