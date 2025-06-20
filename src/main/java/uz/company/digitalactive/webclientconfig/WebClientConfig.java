package uz.company.digitalactive.webclientonfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {
    @Value("${local.host}")
    public String url;

    public WebClient webClient(WebClient.Builder builder){
        return builder
                .baseUrl(url)
                .build();
    }
}