package com.test.securityjwt.demo.mapper;

import com.test.securityjwt.demo.entity.Role;
import com.test.securityjwt.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper    extends Mapper<Role> {

}
