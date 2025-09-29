package com.typ.ai.service;

import com.typ.ai.model.ReportRequest;
import com.typ.ai.model.ReportResponse;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataAnalysisService {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore embeddingStore;

    @Autowired
    private UserDataService userDataService;

    /**
     * 分析用户数据并生成洞察
     */
    public Map<String, Object> analyzeUserData(ReportRequest request) {
        // 获取用户数据
        Map<String, Object> userData = userDataService.getUserStatistics(request);
        
        // 数据预处理
        String processedData = preprocessData(userData);
        
        // 生成数据洞察
        List<String> insights = generateInsights(processedData, request);
        
        // 计算关键指标
        Map<String, Object> keyMetrics = calculateKeyMetrics(userData);
        
        return Map.of(
            "insights", insights,
            "keyMetrics", keyMetrics,
            "rawData", userData
        );
    }

    /**
     * 预处理数据
     */
    private String preprocessData(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("用户数据分析报告\n");
        sb.append("==================\n\n");
        
        data.forEach((key, value) -> {
            sb.append(key).append(": ").append(value).append("\n");
        });
        
        return sb.toString();
    }

    /**
     * 生成数据洞察
     */
    private List<String> generateInsights(String data, ReportRequest request) {
        // 这里可以集成更复杂的AI分析逻辑
        // 目前返回基础洞察
        return List.of(
            "用户总数呈现增长趋势",
            "新用户注册率保持稳定",
            "用户活跃度有所提升",
            "邮箱验证完成率较高"
        );
    }

    /**
     * 计算关键指标
     */
    private Map<String, Object> calculateKeyMetrics(Map<String, Object> data) {
        return Map.of(
            "totalUsers", data.getOrDefault("totalUsers", 0),
            "activeUsers", data.getOrDefault("activeUsers", 0),
            "newUsers", data.getOrDefault("newUsers", 0),
            "conversionRate", data.getOrDefault("conversionRate", 0.0)
        );
    }

    /**
     * 将文档添加到向量存储
     */
    public void addDocumentToStore(Document document) {
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .documentSplitter(DocumentSplitters.recursive(300, 0))
                .build();
        
        ingestor.ingest(document);
    }

    /**
     * 从向量存储中搜索相关文档
     */
    public List<TextSegment> searchSimilarDocuments(String query, int maxResults) {
        return embeddingStore.findRelevant(embeddingModel.embed(query).content(), maxResults);
    }
}
