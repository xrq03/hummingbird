package com.tencent.hummingbird.portal.mapper;

import com.tencent.hummingbird.portal.pojo.Question;
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
    public interface QuestionMapper extends BaseMapper<Question> {
        @Select("select title,createtime from question where user_id=#{userId}")
        List<Question> getQuestions(Integer userId);
        @Select("select title,createtime,user_nick_name from question where id=#{id}")
        Question getQuestion(Integer id);

        @Select("select q.*"+
        "from question q"+
        "left join user_question uq on q.id=uq.question_id"+
        "where q.user_id=#{userId} or uq.user_id=#{userId}"+
        "order by q.createtime desc")
        List<Question> findTeacherQuestion(Integer userid);
    }
