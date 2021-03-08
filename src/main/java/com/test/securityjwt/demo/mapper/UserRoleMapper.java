package com.test.securityjwt.demo.mapper;

import com.test.securityjwt.demo.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserRoleMapper extends Mapper<UserRole> {
    List<String> getUserRoleList(@Param("userId") String userId);
}
