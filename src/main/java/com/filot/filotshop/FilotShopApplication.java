package com.filot.filotshop;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FilotShopApplication {


    public static final String APPLICATION_LOCATIONS_REMOTE =
            "spring.config.location=classpath:application.yml" ;

    public static final String APPLICATION_LOCATIONS_LOCAL =
            "spring.config.location=classpath:local.yml" ;
    public static void main(String[] args) {


        new SpringApplicationBuilder(FilotShopApplication.class)
                // if (현재 로컬이라면 (개발중이라면) {
                //  .properties(APPLICATION_LOCATIONS_LOCAL)
                // else if (현재 헤로쿠라면 (원격 서버에 올라갔다면)
                    .properties(APPLICATION_LOCATIONS_REMOTE)
                .run(args);
    }


    @Bean
    public PasswordEncoder passwordEncoder (){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
