# Spring AI 智能报告生成模块

基于 LangChain4j 和 Spring AI 的智能数据报告生成系统，提供自动化的数据分析和报告生成能力。

## 技术栈

- **Spring Boot 2.6.13**
- **LangChain4j 0.25.0** - Java 版本的 LangChain
- **Spring AI 1.0.0-M3** - Spring 官方 AI 集成
- **OpenAI GPT-3.5-turbo** - 大语言模型
- **Thymeleaf** - 模板引擎
- **Jackson** - JSON 处理

## 核心功能

### 1. 智能数据分析
- 自动分析用户数据统计
- 生成数据洞察和趋势分析
- 计算关键业务指标

### 2. AI 报告生成
- 基于 LangChain4j 的智能内容生成
- 支持多种报告格式（HTML、TXT、JSON）
- 自定义提示词和模板

### 3. 多格式输出
- HTML 报告（带样式和图表）
- 纯文本报告
- JSON 结构化数据
- 支持文件下载

## 项目结构

```
spring-ai/
├── src/main/java/com/typ/ai/
│   ├── config/              # 配置类
│   │   └── LangChain4jConfig.java
│   ├── controller/          # 控制器
│   │   └── ReportController.java
│   ├── model/              # 数据模型
│   │   ├── ReportRequest.java
│   │   └── ReportResponse.java
│   ├── service/            # 业务服务
│   │   ├── DataAnalysisService.java
│   │   ├── ReportGenerationService.java
│   │   ├── ReportTemplateService.java
│   │   └── UserDataService.java
│   └── AiApplication.java  # 启动类
├── src/main/resources/
│   ├── application.yml     # 配置文件
│   └── templates/reports/  # 报告模板
└── pom.xml
```

## 配置说明

### 1. OpenAI 配置
```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY:your-api-key-here}
      base-url: ${OPENAI_BASE_URL:https://api.openai.com/v1}
      chat:
        options:
          model: gpt-3.5-turbo
          temperature: 0.7
          max-tokens: 2000
```

### 2. LangChain4j 配置
```yaml
langchain4j:
  open-ai:
    chat-model:
      api-key: ${OPENAI_API_KEY:your-api-key-here}
      model-name: gpt-3.5-turbo
      temperature: 0.7
```

## API 接口

### 1. 生成报告
```http
POST /api/report/generate
Content-Type: application/json

{
  "reportType": "USER_ANALYSIS",
  "title": "用户数据分析报告",
  "dataSource": "USER_TABLE",
  "format": "HTML",
  "language": "zh-CN",
  "includeCharts": true,
  "includeRecommendations": true
}
```

### 2. 快速生成用户分析报告
```http
POST /api/report/user-analysis?title=月度用户报告&format=HTML
```

### 3. 快速概览报告
```http
GET /api/report/quick?title=数据概览
```

## 使用示例

### 1. 启动应用
```bash
cd spring-ai
mvn spring-boot:run
```

### 2. 设置环境变量
```bash
export OPENAI_API_KEY=your-actual-api-key
export OPENAI_BASE_URL=https://api.openai.com/v1
```

### 3. 调用 API
```bash
# 生成用户分析报告
curl -X POST "http://localhost:8082/api/report/user-analysis" \
  -H "Content-Type: application/json" \
  -d '{"title": "2024年用户分析报告", "format": "HTML"}'
```

## 核心特性

### 1. 智能数据分析
- **用户统计**：总用户数、活跃用户、新用户
- **转化率分析**：活跃用户转化率计算
- **趋势分析**：按时间维度的数据趋势
- **分布分析**：邮箱域名、用户名长度分布

### 2. AI 内容生成
- **自然语言处理**：基于 GPT-3.5 的智能分析
- **结构化输出**：JSON 格式的标准化响应
- **多语言支持**：支持中文、英文等多种语言
- **自定义提示**：支持用户自定义分析要求

### 3. 模板系统
- **HTML 模板**：美观的网页报告格式
- **文本模板**：纯文本格式，便于邮件发送
- **JSON 模板**：结构化数据，便于程序处理

### 4. 扩展性设计
- **插件化架构**：易于添加新的数据源
- **模板引擎**：支持自定义报告模板
- **多模型支持**：可切换不同的 AI 模型

## 开发指南

### 1. 添加新的数据源
```java
@Service
public class CustomDataService {
    public Map<String, Object> getCustomStatistics(ReportRequest request) {
        // 实现自定义数据统计逻辑
    }
}
```

### 2. 自定义报告模板
在 `src/main/resources/templates/reports/` 目录下添加新的模板文件。

### 3. 扩展 AI 提示词
修改 `ReportGenerationService.buildPrompt()` 方法来自定义 AI 提示词。

## 注意事项

1. **API 密钥安全**：请妥善保管 OpenAI API 密钥
2. **成本控制**：合理设置 max-tokens 和 temperature 参数
3. **错误处理**：API 调用失败时会返回错误信息
4. **文件存储**：生成的报告文件存储在 `./reports/` 目录

## 依赖说明

- **LangChain4j**：提供 AI 模型集成和工具链
- **Spring AI**：Spring 官方 AI 集成框架
- **OpenAI**：大语言模型服务提供商
- **Thymeleaf**：模板引擎，用于生成 HTML 报告

## 性能优化

1. **缓存机制**：可添加 Redis 缓存减少重复计算
2. **异步处理**：大批量报告生成可异步处理
3. **连接池**：合理配置 HTTP 连接池
4. **批处理**：支持批量生成多个报告
