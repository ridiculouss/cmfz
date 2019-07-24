package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    Admin selectAdmin(@Param("username") String username);
}
