package com.typ.ai.service;

import com.typ.ai.model.ReportResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ReportTemplateService {

    @Value("${report.output.path:./reports/}")
    private String outputPath;

    /**
     * 生成报告文件
     */
    public String generateReportFile(ReportResponse response, String format) throws IOException {
        // 确保输出目录存在
        Path outputDir = Paths.get(outputPath);
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }

        String fileName = generateFileName(response.getTitle(), format);
        String filePath = outputDir.resolve(fileName).toString();

        switch (format.toUpperCase()) {
            case "HTML":
                generateHtmlReport(response, filePath);
                break;
            case "TXT":
                generateTextReport(response, filePath);
                break;
            case "JSON":
                generateJsonReport(response, filePath);
                break;
            default:
                throw new IllegalArgumentException("不支持的格式: " + format);
        }

        return filePath;
    }

    /**
     * 生成HTML报告
     */
    private void generateHtmlReport(ReportResponse response, String filePath) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"zh-CN\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>").append(response.getTitle()).append("</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 40px; line-height: 1.6; }\n");
        html.append("        .header { text-align: center; margin-bottom: 30px; }\n");
        html.append("        .section { margin-bottom: 25px; }\n");
        html.append("        .metric { background: #f5f5f5; padding: 15px; margin: 10px 0; border-radius: 5px; }\n");
        html.append("        .insight { background: #e8f4fd; padding: 10px; margin: 5px 0; border-left: 4px solid #2196F3; }\n");
        html.append("        .recommendation { background: #f0f8e8; padding: 10px; margin: 5px 0; border-left: 4px solid #4CAF50; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        
        html.append("    <div class=\"header\">\n");
        html.append("        <h1>").append(response.getTitle()).append("</h1>\n");
        html.append("        <p>生成时间: ").append(response.getGeneratedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("</p>\n");
        html.append("    </div>\n");
        
        if (response.getSummary() != null) {
            html.append("    <div class=\"section\">\n");
            html.append("        <h2>执行摘要</h2>\n");
            html.append("        <p>").append(response.getSummary()).append("</p>\n");
            html.append("    </div>\n");
        }
        
        if (response.getKeyMetrics() != null && !response.getKeyMetrics().isEmpty()) {
            html.append("    <div class=\"section\">\n");
            html.append("        <h2>关键指标</h2>\n");
            response.getKeyMetrics().forEach((key, value) -> {
                html.append("        <div class=\"metric\">\n");
                html.append("            <strong>").append(key).append(":</strong> ").append(value).append("\n");
                html.append("        </div>\n");
            });
            html.append("    </div>\n");
        }
        
        if (response.getInsights() != null && !response.getInsights().isEmpty()) {
            html.append("    <div class=\"section\">\n");
            html.append("        <h2>数据洞察</h2>\n");
            response.getInsights().forEach(insight -> {
                html.append("        <div class=\"insight\">").append(insight).append("</div>\n");
            });
            html.append("    </div>\n");
        }
        
        if (response.getRecommendations() != null && !response.getRecommendations().isEmpty()) {
            html.append("    <div class=\"section\">\n");
            html.append("        <h2>建议措施</h2>\n");
            response.getRecommendations().forEach(recommendation -> {
                html.append("        <div class=\"recommendation\">").append(recommendation).append("</div>\n");
            });
            html.append("    </div>\n");
        }
        
        if (response.getContent() != null) {
            html.append("    <div class=\"section\">\n");
            html.append("        <h2>详细分析</h2>\n");
            html.append("        <div style=\"white-space: pre-wrap;\">").append(response.getContent()).append("</div>\n");
            html.append("    </div>\n");
        }
        
        html.append("</body>\n");
        html.append("</html>\n");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(html.toString());
        }
    }

    /**
     * 生成文本报告
     */
    private void generateTextReport(ReportResponse response, String filePath) throws IOException {
        StringBuilder text = new StringBuilder();
        text.append(response.getTitle()).append("\n");
        text.append("=".repeat(response.getTitle().length())).append("\n\n");
        
        text.append("生成时间: ").append(response.getGeneratedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");
        
        if (response.getSummary() != null) {
            text.append("执行摘要\n");
            text.append("--------\n");
            text.append(response.getSummary()).append("\n\n");
        }
        
        if (response.getKeyMetrics() != null && !response.getKeyMetrics().isEmpty()) {
            text.append("关键指标\n");
            text.append("--------\n");
            response.getKeyMetrics().forEach((key, value) -> {
                text.append(key).append(": ").append(value).append("\n");
            });
            text.append("\n");
        }
        
        if (response.getInsights() != null && !response.getInsights().isEmpty()) {
            text.append("数据洞察\n");
            text.append("--------\n");
            response.getInsights().forEach(insight -> {
                text.append("• ").append(insight).append("\n");
            });
            text.append("\n");
        }
        
        if (response.getRecommendations() != null && !response.getRecommendations().isEmpty()) {
            text.append("建议措施\n");
            text.append("--------\n");
            response.getRecommendations().forEach(recommendation -> {
                text.append("• ").append(recommendation).append("\n");
            });
            text.append("\n");
        }
        
        if (response.getContent() != null) {
            text.append("详细分析\n");
            text.append("--------\n");
            text.append(response.getContent()).append("\n");
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(text.toString());
        }
    }

    /**
     * 生成JSON报告
     */
    private void generateJsonReport(ReportResponse response, String filePath) throws IOException {
        // 这里可以使用Jackson进行JSON序列化
        // 简化处理，直接写入JSON字符串
        String json = "{\n" +
                "  \"reportId\": \"" + response.getReportId() + "\",\n" +
                "  \"title\": \"" + response.getTitle() + "\",\n" +
                "  \"summary\": \"" + response.getSummary() + "\",\n" +
                "  \"content\": \"" + response.getContent().replace("\"", "\\\"") + "\",\n" +
                "  \"generatedAt\": \"" + response.getGeneratedAt() + "\",\n" +
                "  \"processingTime\": " + response.getProcessingTime() + "\n" +
                "}";

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }

    /**
     * 生成文件名
     */
    private String generateFileName(String title, String format) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String safeTitle = title.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]", "_");
        return safeTitle + "_" + timestamp + "." + format.toLowerCase();
    }
}
