package com.sc2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author sc2
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Sc2Application
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(Sc2Application.class, args);
        System.out.println(
                "SC2: Backend started! SC2：后端已启动！"
        );
    }
}