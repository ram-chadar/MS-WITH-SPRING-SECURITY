package com.jbk.product.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {                                    

  

   @Override
   protected void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("swagger-ui.html")
               .addResourceLocations("classpath:/META-INF/resources/");
       registry.addResourceHandler("/webjars/**")
               .addResourceLocations("classpath:/META-INF/resources/webjars/");
   }
}