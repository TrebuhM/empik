package com.empik;

import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = { "spring.datasource.url=jdbc:postgresql://127.0.0.1:5433/empik",
    "spring.datasource.password=integration-test", "spring.datasource.username=integration-test" })
public class BaseIT {

}
