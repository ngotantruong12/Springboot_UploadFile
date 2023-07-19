package ntt.com.example.demo_SpringBoot_Web.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfig {

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:3000")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                        .allowedHeaders("*")
//                        .allowCredentials(true)
//                        .maxAge(3600);
//            }
//        };
//    }
//	@Bean
//	public CommonsMultipartResolver multipartResolver() {
//		CommonsMultipartResolver  multipartResolver = new CommonsMultipartResolver();
//	    multipartResolver.setMaxUploadSize(52428800); // giới hạn 50MB
//	    return multipartResolver;
//	}
}
