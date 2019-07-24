package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.util.Page;

import java.util.Map;

public interface UserService {
    Page<User> queryAllUser(Map<String, Object> map);

    String addUser(User user);

    String modifyUser(User user, String opassword);

    void removeUser(String[] ids);

    void modifyProfile(String id, String profileName);

    String modifyStatus(String id, String status);

    String exportUser(String filename, String realPath);

    void importUser(String absolutePath) throws Exception;
}
