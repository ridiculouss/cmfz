package com.baizhi.dao;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {
    List<User> selectAllUser(@Param("begin") long begin, @Param("pageSize") int pageSize);

    int selectAllCount();

    int insertUser(User user);

    int updateUser(User user);

    void deleteUser(@Param("ids") String[] ids);

    void updateWithProfile(Map<String, Object> map);

    int updateWithStatus(Map<String, Object> map);
}
