package com.devfactory.codeserver.api;

import com.devfactory.codeserver.api.sourceapi.CodesdkDb;
import org.modelmapper.ModelMapper;
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
@Profile(TestProfile.UNIT_TEST)
public class UnitTestConfig {

    @Primary
    @Bean
    public CodesdkDb codesdkdb() {
        return new CodesdkDb();
    }

}
