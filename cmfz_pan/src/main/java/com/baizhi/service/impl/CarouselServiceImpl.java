package com.baizhi.service.impl;

import com.baizhi.dao.CarouselDao;
import com.baizhi.entity.Carousel;
import com.baizhi.service.CarouselService;
import com.baizhi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselDao carouselDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Carousel> queryAllCarousel(Map<String, Object> map) {
        Integer rowNum = Integer.valueOf(map.get("rowNum").toString());
        Integer currentPage = Integer.valueOf(map.get("page").toString());
        Page<Carousel> page = new Page<>();
        page.setPageSize(rowNum);
        page.setCurrentPage(currentPage);
        List<Carousel> carousels = carouselDao.selectAllCarousel(page.getBegin(), page.getPageSize());
        int count = carouselDao.selectAllCount();
        page.setResult(carousels);
        page.setTotal(count);
        return page;
    }

    @Override
    public String addCarousel(Carousel carousel) {
        String id = UUID.randomUUID().toString();
        carousel.setId(id);
        int i = carouselDao.insertCarousel(carousel);
        if (i != 0) return id;
        return null;
    }

    @Override
    public String modifyCarousel(Carousel carousel) {
        String s = null;
        int i = carouselDao.updateCarousel(carousel);
        if (i != 0) s = carousel.getId();
        if (carousel.getImage().isEmpty()) s = null;
        return s;
    }

    @Override
    public void removeCarousel(String[] ids) {
        carouselDao.deleteCarousel(ids);
    }

    @Override
    public void modifyImage(String id, String imageName) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("value", imageName);
        carouselDao.updateByField(map);
    }
}
