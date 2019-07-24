package com.baizhi.service;

import com.baizhi.entity.Guru;
import com.baizhi.util.Page;

import java.util.Map;

public interface GuruService {
    Page<Guru> queryAllGuru(Map<String, Object> map);

    String addGuru(Guru guru);

    void modifyProfile(String id, String profile);

    String modifyStatus(String id, String status);
}
