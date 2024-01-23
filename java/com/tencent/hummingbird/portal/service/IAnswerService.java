package com.tencent.hummingbird.portal.service;

import com.tencent.hummingbird.portal.pojo.Answer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.hummingbird.portal.pojo.User;
import com.tencent.hummingbird.portal.vo.AnswerVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ytsky
 * @since 2023-04-09
 */
public interface IAnswerService extends IService<Answer> {
    Answer saveAnswer(AnswerVo answerVo,String username);
    List<Answer> getAnswersByQuestionId(Integer questionId);
}
