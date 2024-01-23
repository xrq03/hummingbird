package com.tencent.hummingbird.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.hummingbird.portal.pojo.Classroom;
import com.tencent.hummingbird.portal.vo.RegisterVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: 玛卡巴卡萌♡
 * Date: 2023-04-09
 * Time: 15:26
 */
@Mapper
public interface RegisterMapper /*extends BaseMapper<RegisterVo>*/ {
    @Insert("insert into user(username,nickname,password,classroom_id) values(#{phone},#{nickname},#{password},#{cid})")
    int insertUser(RegisterVo registerVo);

    @Select("select * from classroom where invite_code=#{inviteCode}")
    Classroom selectCidByCode(String inviteCode);
}
