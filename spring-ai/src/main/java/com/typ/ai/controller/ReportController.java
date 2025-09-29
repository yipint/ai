package com.typ.ai.controller;

import com.typ.ai.model.ReportRequest;
import com.typ.ai.model.ReportResponse;
import com.typ.ai.service.ReportGenerationService;
import com.typ.common.mybatis.model.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "智能报告生成")
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportGenerationService reportGenerationService;

    @Operation(summary = "生成数据报告")
    @PostMapping("/generate")
    public Result<ReportResponse> generateReport(@Valid @RequestBody ReportRequest request) {
        ReportResponse response = reportGenerationService.generateReport(request);
        return Result.success(response);
    }

    @Operation(summary = "生成用户分析报告")
    @PostMapping("/user-analysis")
    public Result<ReportResponse> generateUserAnalysisReport(@RequestParam(required = false) String title,
                                                           @RequestParam(required = false) String format,
                                                           @RequestParam(required = false) String language) {
        ReportRequest request = new ReportRequest();
        request.setReportType("USER_ANALYSIS");
        request.setTitle(title != null ? title : "用户数据分析报告");
        request.setDataSource("USER_TABLE");
        request.setFormat(format != null ? format : "HTML");
        request.setLanguage(language != null ? language : "zh-CN");
        request.setIncludeCharts(true);
        request.setIncludeRecommendations(true);

        ReportResponse response = reportGenerationService.generateReport(request);
        return Result.success(response);
    }

    @Operation(summary = "快速生成报告")
    @GetMapping("/quick")
    public Result<ReportResponse> quickReport(@RequestParam(defaultValue = "用户数据概览") String title) {
        ReportRequest request = new ReportRequest();
        request.setReportType("QUICK_OVERVIEW");
        request.setTitle(title);
        request.setDataSource("USER_TABLE");
        request.setFormat("HTML");
        request.setLanguage("zh-CN");
        request.setIncludeCharts(false);
        request.setIncludeRecommendations(true);

        ReportResponse response = reportGenerationService.generateReport(request);
        return Result.success(response);
    }

    @Operation(summary = "获取报告模板列表")
    @GetMapping("/templates")
    public Result<List<String>> getReportTemplates() {
        List<String> templates = List.of(
            "USER_ANALYSIS",
            "QUICK_OVERVIEW", 
            "TREND_ANALYSIS",
            "PERFORMANCE_REPORT",
            "CUSTOM_ANALYSIS"
        );
        return Result.success(templates);
    }

    @Operation(summary = "获取支持的报告格式")
    @GetMapping("/formats")
    public Result<List<String>> getSupportedFormats() {
        List<String> formats = List.of("HTML", "TXT", "JSON");
        return Result.success(formats);
    }
}
