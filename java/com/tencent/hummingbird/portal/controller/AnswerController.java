package com.tencent.hummingbird.portal.controller;


import com.tencent.hummingbird.portal.pojo.Answer;
import com.tencent.hummingbird.portal.pojo.User;
import com.tencent.hummingbird.portal.r.R;
import com.tencent.hummingbird.portal.service.IAnswerService;
import com.tencent.hummingbird.portal.vo.AnswerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = "/answer")
@Slf4j
public class AnswerController {
    @Autowired
    private IAnswerService answerService;
    @PostMapping("/add")
    @PreAuthorize("hasRole('TEACHER')")
    //@PreAuthorize("hasAuthority('/question/answer')")
    public R postAnswer(@AuthenticationPrincipal UserDetails user,@Validated AnswerVo answerVo, BindingResult result){
        log.info("答案:{}",answerVo);
        if (result.hasErrors()){
            String message=result.getFieldError().getDefaultMessage();
            return R.unproecsableEntity(message);
        }
        Answer answer=answerService.saveAnswer(answerVo, user.getUsername());
        return R.created("添加回答完成");
    }

    @GetMapping("/question/{id}")
    public R<List<Answer>> questionAnswers(@PathVariable Integer id){
        List<Answer> answers = answerService.getAnswersByQuestionId(id);
        if (id==null){
            return R.invalidRequest("问题Id不能为空");
        }
        return R.ok(answers);
    }
}
