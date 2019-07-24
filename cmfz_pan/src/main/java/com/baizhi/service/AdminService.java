package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.Map;

public interface AdminService {
    Map<String, Object> queryAdmin(Admin admin);
}
