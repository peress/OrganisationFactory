package com.pipedrive.challenge.repository;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.MountableFile;

public class DatabaseContainer {

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.2-alpine")
            .withDatabaseName("postgrescc")
            .withUsername("postgrescc")
            .withPassword("postgres")
            .withExposedPorts(5432)
            .withCopyFileToContainer(MountableFile.forClasspathResource("sql/"), "/docker-entrypoint-initdb.d/")
            .waitingFor(Wait.forHttp("/"))
            .waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*\\n", 1));

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }
}
