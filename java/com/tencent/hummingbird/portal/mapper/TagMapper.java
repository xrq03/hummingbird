package com.tencent.hummingbird.portal.mapper;

import com.tencent.hummingbird.portal.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* <p>
    *  Mapper 接口
    * </p>
*
* @author ytsky
* @since 2023-04-09
*/
    @Repository
    public interface TagMapper extends BaseMapper<Tag> {
        @Select("select * from tag")
        List<Tag> selectTags();
    }
