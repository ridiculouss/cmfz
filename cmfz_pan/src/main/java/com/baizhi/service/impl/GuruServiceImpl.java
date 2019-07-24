package com.baizhi.service.impl;

import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import com.baizhi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {

    @Autowired
    private GuruDao guruDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Guru> queryAllGuru(Map<String, Object> map) {
        Integer rowNum = Integer.valueOf(map.get("rowNum").toString());
        Integer currentPage = Integer.valueOf(map.get("page").toString());
        Page<Guru> page = new Page<>();
        page.setPageSize(rowNum);
        page.setCurrentPage(currentPage);
        List<Guru> gurus = guruDao.selectAllGuru(page.getBegin(), page.getPageSize());
        int count = guruDao.selectAllCount();
        page.setResult(gurus);
        page.setTotal(count);
        return page;
    }

    @Override
    public String addGuru(Guru guru) {
        String id = UUID.randomUUID().toString();
        guru.setId(id);
        int i = guruDao.insertGuru(guru);
        if (i != 0) return id;
        return null;
    }

    @Override
    public void modifyProfile(String id, String profile) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("value", profile);
        guruDao.updateWithProfile(map);
    }

    @Override
    public String modifyStatus(String id, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        if ("on".equals(status)) status = "off";
        else if ("off".equals(status)) status = "on";
        map.put("value", status);
        int i = guruDao.updateWithStatus(map);
        if (i != 0) return "true";
        return null;
    }

}
