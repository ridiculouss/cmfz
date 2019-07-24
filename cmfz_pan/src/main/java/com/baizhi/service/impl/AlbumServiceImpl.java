package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
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
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Album> queryAllAlbum(Map<String, Object> map) {
        Integer rowNum = Integer.valueOf(map.get("rowNum").toString());
        Integer currentPage = Integer.valueOf(map.get("page").toString());
        Page<Album> page = new Page<>();
        page.setPageSize(rowNum);
        page.setCurrentPage(currentPage);
        List<Album> albums = albumDao.selectAllAlbum(page.getBegin(), page.getPageSize());
        int count = albumDao.selectAllCount();
        page.setResult(albums);
        page.setTotal(count);
        return page;
    }

    @Override
    public String addAlbum(Album album) {
        String id = UUID.randomUUID().toString();
        album.setId(id);
        int i = albumDao.insertAlbum(album);
        if (i != 0) return id;
        return null;
    }

    @Override
    public String modifyAlbum(Album album) {
        String s = null;
        int i = albumDao.updateAlbum(album);
        if (i != 0) s = album.getId();
        if (album.getCover().isEmpty()) s = null;
        return s;
    }

    @Override
    public void removeAlbum(String[] ids) {
        albumDao.deleteAlbum(ids);
    }

    @Override
    public void modifyCover(String id, String cover) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("value", cover);
        albumDao.updateByField(map);
    }

}
