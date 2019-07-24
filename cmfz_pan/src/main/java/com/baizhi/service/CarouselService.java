package com.baizhi.service;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Carousel;
import com.baizhi.util.Page;

import java.util.Map;

public interface CarouselService {
    Page<Carousel> queryAllCarousel(Map<String, Object> map);

    String addCarousel(Carousel carousel);

    String modifyCarousel(Carousel carousel);

    void removeCarousel(String[] ids);

    void modifyImage(String id, String imageName);
}
