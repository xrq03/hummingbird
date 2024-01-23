package com.tencent.hummingbird.portal.service.impl;

import com.tencent.hummingbird.portal.mapper.RegisterMapper;
import com.tencent.hummingbird.portal.pojo.Classroom;
import com.tencent.hummingbird.portal.service.IRegisterService;
import com.tencent.hummingbird.portal.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: 玛卡巴卡萌♡
 * Date: 2023-04-09
 * Time: 15:38
 */
@Service
public class RegisterServiceImpl implements IRegisterService {
    @Autowired
    private RegisterMapper registerMapper;
    @Override
    public int regist(RegisterVo registerVo) {
        Classroom classroom = registerMapper.selectCidByCode(registerVo.getInviteCode());
        if (classroom==null){
            return -1;
        }
        registerVo.setPassword("{bcrypt}"+encode(registerVo));
        registerVo.setCid(classroom.getId());
        return registerMapper.insertUser(registerVo);
    }

    public String encode(RegisterVo registerVo){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        return encoder.encode(registerVo.getPassword());
    }
}
