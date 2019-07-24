package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("queryAll")
    public Page<User> queryAll(int page, int rows){
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rowNum", rows);
        Page<User> UserPage = userService.queryAllUser(map);
        return UserPage;
    }

    @RequestMapping("alter")
    public String alter(String oper, User user, String opassword, String[] id){
        if ("add".equals(oper)){
            return userService.addUser(user);
        }else if ("edit".equals(oper)){
            return userService.modifyUser(user,opassword);
        }else if ("del".equals(oper)){
            userService.removeUser(id);
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile profile, HttpSession session){
        if (profile.getSize() != 0){
            String[] split = profile.getContentType().split("/");
            String profileName = new Date().getTime() + "." +split[split.length-1];
            String realPath = session.getServletContext().getRealPath("/userPic");
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdir();
            }
            try {
                profile.transferTo(new File(realPath +"/"+ profileName));
                userService.modifyProfile(id, profileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("alterStatus")
    public String alterStatus(String id, String status){
        return userService.modifyStatus(id, status);
    }

    @RequestMapping("exportExcel")
    public void exportExcel(String filename, HttpSession session, HttpServletResponse response) throws Exception {
        String realPath = session.getServletContext().getRealPath("userPic/");
        File file = new File("E:/tempPOI/");
        if(!file.exists()) file.mkdir();
        String name = userService.exportUser(file.getPath(), realPath);
        response.setContentType(session.getServletContext().getMimeType("xls"));
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode((filename+".xls"), "utf-8"));
        FileCopyUtils.copy(new FileInputStream(new File(file, (name + ".xls"))), response.getOutputStream());
    }

    @RequestMapping("importExcel")
    public void importExcel(MultipartFile file) throws Exception{
        File file1 = new File("E:/tempPOI/");
        if(!file1.exists()) file1.mkdir();
        File file2 = new File(file1, file.getOriginalFilename());
        String absolutePath = file2.getAbsolutePath();
        FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(file2));
        userService.importUser(absolutePath);
    }
}
