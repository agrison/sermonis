package me.grison.sermonis.conf;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC Web-App Configuration.
 *
 * @author Alexandre Grison <a.grison@gmail.com>
 */
@Configuration
@EnableWebMvc
public class WebAppConfiguration extends WebMvcConfigurerAdapter {
    /**
     * Register the Meteor arguments resolver.
     * @param argumentResolvers the list of already registered argument resolvers.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new MeteorArgumentResolver());
    }

    /**
     * Serve resources from /WEB-INF/resources/ to `/resources/`.
     * @param registry the Spring resource handler registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    /**
     * Let the default servlet deal with requests we don't care.
     * @param configurer the Spring default servlet handler configurer.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
