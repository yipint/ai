package com.typ.business.model.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户入参模型（BO）")
public class UserBO {

    @Schema(description = "主键ID，更新时必填")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "显示名称")
    private String nickname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "是否生效")
    private Integer isDeleted;
}


