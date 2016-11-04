package com.devfactory.codeserver.api;

import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Class to serve as base for checkoutStatus tests.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {IntegrationTestConfig.class}, webEnvironment=WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = {"com.devfactory.codeserver"})
@EnableConfigurationProperties(CodeServerProperties.class)
@ActiveProfiles({TestProfile.INTEGRATION_TEST, Profile.API, Profile.REST})
public abstract class BaseIntegrationTest {
}
