package com.typ.business.runner;

import com.winning.imism.common.utils.LocalHostAddressUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 仅在控制台打印启动完成的消息。
 * 在部署时，打印日志将设置成ERROR时。不知道何时服务启动完成.
 *
 * @author mhuang.
 */
@Component
@Slf4j
@Order(value = 0)
public class AppStartedDisplayRunner implements ApplicationRunner {

    private Environment environment;

    @Override
    public void run(ApplicationArguments args) {
        String appName = environment.getProperty("spring.application.name");
        String serverPort = environment.getProperty("server.port");
        //String actuatorPort = environment.getProperty("management.server.port");
        //String sslFlag = environment.getProperty("server.ssl.enabled");
        String sslFlag = "false";
        String active = Arrays.toString(environment.getActiveProfiles());
        String info = String
                .format("\r\n\r\n>>>>>>>>>> %s服务 启动成功, server port = %s, active profiles = %s <<<<<<<<<<\n" +
                                "\n", appName, serverPort,
                         active);
        String ipAddr = LocalHostAddressUtil.getLocalHostExactAddress().getHostAddress();
        String tempSSLStr = "";
        if (Boolean.TRUE.toString().equalsIgnoreCase(sslFlag)) {
            tempSSLStr = "s";
        }
        String serverAddress = MessageFormat.format("服务请求地址: http{2}://{0}:{1}", ipAddr, serverPort, tempSSLStr);
        String docAddress = MessageFormat.format("接口测试地址: http{2}://{0}:{1}/doc.html", ipAddr, serverPort, tempSSLStr);
        String actuatorAddress = MessageFormat.format("日志执行地址: http{2}://{0}:{1}/actuator/loggers", ipAddr, serverPort, tempSSLStr);
        log.info(info);
        log.info(serverAddress);
        log.info(docAddress);
        //log.info(actuatorAddress);

        //便于控制台打印.提示启动状态.
        System.out.println(info);
        System.out.println(serverAddress);
        System.out.println(docAddress);
        //System.out.println(actuatorAddress);
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
