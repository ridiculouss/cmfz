package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Carousel;
import com.baizhi.service.AdminService;
import com.baizhi.service.CarouselService;
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
@RequestMapping("carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    @RequestMapping("queryAll")
    public Page<Carousel> queryAll(int page, int rows){
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rowNum", rows);
        Page<Carousel> CarouselPage = carouselService.queryAllCarousel(map);
        return CarouselPage;
    }

    @RequestMapping("alter")
    public String alter(String oper, Carousel carousel, String[] id){
        if ("add".equals(oper)){
            return carouselService.addCarousel(carousel);
        }else if ("edit".equals(oper)){
            return carouselService.modifyCarousel(carousel);
        }else if ("del".equals(oper)){
            carouselService.removeCarousel(id);
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile image, HttpSession session){
        if (image.getSize() != 0){
            String[] split = image.getContentType().split("/");
            String imageName = new Date().getTime() + "." +split[split.length-1];
            String realPath = session.getServletContext().getRealPath("/carouselPic");
            File file = new File(realPath);
            if (!file.exists()){
                file.mkdir();
            }
            try {
                image.transferTo(new File(realPath +"/"+ imageName));
                carouselService.modifyImage(id, imageName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
