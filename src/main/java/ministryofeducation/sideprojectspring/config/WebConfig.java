package ministryofeducation.sideprojectspring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "file:////images/personnel/";
    private String uploadPath = "/profile-image/**";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(uploadPath)
            .addResourceLocations(resourcePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("http://dnch-edu-service-react:3000");
        registry.addMapping("/**").allowedOrigins("*");
    }

}
