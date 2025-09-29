package com.typ.ai.service;

import com.typ.ai.model.ReportRequest;
import com.typ.ai.model.ReportResponse;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReportGenerationService {

    @Autowired
    private ChatLanguageModel chatLanguageModel;

    @Autowired
    private DataAnalysisService dataAnalysisService;

    @Autowired
    private ReportTemplateService templateService;

    /**
     * 生成智能数据报告
     */
    public ReportResponse generateReport(ReportRequest request) {
        long startTime = System.currentTimeMillis();
        
        try {
            // 1. 分析数据
            Map<String, Object> analysisResult = dataAnalysisService.analyzeUserData(request);
            
            // 2. 构建提示词
            String prompt = buildPrompt(request, analysisResult);
            
            // 3. 调用AI生成报告内容
            String reportContent = chatLanguageModel.generate(prompt);
            
            // 4. 解析AI响应
            ReportResponse response = parseAIResponse(reportContent, analysisResult);
            
            // 5. 设置基本信息
            response.setReportId(UUID.randomUUID().toString());
            response.setTitle(request.getTitle());
            response.setFormat(request.getFormat());
            response.setGeneratedAt(LocalDateTime.now());
            response.setProcessingTime(System.currentTimeMillis() - startTime);
            response.setStatus("SUCCESS");
            
            // 6. 生成文件（如果需要）
            if (request.getFormat() != null && !request.getFormat().equals("JSON")) {
                String filePath = templateService.generateReportFile(response, request.getFormat());
                response.setFilePath(filePath);
            }
            
            return response;
            
        } catch (Exception e) {
            ReportResponse errorResponse = new ReportResponse();
            errorResponse.setReportId(UUID.randomUUID().toString());
            errorResponse.setTitle(request.getTitle());
            errorResponse.setStatus("ERROR");
            errorResponse.setErrorMessage(e.getMessage());
            errorResponse.setProcessingTime(System.currentTimeMillis() - startTime);
            return errorResponse;
        }
    }

    /**
     * 构建AI提示词
     */
    private String buildPrompt(ReportRequest request, Map<String, Object> analysisResult) {
        StringBuilder prompt = new StringBuilder();
        
        prompt.append("你是一个专业的数据分析师，请根据以下数据生成一份详细的数据分析报告。\n\n");
        
        prompt.append("报告要求：\n");
        prompt.append("- 语言：").append(request.getLanguage()).append("\n");
        prompt.append("- 报告类型：").append(request.getReportType()).append("\n");
        prompt.append("- 标题：").append(request.getTitle()).append("\n");
        
        if (request.getCustomPrompt() != null) {
            prompt.append("- 特殊要求：").append(request.getCustomPrompt()).append("\n");
        }
        
        prompt.append("\n数据概览：\n");
        prompt.append("总用户数：").append(analysisResult.get("totalUsers")).append("\n");
        prompt.append("活跃用户数：").append(analysisResult.get("activeUsers")).append("\n");
        prompt.append("新用户数：").append(analysisResult.get("newUsers")).append("\n");
        prompt.append("转化率：").append(analysisResult.get("conversionRate")).append("\n");
        
        prompt.append("\n详细数据：\n");
        prompt.append(analysisResult.get("rawData").toString());
        
        prompt.append("\n\n请生成包含以下部分的报告：\n");
        prompt.append("1. 执行摘要\n");
        prompt.append("2. 关键指标分析\n");
        prompt.append("3. 数据洞察\n");
        prompt.append("4. 趋势分析\n");
        prompt.append("5. 建议措施\n");
        
        if (request.getIncludeRecommendations()) {
            prompt.append("6. 具体建议\n");
        }
        
        prompt.append("\n请以JSON格式返回，包含以下字段：\n");
        prompt.append("- summary: 执行摘要\n");
        prompt.append("- content: 详细报告内容\n");
        prompt.append("- insights: 数据洞察列表\n");
        prompt.append("- recommendations: 建议措施列表\n");
        
        return prompt.toString();
    }

    /**
     * 解析AI响应
     */
    private ReportResponse parseAIResponse(String aiResponse, Map<String, Object> analysisResult) {
        ReportResponse response = new ReportResponse();
        
        try {
            // 简单的JSON解析（实际项目中可以使用Jackson）
            // 这里简化处理，直接设置内容
            response.setContent(aiResponse);
            response.setSummary("基于AI分析的用户数据报告");
            
            @SuppressWarnings("unchecked")
            List<String> insights = (List<String>) analysisResult.get("insights");
            response.setInsights(insights);
            
            response.setRecommendations(List.of(
                "优化用户注册流程",
                "提升用户活跃度",
                "加强用户留存策略",
                "完善用户反馈机制"
            ));
            
            @SuppressWarnings("unchecked")
            Map<String, Object> keyMetrics = (Map<String, Object>) analysisResult.get("keyMetrics");
            response.setKeyMetrics(keyMetrics);
            
        } catch (Exception e) {
            // 如果解析失败，使用原始响应
            response.setContent(aiResponse);
            response.setSummary("AI生成的数据分析报告");
        }
        
        return response;
    }
}
