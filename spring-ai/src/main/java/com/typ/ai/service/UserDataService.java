package com.typ.ai.service;

import com.typ.business.service.UserService;
import com.typ.common.mybatis.model.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserDataService {

    @Autowired
    private UserService userService;

    /**
     * 获取用户统计数据
     */
    public Map<String, Object> getUserStatistics(com.typ.ai.model.ReportRequest request) {
        Map<String, Object> statistics = new HashMap<>();
        
        // 获取总用户数
        long totalUsers = userService.count();
        statistics.put("totalUsers", totalUsers);
        
        // 获取分页数据用于分析
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNum(1);
        pageQuery.setPageSize(1000); // 获取更多数据用于分析
        
        var userPage = userService.pageUsers(pageQuery);
        var users = userPage.getRecords();
        
        // 计算活跃用户数（假设最近30天有更新的为活跃用户）
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        long activeUsers = users.stream()
                .filter(user -> user.getUpdateAt() != null && user.getUpdateAt().isAfter(thirtyDaysAgo))
                .count();
        statistics.put("activeUsers", activeUsers);
        
        // 计算新用户数（最近7天注册的）
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        long newUsers = users.stream()
                .filter(user -> user.getCreateAt() != null && user.getCreateAt().isAfter(sevenDaysAgo))
                .count();
        statistics.put("newUsers", newUsers);
        
        // 计算转化率（活跃用户/总用户）
        double conversionRate = totalUsers > 0 ? (double) activeUsers / totalUsers : 0.0;
        statistics.put("conversionRate", Math.round(conversionRate * 100.0) / 100.0);
        
        // 按邮箱域名分组统计
        Map<String, Long> emailDomainStats = users.stream()
                .filter(user -> user.getEmail() != null && user.getEmail().contains("@"))
                .collect(java.util.stream.Collectors.groupingBy(
                    user -> user.getEmail().substring(user.getEmail().indexOf("@") + 1),
                    java.util.stream.Collectors.counting()
                ));
        statistics.put("emailDomainStats", emailDomainStats);
        
        // 按创建时间分组统计（按月）
        Map<String, Long> monthlyStats = users.stream()
                .filter(user -> user.getCreateAt() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                    user -> user.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    java.util.stream.Collectors.counting()
                ));
        statistics.put("monthlyStats", monthlyStats);
        
        // 用户名长度分布
        Map<String, Long> usernameLengthStats = users.stream()
                .filter(user -> user.getUsername() != null)
                .collect(java.util.stream.Collectors.groupingBy(
                    user -> {
                        int length = user.getUsername().length();
                        if (length <= 5) return "1-5";
                        else if (length <= 10) return "6-10";
                        else if (length <= 15) return "11-15";
                        else return "16+";
                    },
                    java.util.stream.Collectors.counting()
                ));
        statistics.put("usernameLengthStats", usernameLengthStats);
        
        // 数据更新时间
        statistics.put("generatedAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        return statistics;
    }
}
