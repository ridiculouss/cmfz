package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AlbumDao {
    List<Album> selectAllAlbum(@Param("begin") long begin, @Param("pageSize") int pageSize);

    int selectAllCount();

    int insertAlbum(Album album);

    int updateAlbum(Album album);

    void deleteAlbum(@Param("ids") String[] ids);

    void updateByField(Map<String, Object> map);

    Album selectAlbumCount(@Param("id")String aid);

    int updateAlbumCount(Album album);
}
