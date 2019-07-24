package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.util.Page;

import java.util.Map;

public interface AlbumService {
    Page<Album> queryAllAlbum(Map<String, Object> map);

    String addAlbum(Album album);

    String modifyAlbum(Album album);

    void removeAlbum(String[] ids);

    void modifyCover(String id, String cover);
}
