/**
 * @author yogesh.badke@aurea.com
 */
package com.devfactory.codeserver.api.rest;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devfactory.codeserver.api.service.SourceApiService;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthCheckController {

    private final SourceApiService service;

    public HealthCheckController(SourceApiService service) {
        this.service = service;
    }

    @GetMapping("/health")
    public Map<String, String> getHealthStatus() {
        // Health will be considered down even if codeserver is running but source-api isn't
        return service.isHealthOk()
                ? Collections.singletonMap("status", SourceApiService.HEALTH_OK)
                : Collections.singletonMap("status", SourceApiService.HEALTH_NOT_OK);
    }
}
