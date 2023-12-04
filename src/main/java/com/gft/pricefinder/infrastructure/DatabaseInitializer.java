package com.gft.pricefinder.infrastructure;

import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final Resource schemaResource;
    private final Resource dataResource;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate,
                               @Value("classpath:schema.sql") Resource schemaResource,
                               @Value("classpath:data.sql") Resource dataResource) {
        this.jdbcTemplate = jdbcTemplate;
        this.schemaResource = schemaResource;
        this.dataResource = dataResource;
    }


    @Override
    public void run(String... args) throws Exception {
        executeScript(schemaResource);
        executeScript(dataResource);
    }

    private void executeScript(Resource resource) throws DataAccessException, IOException {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            String script = FileCopyUtils.copyToString(reader);
            jdbcTemplate.execute(script);
        }
    }
}
