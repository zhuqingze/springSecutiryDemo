package com.test.securityjwt.demo.mapper;

import com.test.securityjwt.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper   extends Mapper<User> {
    User getUserByName(@Param("name") String name);
}
