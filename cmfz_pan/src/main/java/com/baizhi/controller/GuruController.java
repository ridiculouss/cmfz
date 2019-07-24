package com.baizhi.controller;

import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
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
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService guruService;

    @RequestMapping("queryAll")
    public Page<Guru> queryAll(int page, int rows){
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rowNum", rows);
        Page<Guru> GuruPage = guruService.queryAllGuru(map);
        return GuruPage;
    }

    @RequestMapping("alter")
    public String alter(String oper, Guru guru, String[] id){
        if ("add".equals(oper)){
            return guruService.addGuru(guru);
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile profile, HttpSession session){
        if (profile.getSize() != 0){
            String[] split = profile.getContentType().split("/");
            String imageName = new Date().getTime() + "." +split[split.length-1];
            String realPath = session.getServletContext().getRealPath("/guruPic");

            File file = new File(realPath);
            if (!file.exists()){
                file.mkdir();
            }
            try {
                profile.transferTo(new File(realPath +"/"+ imageName));
                guruService.modifyProfile(id, imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("alterStatus")
    public String alterStatus(String id, String status){
        return guruService.modifyStatus(id, status);
    }
    
}
