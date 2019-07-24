package com.baizhi.service;

import com.baizhi.entity.Chapter;
import com.baizhi.util.Page;

import java.util.Map;

public interface ChapterService {

    Page<Chapter> queryChapterByAid(Map<String, Object> map);

    String addChapter(Chapter chapter);

    void removeChapter(String[] ids);

    void modifyDownPath(String id, String downPath, Double size);
}
