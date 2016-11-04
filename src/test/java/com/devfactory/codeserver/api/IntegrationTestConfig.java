package com.devfactory.codeserver.api;

import com.devfactory.codesdk.ClientConfig;
import com.devfactory.codesdk.exception.CodesdkException;
import com.devfactory.codesdk.service.CodesdkService;
import com.devfactory.codesdk.service.CodesdkServiceFactory;
import com.devfactory.codeserver.api.longmethod.LongMethodService;
import com.devfactory.codeserver.api.sourceapi.CodesdkDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.Properties;

import static com.devfactory.codesdk.ClientConfig.METADATA_GEN_VIA_GIT_KEY;
import static com.devfactory.codesdk.ClientConfig.METADATA_GEN_VIA_UPLOAD_KEY;
import static com.devfactory.codesdk.ClientConfig.RPC_URL_KEY;

/**
 * Spring configuration class.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = {"com.devfactory.codeserver"})
@EnableConfigurationProperties(CodeServerProperties.class)
@Profile(TestProfile.INTEGRATION_TEST)
public class IntegrationTestConfig {

    @Autowired
    private CodeServerProperties config;

    @Primary
    @Bean
    public CodesdkDb codesdkdb() {
        return new CodesdkDb();
    }

    @Bean
    public ClientConfig getCodesdkClientConfig() {
        Properties properties = new Properties();
        properties.setProperty(RPC_URL_KEY, config.getSourceapi().getIpc().getBaseUrl());
        properties.setProperty(METADATA_GEN_VIA_GIT_KEY, config.getSourceapi().getRest().getMetadata().getGenerateViaGit());
        properties.setProperty(METADATA_GEN_VIA_UPLOAD_KEY, config.getSourceapi().getRest().getMetadata().getGenerateViaUpload());
        return new ClientConfig(properties);
    }

    @Bean
    public CodesdkService getCodesdkService() throws CodesdkException {
        return CodesdkServiceFactory.createService(getCodesdkClientConfig());
    }

    @Bean
    public LongMethodService getLongMethodService() {
        return new LongMethodService();
    }
}
