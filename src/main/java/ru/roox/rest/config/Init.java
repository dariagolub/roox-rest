package ru.roox.rest.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.roox.rest.filters.TokenAuthFilter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class Init extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                WebAppConfig.class,
                DataConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {

        return new Class<?>[]{
                WebAppConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addFilter("tokenAuthFilter", TokenAuthFilter.class).addMappingForUrlPatterns(null, false, "/customerapi/*");
    }
}
