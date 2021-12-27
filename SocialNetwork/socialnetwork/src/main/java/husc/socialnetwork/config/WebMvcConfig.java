package husc.socialnetwork.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

 
 
   // Cấu hình để sử dụng các file nguồn tĩnh (html, image, ..)
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
       registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
       registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
   }

   
   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
       configurer.enable();
   }

}
