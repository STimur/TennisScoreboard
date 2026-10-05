package org.timur.roadmap.tennisscoreboard.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.timur.roadmap.tennisscoreboard") // В веб конфигурации лучше оставить
    // сканирование пакетов только веб слоя, а остальные сканировать в другом классе (например в AppConfig).
public class WebConfig implements WebMvcConfigurer {

    // Комментарии, которые не несут значимой смысловой нагрузки (как в строке 18) или
        // просто описывают работу методов, не нужны. Стоит удалять их перед коммитом.
    // You can override methods here to add View Resolvers or Resource Handlers

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Разрешаем доступ к любым файлам в корне webapp
        registry.addResourceHandler("/**")
                .addResourceLocations("/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // При переходе на "/" (корень приложения) автоматически отдавать index.html
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}