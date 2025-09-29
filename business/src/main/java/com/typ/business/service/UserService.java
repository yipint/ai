package com.typ.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.typ.business.entity.User;
import com.typ.common.mybatis.model.PageQuery;

public interface UserService extends IService<User> {

    IPage<User> pageUsers(PageQuery query);

    User getUserById(Long id);

    boolean createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Long id);
}


