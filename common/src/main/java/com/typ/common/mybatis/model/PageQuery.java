package com.typ.common.mybatis.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @description: 基础查询条件封装
 * @author: typ
 * @date: 2025/5/28
 * @version: 1.0
 */
@Schema(description = "基础分页查询条件封装")
@Data
@Accessors(chain = true)
public class PageQuery implements Serializable {

    @Schema(description = "当前页码")
    private Integer pageNum = 1;

    @Schema(description = "每页条数")
    private Integer pageSize = 10;

    @Schema(description = "搜索关键字")
    private String searchKey;

    /** 开始时间.(yyyy-MM-dd HH:mm:ss) */
    @Schema(description = "开始时间.(yyyy-MM-dd HH:mm:ss)")
    private LocalDateTime startDateTime;

    /** 结束时间. */
    @Schema(description = "结束时间.(yyyy-MM-dd HH:mm:ss)")
    private LocalDateTime endDateTime;

    /** 开始日期.(yyyy-MM-dd) */
    @Schema(description = "开始日期.(yyyy-MM-dd)")
    private LocalDate startDate;

    /** 结束日期. */
    @Schema(description = "结束日期.(yyyy-MM-dd)")
    private LocalDate endDate;

    public static <T> IPage<T> buildPage(PageQuery query) {
        return new Page<>(query.getPageNum(), query.getPageSize());
    }
}
