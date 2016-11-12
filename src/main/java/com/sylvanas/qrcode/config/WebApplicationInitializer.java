package com.sylvanas.qrcode.config;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * WebApplication配置,用于替代web.xml
 *
 * Created by sylvanasp on 2016/11/10.
 */
public class WebApplicationInitializer implements org.springframework.web.WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        //注册Spring MVC配置类
        ctx.register(SpringMvcConfig.class);
        //关联ServletContext
        ctx.setServletContext(servletContext);
        //配置Spring MVC的DispatcherServlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
                new DispatcherServlet(ctx));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
    }
}
