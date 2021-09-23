package com.saber.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Bootstrap implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext rootContextConfiguration =new AnnotationConfigWebApplicationContext();
        rootContextConfiguration.register(RootContextConfiguration.class);

        servletContext.addListener(new ContextLoaderListener(rootContextConfiguration));

        AnnotationConfigWebApplicationContext webServletConfiguration = new AnnotationConfigWebApplicationContext();
        webServletConfiguration.register(WebServletConfiguration.class);

        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("SpringDispatcherServlet",
                                                        new DispatcherServlet(webServletConfiguration));
        dispatcherServlet.addMapping("/");
        dispatcherServlet.setLoadOnStartup(1);

    }
}
