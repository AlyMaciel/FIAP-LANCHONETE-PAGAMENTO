package com.postech.config;

import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@TestConfiguration
public class EmbeddedMongoConfig {


    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    @Value("${spring.data.mongodb.host:localhost}")
    String ip;

    @Value("${spring.data.mongodb.port:27017}")
    int port;



    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");

    }

    @Bean
    public MongodExecutable mongodExecutable() throws IOException {
        System.out.println("Iniciando MongoDB Embutido...");
        ImmutableMongodConfig mongodConfig = MongodConfig
                .builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable executable = starter.prepare(mongodConfig);

        System.out.println("MongoDB Embutido Configurado em " + ip + ":" + port);
        return executable;
    }
}


