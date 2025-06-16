package com.bnsref.gatewayservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = GatewayServiceApplication.class)
@ActiveProfiles("test")
// Désactiver explicitement Spring Cloud Config et l'importation
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "spring.config.import=optional:classpath:/application-test.properties"
})
class GatewayServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
