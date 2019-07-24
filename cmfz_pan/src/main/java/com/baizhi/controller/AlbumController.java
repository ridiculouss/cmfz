package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("queryAll")
    public Page<Album> queryAll(int page, int rows){
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rowNum", rows);
        Page<Album> AlbumPage = albumService.queryAllAlbum(map);
        return AlbumPage;
    }

    @RequestMapping("alter")
    public String alter(String oper, Album album, String[] id){
        if ("add".equals(oper)){
            return albumService.addAlbum(album);
        }else if ("edit".equals(oper)){
            return albumService.modifyAlbum(album);
        }else if ("del".equals(oper)){
            albumService.removeAlbum(id);
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile cover, HttpSession session){
        if (cover.getSize() != 0){
            String[] split = cover.getContentType().split("/");
            String imageName = new Date().getTime() + "." +split[split.length-1];
            String realPath = session.getServletContext().getRealPath("/albumPic");
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdir();
            }
            try {
                cover.transferTo(new File(realPath +"/"+ imageName));
                albumService.modifyCover(id, imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
