package com.typ.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.typ.business.utils.BeanConverter;
import com.typ.business.entity.User;
import com.typ.business.model.bo.UserBO;
import com.typ.business.model.vo.UserVO;
import com.typ.business.service.UserService;
import com.typ.common.mybatis.model.PageQuery;
import com.typ.common.mybatis.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @Operation(summary = "分页查询")
    @PostMapping("/page")
    public Result<IPage<UserVO>> page(@RequestBody PageQuery query) {
        IPage<User> page = userService.pageUsers(query);
        IPage<UserVO> voPage = BeanConverter.convertPage(page, UserVO::new);
        return Result.success(voPage);
    }

    @Operation(summary = "根据ID查询")
    @GetMapping("/{id}")
    public Result<UserVO> getById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return Result.success(BeanConverter.convert(user, UserVO::new));
    }

    @Operation(summary = "新增")
    @PostMapping
    public Result<Void> add(@RequestBody UserBO userBO) {
        userService.createUser(BeanConverter.convert(userBO, User::new));
        return Result.success();
    }

    @Operation(summary = "修改")
    @PutMapping
    public Result<Void> update(@RequestBody UserBO userBO) {
        userService.updateUser(BeanConverter.convert(userBO, User::new));
        return Result.success();
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }
}


