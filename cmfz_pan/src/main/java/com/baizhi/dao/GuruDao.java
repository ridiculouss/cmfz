package com.baizhi.dao;

import com.baizhi.entity.Guru;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GuruDao {
    List<Guru> selectAllGuru(@Param("begin") long begin, @Param("pageSize") int pageSize);

    int selectAllCount();

    int insertGuru(Guru guru);

    void updateWithProfile(Map<String, Object> map);

    int updateWithStatus(Map<String, Object> map);
}
