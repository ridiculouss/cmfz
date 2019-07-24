package com.baizhi.dao;

import com.baizhi.entity.Carousel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CarouselDao {
    List<Carousel> selectAllCarousel(@Param("begin") long begin, @Param("pageSize") int pageSize);

    int selectAllCount();

    int updateCarousel(Carousel carousel);

    int insertCarousel(Carousel carousel);

    int deleteCarousel(@Param("ids") String[] ids);

    int updateByField(Map<String, Object> map);
}
