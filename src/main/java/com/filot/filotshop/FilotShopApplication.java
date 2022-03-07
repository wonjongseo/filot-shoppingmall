package com.filot.filotshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FilotShopApplication {


    public static final String APPLICATION_LOCATIONS =
            "spring.config.location=" +
                    "classpath:application.yml" ;
//                    "classpath:aws.yml";

    public static void main(String[] args) {

//        SpringApplication.run(FilotShopApplication.class, args);
        new SpringApplicationBuilder(FilotShopApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }


    @Bean
    public PasswordEncoder passwordEncoder (){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
