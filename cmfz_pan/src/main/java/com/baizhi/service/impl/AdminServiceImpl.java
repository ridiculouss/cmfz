package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    public Map<String, Object> queryAdmin(Admin admin) {
        Map<String, Object> map = new HashMap<>();
        Admin selectedAdmin = adminDao.selectAdmin(admin.getUsername());
        if (selectedAdmin == null){
            map.put("code","100");
            map.put("message", "用户名不存在");
            return map;
        }else if (!selectedAdmin.getPassword().equals(admin.getPassword())){
            map.put("code","101");
            map.put("message", "密码错误");
            return map;
        }
        map.put("admin", selectedAdmin);
        map.put("code","102");
        return map;
    }
}
