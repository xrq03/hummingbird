package com.tencent.hummingbird.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tencent.hummingbird.portal.mapper.*;
import com.tencent.hummingbird.portal.pojo.*;
import com.tencent.hummingbird.portal.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.hummingbird.portal.service.ITagService;
import com.tencent.hummingbird.portal.service.IUserService;
import com.tencent.hummingbird.portal.service.ServiceException;
import com.tencent.hummingbird.portal.vo.QuestionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ytsky
 * @since 2023-04-09
 */
@Service
@Slf4j  //日志处理技术  有该注解才可以使用log
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private  QuestionMapper questionMapper;
    @Override
    public PageInfo<Question> getMyQuestions(String username,Integer pageNumber,Integer pageSize) {
        /**
         * username--User
         * user.getId();
         * QueryWrapper  qw  eq("userid",user.getId())
         * list(qw)
         */
        User user = userMapper.findUserByUsername(username);
        QueryWrapper<Question> query=new QueryWrapper<>();
        query.eq("user_id", user.getId());
        query.eq("delete_status",0);
        query.orderByDesc("createtime");
        PageHelper.startPage(pageNumber,pageSize);
        List<Question> questions = questionMapper.selectList(query);
        for (Question question:questions){
            String tagNames = question.getTagNames();
            List<Tag> tags = tagNameToTags(tagNames);
            question.setTags(tags);
        }
        return new PageInfo<Question>(questions);
    }
    @Autowired
    private QuestionTagMapper questionTagMapper;
    @Autowired
    private UserQuestionMapper userQuestionMapper;
    @Autowired
    private IUserService userService;

    @Override
    @Transactional//添加事务处理  保证几个添加同时成功或失败
    public void saveQuestions(QuestionVo questionVo, String username) {
        //添加question
        User user=userMapper.findUserByUsername(username);
        log.debug("获得当前登录用户信息:{}",user);
        StringBuilder builder=new StringBuilder();
        for (String tagName:questionVo.getTagNames()){
            builder.append(tagName).append(",");
        }
        String tagNames=builder.deleteCharAt(builder.length()-1).toString();

        Question question=new Question().setTitle(questionVo.getTitle())
                .setContent(questionVo.getContent())
                .setTagNames(tagNames)
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setStatus(0)
                .setDeleteStatus(0)
                .setPublicStatus(0)
                .setPageViews(0)
                .setCreatetime(LocalDateTime.now());
        int num1 = questionMapper.insert(question);//自动给id赋值
        if (num1!=1){
            throw ServiceException.busy();
        }
        log.debug("新增添加了对象:{}",question);
        //添加questionTag
        Map<String,Tag> nameToTagMap=tagService.getNameToTagMap();
        for (String tagName:questionVo.getTagNames()){
            Tag tag=nameToTagMap.get(tagName);
            QuestionTag questionTag=new QuestionTag()
                    .setQuestionId(question.getId())
                    .setTagId(tag.getId());
            int num2=questionTagMapper.insert(questionTag);
            if (num1!=1){
                throw ServiceException.busy();
            }
            log.debug("新增了标签关系:{}",questionTag);
        }
        //user-question 教师-问题
        Map<String,User> masters=userService.getMasterMap();
        for (String nickName:questionVo.getTeacherNicknames()){
            User master=masters.get(nickName);
            UserQuestion userQuestion=new UserQuestion()
                    .setQuestionId(question.getId())
                    .setUserId(master.getId())
                    .setCreatetime(LocalDateTime.now());
            int nums = userQuestionMapper.insert(userQuestion);
            if (nums!=1){
                throw ServiceException.busy();
            }
            log.debug("新增了讲师关系",userQuestion);
        }
    }

    @Override
    public Integer countQuestionsByUserId(Integer userId) {
        //指定泛型的目的   --找到相应的表
        QueryWrapper<Question> query=new QueryWrapper<>();
        query.eq("user_Id",userId);
        Integer count = questionMapper.selectCount(query);
        return count;
    }
    @Autowired
    UserCollectMapper userCollectMapper;
    @Override
    public Integer collectQuestionsByUserId(Integer userId) {
        QueryWrapper<UserCollect> query=new QueryWrapper();
        query.eq("user_id",userId);
        Integer collect = userCollectMapper.selectCount(query);
        return collect;
    }

    @Override
    public List<Question> GetMyQuestionsByUserId(Integer userId) {
        List<Question> questions = questionMapper.getQuestions(userId);
        return questions;
    }

    @Override
    public Question getQuestion(Integer id) {
        return questionMapper.getQuestion(id);
    }

    @Autowired
    private ITagService tagService;
    public List<Tag> tagNameToTags(String tagNames){
        String[] tns=tagNames.split(",");
        List<Tag> tags=new ArrayList<>();
        Map<String, Tag> nameToTagMap = tagService.getNameToTagMap();
        for (String tagName:tns){
            Tag tag = nameToTagMap.get(tagName);
            tags.add(tag);
        }
        return tags;
    }
    public PageInfo<Question> getQuestionsByTeacherName(String username,Integer pageNumber,Integer pageSize) {
        User user=userMapper.findUserByUsername(username);
        PageHelper.startPage(pageNumber,pageSize);
        List<Question> questions=questionMapper.findTeacherQuestion(user.getId());
        for (Question q:questions){
            List<Tag> tags=tagNameToTags(q.getTagNames());
            q.setTags(tags);
        }
        return new PageInfo<>(questions);
    }

    @Override
    public Question getQuestionById(Integer id) {
        Question question = questionMapper.selectById(id);
        List<Tag> tags = tagNameToTags(question.getTagNames());
        question.setTags(tags);
        return question;
    }
}

