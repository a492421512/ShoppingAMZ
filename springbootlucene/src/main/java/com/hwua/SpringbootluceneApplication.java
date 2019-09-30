package com.hwua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringbootluceneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootluceneApplication.class, args);
    }

}
