package com.typ.common.mybatis.model;

import com.typ.common.define.CommonCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @description: 统一结果封装
 * @author: typ
 * @date: 2025/5/30
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "统一结果封装")
public class Result<T> {
    @Schema(description = "状态码")
    private int code;
    @Schema(description = "返回信息")
    private String message;
    @Schema(description = "返回数据")
    private T data;
    @Schema(description = "是否成功")
    //兼容老项目
    private Boolean success;

    public static <T> Result<T> success(T data) {
        return new Result()
                .setSuccess( true)
                .setCode(CommonCode.SUCCESS.code())
                .setMessage(CommonCode.SUCCESS.message())
                .setData(data);
    }
    public static <T> Result<T> fail(T data) {
        return new Result()
                .setSuccess( false)
                .setCode(CommonCode.FAIL.code())
                .setMessage(CommonCode.FAIL.message())
                .setData(data);
    }

    public static  Result<Void> success() {
        return new Result()
                .setSuccess( true)
                .setCode(CommonCode.SUCCESS.code())
                .setMessage(CommonCode.SUCCESS.message());
    }
    public static  Result<Void> fail() {
        return new Result()
                .setSuccess( false)
                .setCode(CommonCode.FAIL.code())
                .setMessage(CommonCode.FAIL.message());
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result()
                .setSuccess( false)
                .setCode(code)
                .setMessage(message);
    }


}
