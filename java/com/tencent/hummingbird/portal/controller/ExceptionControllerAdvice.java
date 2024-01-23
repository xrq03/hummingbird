package com.tencent.hummingbird.portal.controller;

import com.tencent.hummingbird.portal.r.R;
import com.tencent.hummingbird.portal.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Create with IntelliJ IDEA.
 * Description:利用AOP
 * User: 玛卡巴卡萌♡
 * Date: 2023-05-14
 * Time: 9:12
 */
@RestControllerAdvice   //拦截所有controller中的方法
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler
    public R handlerServiceException(ServiceException e){
        log.error("发生业务异常",e);
        return R.failed(e);
    }
    @ExceptionHandler
    public R handlerException(Exception e){
        log.error("其他异常",e);
        return R.failed(e);
    }
}
