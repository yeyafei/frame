package com.frame.swagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.frame.constant.Constant;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* Swagger
*
* @author yyf
* @date 2018年11月2日
*/
@EnableSwagger2
@Configuration
public class Swagger2Config {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(Constant.SWAGGER_BASEPACKAGE))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(Constant.SWAGGER_TITLE)
				.description(Constant.SWAGGER_DESCRIPTION)
				.termsOfServiceUrl(Constant.SWAGGER_TERMSOFSERVICEURL)
				.version(Constant.SWAGGER_VERSION)
				.build();
	}
}