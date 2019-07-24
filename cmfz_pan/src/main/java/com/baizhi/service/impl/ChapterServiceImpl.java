package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
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
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Chapter> queryChapterByAid(Map<String, Object> map) {
        Integer rowNum = Integer.valueOf(map.get("rowNum").toString());
        Integer currentPage = Integer.valueOf(map.get("page").toString());
        Page<Chapter> page = new Page<>();
        page.setPageSize(rowNum);
        page.setCurrentPage(currentPage);
        map.put("begin", page.getBegin());
        map.put("pageSize", page.getPageSize());
        List<Chapter> chapters = chapterDao.selectChapterByAid(map);
        int count = chapterDao.selectChapterCount(map.get("aid").toString());
        page.setResult(chapters);
        page.setTotal(count);
        return page;
    }

    @Override
    public String addChapter(Chapter chapter) {
        String id = UUID.randomUUID().toString();
        chapter.setId(id);
        int i = chapterDao.insertChapter(chapter);
        Album album = albumDao.selectAlbumCount(chapter.getAid());
        album.setCount(album.getCount() + 1);
        int j = albumDao.updateAlbumCount(album);
        if (i != 0 && j !=0) return id;
        return null;
    }

    @Override
    public void removeChapter(String[] ids) {
        chapterDao.deleteChapter(ids);
    }

    @Override
    public void modifyDownPath(String id, String downPath, Double size) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("downPath", downPath);
        map.put("size", size);
        chapterDao.updateByField(map);
    }
}
