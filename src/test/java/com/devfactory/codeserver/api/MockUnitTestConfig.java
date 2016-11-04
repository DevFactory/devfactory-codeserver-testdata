package com.devfactory.codeserver.api;

import static org.mockito.Mockito.*;
import com.devfactory.codesdk.service.CodesdkService;
import com.devfactory.codeserver.api.dao.RepositoryDB;
import com.devfactory.codeserver.api.dao.RevisionDB;
import com.devfactory.codeserver.api.service.DownloadableService;
import com.devfactory.codeserver.api.service.RepositoryService;
import com.devfactory.codeserver.api.service.RevisionService;
import com.devfactory.codeserver.api.service.ScmInteractionService;
import com.devfactory.codeserver.api.service.SourceApiService;
import com.devfactory.codeserver.api.sourceapi.CodesdkDb;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Spring configuration class.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = {"com.devfactory.codeserver"})
@EnableConfigurationProperties(CodeServerProperties.class)
@Profile(TestProfile.MOCK_TEST)
public class MockUnitTestConfig {

    @Primary
    @Bean
    public CodesdkDb codesdkdb() {
        return mock(CodesdkDb.class);
    }

    @Bean
    public CodesdkService getCodesdkService() {
        return mock(CodesdkService.class);
    }

    @Primary
    @Bean
    public RepositoryService getRepositoryService() {
        return mock(RepositoryService.class);
    }

    @Primary
    @Bean
    public RevisionService getRevisionService() {
        return mock(RevisionService.class);
    }

    @Primary
    @Bean
    public DownloadableService getDownloadableService() {
        return mock(DownloadableService.class);
    }

    @Primary
    @Bean
    public ScmInteractionService getScmInteractionService() {
        return mock(ScmInteractionService.class);
    }

    @Primary
    @Bean
    public SourceApiService getSourceApiService() {
        return mock(SourceApiService.class);
    }

    @Primary
    @Bean
    public RepositoryDB getRepositoryDB() {
        return mock(RepositoryDB.class);
    }

    @Primary
    @Bean
    public RevisionDB getRevisionDB() {
        return mock(RevisionDB.class);
    }

}
