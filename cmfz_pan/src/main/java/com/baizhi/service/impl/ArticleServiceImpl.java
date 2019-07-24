package com.baizhi.service.impl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import com.baizhi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Article> queryArticleByGid(Map<String, Object> map) {
        Integer rowNum = Integer.valueOf(map.get("rowNum").toString());
        Integer currentPage = Integer.valueOf(map.get("page").toString());

        Page<Article> page = new Page<>();
        page.setPageSize(rowNum);
        page.setCurrentPage(currentPage);

        map.put("begin", page.getBegin());
        map.put("pageSize", page.getPageSize());

        List<Article> articles = articleDao.selectArticleByGid(map);
        int count = articleDao.selectArticleCount(map.get("gid").toString());

        page.setResult(articles);
        page.setTotal(count);
        return page;
    }

    @Override
    public void addArticle(Article article) {
        String id = UUID.randomUUID().toString();
        article.setId(id);
        int i = articleDao.insertArticle(article);
    }

    @Override
    public void removeArticle(String[] ids) {
        articleDao.deleteArticle(ids);
    }

}
