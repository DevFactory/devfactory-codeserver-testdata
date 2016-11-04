package com.devfactory.codeserver.api.sourceapi;

import lombok.Getter;
import org.springframework.web.client.RestTemplate;

public class CodesdkRestParams {

    @Getter
    private final String url;

    @Getter
    private final RestTemplate restTemplate;

    public CodesdkRestParams(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

}
