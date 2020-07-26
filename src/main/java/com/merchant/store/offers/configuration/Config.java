package com.merchant.store.offers.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Configuration
@EnableSwagger2
public class Config {

    @Bean
    public Supplier<LocalDateTime> getOfferClock(){
        return () -> LocalDateTime.now();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.merchant.store.offers"))
                .paths(PathSelectors.any())
                .build();
    }

}
