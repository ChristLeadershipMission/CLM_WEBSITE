package worldwide.clm.clmwebsite.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class HttpUtils {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public String wakeServerSupportUp(){
        return webClient
                .method(HttpMethod.GET)
                .uri("/clmWebsiteSupport/api/v1/http/log")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
