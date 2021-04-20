package com.example.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Arman
 * Date: 4/17/21
 * Time: 11:56 AM
 **/
@Configuration
public class DocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("The Lab categorized items API documentation")
                .description("NOTE: This service will be activated on April 17, 2021 \n \n \n " +
                        "Project github link: \n" +
                        "https://github.com/2arman/lab-items")
                .termsOfServiceUrl("")
                .version("0.0.1")
                .contact(new Contact("Arman Ajabkhani", "", "arman.ajabkhani@gmail.com"))
                .build();
    }

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }


}
