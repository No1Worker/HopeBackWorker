package com.no1woker.dao;

import com.no1woker.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUserName(@Param("username") String username);

    int checkPhone(@Param("phone") String phone);

    int checkEmail(@Param("email") String email);

    User selectLogin(@Param("phone") String phone, @Param("password") String password);
}