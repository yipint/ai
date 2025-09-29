package com.typ.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.typ.business.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}


