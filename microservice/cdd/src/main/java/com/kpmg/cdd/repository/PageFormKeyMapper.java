package com.kpmg.cdd.repository;
import org.apache.ibatis.annotations.Param;

import com.kpmg.cdd.entity.PageFormKey;

import java.util.List;
import java.util.Map;

public interface PageFormKeyMapper {
    int insert(PageFormKey record);

    int insertSelective(PageFormKey record);

    List<PageFormKey> selectByExample(PageFormKey record);

    List<Map<String, Object>> getPageFormKeyList(Map<String, Object> map);

    PageFormKey findOneById(@Param("id")Integer id);

    PageFormKey findOneByPageId(@Param("pageId")Integer pageId);


}