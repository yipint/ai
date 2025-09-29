package com.typ.ai.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "报告生成请求")
public class ReportRequest {

    @Schema(description = "报告类型", example = "USER_ANALYSIS")
    private String reportType;

    @Schema(description = "报告标题", example = "用户数据分析报告")
    private String title;

    @Schema(description = "数据源", example = "USER_TABLE")
    private String dataSource;

    @Schema(description = "时间范围开始")
    private LocalDateTime startTime;

    @Schema(description = "时间范围结束")
    private LocalDateTime endTime;

    @Schema(description = "过滤条件")
    private Map<String, Object> filters;

    @Schema(description = "分析维度")
    private List<String> dimensions;

    @Schema(description = "报告格式", example = "PDF")
    private String format;

    @Schema(description = "语言", example = "zh-CN")
    private String language = "zh-CN";

    @Schema(description = "自定义提示词")
    private String customPrompt;

    @Schema(description = "是否包含图表")
    private Boolean includeCharts = true;

    @Schema(description = "是否包含建议")
    private Boolean includeRecommendations = true;
}
