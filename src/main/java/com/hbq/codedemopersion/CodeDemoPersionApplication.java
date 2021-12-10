package com.hbq.codedemopersion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CodeDemoPersionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeDemoPersionApplication.class, args);
    }

}
