package org.timur.roadmap.tennisscoreboard;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.timur.roadmap.tennisscoreboard.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Комментарии, которые не несут значимой смысловой нагрузки или
        // просто описывают работу методов, не нужны. Стоит удалять их перед коммитом.

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null; 
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" }; // Routes all traffic to DispatcherServlet
    }
}