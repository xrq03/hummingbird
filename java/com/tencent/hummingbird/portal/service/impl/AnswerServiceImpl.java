package com.tencent.hummingbird.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencent.hummingbird.portal.mapper.UserMapper;
import com.tencent.hummingbird.portal.pojo.Answer;
import com.tencent.hummingbird.portal.mapper.AnswerMapper;
import com.tencent.hummingbird.portal.pojo.User;
import com.tencent.hummingbird.portal.service.IAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.hummingbird.portal.service.ServiceException;
import com.tencent.hummingbird.portal.vo.AnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ytsky
 * @since 2023-04-09
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Override
    public Answer saveAnswer(AnswerVo answerVo, String username) {
        User user=userMapper.findUserByUsername(username);
        Answer answer=new Answer()
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setContent(answerVo.getContent())
                .setQuestId(answerVo.getQuestionId())
                .setAcceptStatus(0)
                .setLikeCount(0)
                .setCreatetime(LocalDateTime.now());
        int result = answerMapper.insert(answer);
        if (result!=1){
            throw ServiceException.busy();
        }
        return answer;
    }

    @Override
    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        /*QueryWrapper<Answer> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("quest_id",questionId);
        List<Answer> answers = answerMapper.selectList(queryWrapper);*/
        List<Answer> answers = answerMapper.findAnswersWithCommentByQuestionId(questionId);

        return answers;
    }
}
