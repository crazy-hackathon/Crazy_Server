package com.project.crazy.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SmartFarm")
                .version("0.0.1")
                .description("대소고 스마트팜 프로젝트 Swagger 문서입니다")
                .build();
    }

}

