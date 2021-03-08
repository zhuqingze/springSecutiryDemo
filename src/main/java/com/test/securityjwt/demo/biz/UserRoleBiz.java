package com.test.securityjwt.demo.biz;

import com.test.securityjwt.demo.biz.base.BaseBiz;
import com.test.securityjwt.demo.entity.UserRole;
import com.test.securityjwt.demo.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhuqz
 * @date: 2021/3/3 16:27
 * @description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleBiz extends BaseBiz<UserRoleMapper, UserRole> {
    @Autowired
    UserRoleMapper userRoleMapper;
    @Override
    protected String getPageName() {
        return null;
    }
    public List<String> getUserRoleList(String userId){
       return this.userRoleMapper.getUserRoleList(userId);
    }
}