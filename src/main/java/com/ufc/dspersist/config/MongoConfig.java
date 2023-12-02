package com.ufc.dspersist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate(@Value("${spring.data.mongodb.uri}") String uri) {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(uri));
    }
}
