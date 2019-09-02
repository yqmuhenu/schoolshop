package com.igeek.dao;

import com.igeek.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDao {
    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryArea();
}
