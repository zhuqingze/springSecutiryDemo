package com.test.securityjwt.demo.biz;

import com.test.securityjwt.demo.biz.base.BaseBiz;
import com.test.securityjwt.demo.entity.Role;
import com.test.securityjwt.demo.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zhuqz
 * @date: 2021/3/3 16:26
 * @description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleBiz extends BaseBiz<RoleMapper, Role> {
    @Override
    protected String getPageName() {
        return null;
    }
}