package com.tencent.hummingbird.portal.controller;


import com.github.pagehelper.PageInfo;
import com.tencent.hummingbird.portal.pojo.Question;
import com.tencent.hummingbird.portal.r.R;
import com.tencent.hummingbird.portal.service.IQuestionService;
import com.tencent.hummingbird.portal.service.IUserService;
import com.tencent.hummingbird.portal.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ytsky
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    @GetMapping("/my")
    //                          下面的注解会从Spring-Security中获得用户详情对象
    public R<List<Question>> my(@AuthenticationPrincipal UserDetails user,Integer pageNum,Integer pageSize){
        String username=user.getUsername(); //phone
       // Integer pageSize=8;
        PageInfo<Question> myQuestions = questionService.getMyQuestions(username,pageNum,pageSize);
        return R.ok(myQuestions);
    }

    @PostMapping("/create")
    public R createQuestion(@Validated QuestionVo questionVo, BindingResult result,
                            @AuthenticationPrincipal UserDetails user){
        if (result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            return R.unproecsableEntity(msg);
        }
        questionService.saveQuestions(questionVo, user.getUsername());
        return R.ok("发布问题完成");

    }
    @Autowired
    IUserService userService;
    @GetMapping("/getMyQuestion")
    public R<List<Question>> myQuestions(@AuthenticationPrincipal UserDetails user){
        List<Question> questions = userService.getUser(user.getUsername());
        return R.ok(questions);
    }

    public R<PageInfo<Question>> teacher(@AuthenticationPrincipal UserDetails user,Integer pageNum) {
        if (pageNum == null)
            pageNum = 1;
        Integer pageSize = 8;
        PageInfo<Question> pageInfo = questionService.getQuestionsByTeacherName(user.getUsername(), pageNum, pageSize);
        return R.ok(pageInfo);
    }
    @GetMapping("/detail/{id}")
    public R<Question> question(@PathVariable Integer id){
        if (id==null){
            return R.invalidRequest("id不能为空");
        }
        Question question = questionService.getQuestionById(id);
        return R.ok(question);
    }
}
