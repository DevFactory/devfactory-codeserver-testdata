package com.devfactory.codeserver.api;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Configuration class to interface access to application.yml.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Data
@ConfigurationProperties(prefix = "codeserver")
public class CodeServerProperties {

    //Used in annotations
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    private String basePath;

    private String repositoriesBasePath;

    private String repositoriesJson;

    private String revisionsBasePath;

    private String revisionsJson;

    private String repositoriesDownloadableExtension;

    private SourceApi sourceapi;

    private List<ScmCredential> scmConfigs;

    @Getter
    @Setter
    public static class ScmCredential {

        @NotEmpty
        private URL host;

        private String userName;

        private String password;
    }

    @Data
    public static class SourceApi {

        private Ipc ipc;

        private Rest rest;

    }

    @Getter
    @Setter
    public static class Ipc {

        @NotEmpty
        private String baseUrl;

    }

    @Getter
    @Setter
    public static class Rest {

        private Metadata metadata;

        @NotEmpty
        private String baseUrl;

        @NotEmpty
        private String healthCheckUrl;

    }

    @Getter
    @Setter
    public static class Metadata {

        @NotEmpty
        private String generateViaGit;

        @NotEmpty
        private String generateViaUpload;

        @NotEmpty
        private String downloadCodedb;
    }
}
