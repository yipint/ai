package com.typ.common.define;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "通用返回码")

public enum CommonCode {
    SUCCESS(0, "成功"),

    FAIL(-1, "失败"),

    PARAM_ERROR(1000, "参数错误"),

    DOWNLOAD_ERROR(4001, "共享信息下载失败")
    ;





    private int code;
    private String message;

    CommonCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }
    public String message() {
        return this.message;
    }




}
