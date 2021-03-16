/*

package com.cloume.maps.doc.template;

import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;


@Configuration
@EnableSwagger2WebMvc
@ConditionalOnExpression("${swagger.enable:true}")
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean(value = "authApi")
    @Order(value = 1)
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cloume.maps.auth.controller"))
                .paths(PathSelectors.any())
                .build().securityContexts(CollectionUtils.newArrayList(securityContext())).securitySchemes(CollectionUtils.<SecurityScheme>newArrayList(apiKey()));
    }

    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title("MAPS微服务聚合API文档")
                .description("<div style='font-size:14px;color:red;'>swagger-bootstrap-ui-demo RESTful APIs</div>")
                .termsOfServiceUrl("https://github.com/HQIT")
                .contact("hqit@cloume.com")
                .version("3.0")
                .build();
    }


    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return CollectionUtils.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }
}
*/
