package com.typ.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.typ.business.entity.User;
import com.typ.business.mapper.UserMapper;
import com.typ.business.service.UserService;
import com.typ.common.mybatis.model.PageQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public IPage<User> pageUsers(PageQuery query) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getSearchKey())) {
            wrapper.like(User::getUsername, query.getSearchKey())
                    .or().like(User::getNickname, query.getSearchKey());
        }
        Page<User> page = new Page<>(query.getPageNum(), query.getPageSize());
        return this.page(page, wrapper);
    }

    @Override
    public User getUserById(Long id) {
        return this.getById(id);
    }

    @Override
    public boolean createUser(User user) {
        return this.save(user);
    }

    @Override
    public boolean updateUser(User user) {
        return this.updateById(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return this.removeById(id);
    }
}


