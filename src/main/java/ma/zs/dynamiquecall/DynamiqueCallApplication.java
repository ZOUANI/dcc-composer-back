package ma.zs.dynamiquecall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DynamiqueCallApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamiqueCallApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return rt;
    }

}
