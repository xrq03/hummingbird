package com.tencent.hummingbird.portal.controller;

import com.tencent.hummingbird.portal.r.R;
import com.tencent.hummingbird.portal.service.IRegisterService;
import com.tencent.hummingbird.portal.service.IUserService;
import com.tencent.hummingbird.portal.service.ServiceException;
import com.tencent.hummingbird.portal.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * Create with IntelliJ IDEA.
 * Description:找登录页
 * User: 玛卡巴卡萌♡
 * Date: 2023-04-09
 * Time: 14:12
 */
@RestController
public class SystemController {
    @RequestMapping("/login.html")
    public ModelAndView loginForm(){
        ModelAndView mav=new ModelAndView("login");//templates/login.html
       // mav.setViewName("login");
        return mav;
    }
    /*@Autowired
    private IRegisterService registerService;
    @RequestMapping(value="/register")
    public R registerStudent(RegisterVo registerVo){
        int regist = registerService.regist(registerVo);
        if (regist>0){
            return R.ok("注册成功");
        }
        return R.failed(new Exception("注册失败"));
        *//*System.out.println(regist);
        return "success";*//*
    }*/
    @Value("${hummingbird.resource.path}")
    private String path;
    @Value("${hummingbird.resource.host}")
    private String host;

    @Autowired
    IUserService userService;
    @RequestMapping("register")
    //当一个实体类前加了@Validated注解
    //表示这个实体类的内容要被SpringValidation验证
    //验证完毕之后,会生成一个BindingResult类型的对象
    //这个对象中保存着验证的结果信息
    public R registerStudent(@Validated RegisterVo registerVo,
                             BindingResult result){
        //spring validation 验证
        if (result.hasErrors()){
            String error = result.getFieldError().getDefaultMessage();
            return R.unproecsableEntity(error);
        }
        //自定义验证
        if (!registerVo.getPassword().equals(registerVo.getConfirm())){
            return R.unproecsableEntity("两次密码不一致");
        }
        userService.registerStudent(registerVo);
        return R.created("注册成功");
    }

    @PostMapping("/upload")
    //MultipartFile[]      和表单中文本域的name一致  <input id="imageFile1" type="file" name="imageFile">
    public R upload(MultipartFile imageFile) throws IOException {
        /*SimpleDateFormat sdf=new SimpleDateFormat("yyyy//MM//dd");
        String childpath = sdf.format(new Date());*/
        String childpath= DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());
        File folder = new File(path,childpath);
        if (!folder.exists()){
           // folder.mkdir();//只能创建当前目录  只能创建一层
            folder.mkdirs();//可以创建层级目录 a/b/c/d
        }
        String originalFilename = imageFile.getOriginalFilename();
        //截取最后一个.后面的字符串（图片格式）
        String ext=originalFilename.substring(originalFilename.lastIndexOf("."));
        String name= UUID.randomUUID().toString()+ext;
        File file=new File(folder,name);
        //执行上传
        imageFile.transferTo(file);
        String url=host+childpath+"/"+name;
        return R.ok(url);
    }
}
