package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import com.baizhi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("queryArticle")
    public Page<Article> queryArticle(int page, int rows, String gid){
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rowNum", rows);
        map.put("gid", gid);
        Page<Article> articlePage = articleService.queryArticleByGid(map);
        return  articlePage;
    }

    @RequestMapping("alter")
    public void alter(String oper, Article article, String[] id){
        if ("add".equals(oper)){
            articleService.addArticle(article);
        }else if ("del".equals(oper)){
            articleService.removeArticle(id);
        }
    }

    @RequestMapping("upload")
    public Map<String, Object> upload(MultipartFile image, HttpSession session){
        String realPath = session.getServletContext().getRealPath("articlePic");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdir();
        }
        Map<String, Object> map = new HashMap<>();
        try {
            image.transferTo(new File(realPath, image.getOriginalFilename()));
            map.put("error", 0);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error", 1);
        }
        map.put("url", "http://localhost:8088/cmfz/articlePic/"+image.getOriginalFilename());
        return map;
    }

    @RequestMapping("showAllImg")
    public Map<String, Object> showAllImg(HttpSession session){
        String articlePic = session.getServletContext().getRealPath("articlePic");
        File file = new File(articlePic);
        String[] list = file.list();
        Map<String, Object> map = new HashMap<>();
        map.put("current_url", "http://localhost:8088/cmfz/articlePic/");
        map.put("total_count", list.length);
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            String name = list[i];
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("is_dir", false);
            objectMap.put("has_file", false);
            File file1 = new File(articlePic, name);
            objectMap.put("filesize", file1.length());
            objectMap.put("is_photo", true);
            String type = name.substring(name.lastIndexOf(".")+1);
            objectMap.put("filetype", type);
            objectMap.put("filename", name);
            objectMap.put("datetime", new Date());
            objects.add(objectMap);
        }
        map.put("file_list", objects);
        return map;
    }
}
