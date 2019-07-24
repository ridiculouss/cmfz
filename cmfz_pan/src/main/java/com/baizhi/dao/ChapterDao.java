package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ChapterDao {

    List<Chapter> selectChapterByAid(Map<String, Object> map);

    int selectChapterCount(@Param("aid") String aid);

    int insertChapter(Chapter chapter);

    void deleteChapter(@Param("ids")String[] ids);

    void updateByField(Map<String, Object> map);
}
