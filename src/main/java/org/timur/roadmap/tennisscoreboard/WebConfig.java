package org.timur.roadmap.tennisscoreboard;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.timur.roadmap.tennisscoreboard")
public class WebConfig implements WebMvcConfigurer {
    // You can override methods here to add View Resolvers or Resource Handlers
}