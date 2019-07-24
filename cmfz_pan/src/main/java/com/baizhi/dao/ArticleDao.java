package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArticleDao {
    List<Article> selectArticleByGid(Map<String, Object> map);

    int selectArticleCount(@Param("gid") String gid);

    int insertArticle(Article article);

    void deleteArticle(@Param("ids") String[] ids);
}
