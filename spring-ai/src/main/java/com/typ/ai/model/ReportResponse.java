package com.typ.ai.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "报告生成响应")
public class ReportResponse {

    @Schema(description = "报告ID")
    private String reportId;

    @Schema(description = "报告标题")
    private String title;

    @Schema(description = "报告内容")
    private String content;

    @Schema(description = "报告摘要")
    private String summary;

    @Schema(description = "关键指标")
    private Map<String, Object> keyMetrics;

    @Schema(description = "数据洞察")
    private List<String> insights;

    @Schema(description = "建议措施")
    private List<String> recommendations;

    @Schema(description = "报告格式")
    private String format;

    @Schema(description = "文件路径")
    private String filePath;

    @Schema(description = "生成时间")
    private LocalDateTime generatedAt;

    @Schema(description = "处理耗时(毫秒)")
    private Long processingTime;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "错误信息")
    private String errorMessage;
}
