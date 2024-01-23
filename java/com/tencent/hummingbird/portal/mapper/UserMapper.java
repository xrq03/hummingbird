package com.tencent.hummingbird.portal.mapper;

import com.tencent.hummingbird.portal.pojo.Permission;
import com.tencent.hummingbird.portal.pojo.Role;
import com.tencent.hummingbird.portal.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tencent.hummingbird.portal.vo.UserVo;
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
    public interface UserMapper extends BaseMapper<User> {
        @Select("select * from user where username=#{username}")
        User findUserByUsername(String name);

        @Select("select " +
            " p.id , p.name " +
            " from user u " +
            " left join user_role ur on u.id=ur.user_id " +
            " left join role r on r.id=ur.role_id " +
            " left join role_permission rp on r.id=rp.role_id " +
            " left join  permission p on p.id=rp.permission_id " +
            " where u.id=#{id}")
        List<Permission> findUserPermissionById(Integer id);

        @Select("select * from user where type=1")
        List<User> getTeachers();

        @Select("select id,username,nickname from user where username=#{username}")
        UserVo findUserVoByUsername(String name);

        @Select("select id,username,nickname from user where username=#{username}")
        User findMyUserByUsername(String name);

        @Select("select r.id,r.name" +
                " from user u" +
                " left join user_role ur on u.id=ur.user_id" +
                " left join role r on r.id=ur.role_id" +
                " where u.id=#{userId}")
        List<Role> findRolesByUserId(Integer id);
    }
