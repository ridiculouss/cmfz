package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("queryChapter")
    public Page<Chapter> queryChapter(int page, int rows, String aid){
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rowNum", rows);
        map.put("aid", aid);
        Page<Chapter> chapterPage = chapterService.queryChapterByAid(map);
        return  chapterPage;
    }

    @RequestMapping("alter")
    public String alter(String oper, Chapter chapter, String[] id){
        if ("add".equals(oper)){
            String aid = chapterService.addChapter(chapter);
            return aid;
        }else if ("del".equals(oper)){
            chapterService.removeChapter(id);
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile downPath, HttpSession session){
//        String[] split = downPath.getContentType().split("/");
//        String imageName = new Date().getTime() + "." +split[split.length-1];
        String imageName = downPath.getOriginalFilename();
        String realPath = session.getServletContext().getRealPath("/chapterPic");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdir();
        }
        try {
            downPath.transferTo(new File(realPath +"/"+ imageName));
            Double size = Double.valueOf(downPath.getSize()/1024/1024);
            chapterService.modifyDownPath(id, imageName, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("download")
    public void fileDownload(String path, HttpSession session, HttpServletResponse response){
        String realPath = session.getServletContext().getRealPath("chapterPic");
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(new File(realPath + "/" + path));
            //设置响应类型
            response.setContentType(session.getServletContext().getMimeType(path.substring(path.lastIndexOf("."))));
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode(path, "utf-8"));
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
