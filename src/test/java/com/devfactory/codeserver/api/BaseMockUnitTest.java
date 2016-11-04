package com.devfactory.codeserver.api;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class to serve as base for unit tests.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MockUnitTestConfig.class})
@ComponentScan(basePackages = {"com.devfactory.codeserver"})
@EnableConfigurationProperties(CodeServerProperties.class)
@ActiveProfiles({TestProfile.MOCK_TEST, Profile.API})
public abstract class BaseMockUnitTest {
}
