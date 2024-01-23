package com.tencent.hummingbird.portal.mapper;

import com.tencent.hummingbird.portal.pojo.Question;
import com.tencent.hummingbird.portal.pojo.UserCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.hummingbird.portal.vo.UserVo;
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
    public interface UserCollectMapper extends BaseMapper<UserCollect> {
        @Select("select question_id,createtime from user_collect where user_id=#{userId}")
        List<UserCollect> getCollections(Integer userId);
    }
