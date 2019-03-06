package com.trackorjargh;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebMvcConfig {

    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/new").setViewName("forward:/new/index.html");
                registry.addViewController("/new/login").setViewName("forward:/new/index.html");
                registry.addViewController("/new/perfil").setViewName("forward:/new/index.html");
                registry.addViewController("/new/activarusuario/*").setViewName("forward:/new/index.html");
                registry.addViewController("/new/olvidocontra").setViewName("forward:/new/index.html");
                registry.addViewController("/new/cambiarcontra/*").setViewName("forward:/new/index.html");
                registry.addViewController("/new/contracambiada/*").setViewName("forward:/new/index.html");
                registry.addViewController("/new/busqueda").setViewName("forward:/new/index.html");
                registry.addViewController("/new/peliculas").setViewName("forward:/new/index.html");
                registry.addViewController("/new/pelicula/*").setViewName("forward:/new/index.html");
                registry.addViewController("/new/series").setViewName("forward:/new/index.html");
                registry.addViewController("/new/serie/*").setViewName("forward:/new/index.html");
                registry.addViewController("/new/libros").setViewName("forward:/new/index.html");
                registry.addViewController("/new/libro/*").setViewName("forward:/new/index.html");
                registry.addViewController("/new/administracion").setViewName("forward:/new/index.html");
                registry.addViewController("/new/administracion/*/*").setViewName("forward:/new/index.html");
                registry.addViewController("/new/subirContenido").setViewName("forward:/new/index.html");
            }
        };
    }
}