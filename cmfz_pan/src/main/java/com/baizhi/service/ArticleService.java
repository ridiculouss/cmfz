package com.baizhi.service;

import com.baizhi.entity.Article;
import com.baizhi.util.Page;

import java.util.Map;

public interface ArticleService {

    Page<Article> queryArticleByGid(Map<String, Object> map);

    void addArticle(Article article);

    void removeArticle(String[] ids);
}
