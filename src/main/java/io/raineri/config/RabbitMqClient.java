package io.raineri.config;


import com.rabbitmq.client.amqp.Connection;
import com.rabbitmq.client.amqp.Environment;
import com.rabbitmq.client.amqp.impl.AmqpEnvironmentBuilder;

public class RabbitMqClient {
    private static Connection client;
    public RabbitMqClient() {
        Environment environment = new AmqpEnvironmentBuilder()
                .connectionSettings()
                .uri("amqp://guest:guest@localhost:5672/%2f")
                .environmentBuilder()
                .build();

        client = environment.connectionBuilder().build();
    }
    public Connection instance() {
        if (client == null)
            new RabbitMqClient();
        return client;
    }
}
