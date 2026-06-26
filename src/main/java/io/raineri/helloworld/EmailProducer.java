package io.raineri.helloworld;

import com.google.gson.Gson;
import com.rabbitmq.client.amqp.Connection;
import com.rabbitmq.client.amqp.Message;
import com.rabbitmq.client.amqp.Publisher;
import io.raineri.config.RabbitMqClient;
import io.raineri.model.EmailMessage;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class EmailProducer {
    private final String QUEUE_NAME = "email-queue";
    private final int timeout = 5;
    private Publisher publisher;

    public void createQueue() {
        Connection connection = new RabbitMqClient().instance();
        this.publisher = connection.publisherBuilder().queue(QUEUE_NAME).build();
        System.out.println(publisher);
    }

    public void publish(EmailMessage emailMessage) throws InterruptedException {

        Message message = publisher.message(formatModelToJson(emailMessage).getBytes(StandardCharsets.UTF_8));
        CountDownLatch latch = new CountDownLatch(1);
        this.publisher.publish(
                message,
                context -> {
                    if(context.status() == Publisher.Status.ACCEPTED)
                        System.out.println(" [x] Sent '" + message + "'");
                    latch.countDown();
                });
        if (!latch.await(timeout, TimeUnit.SECONDS))
            throw new IllegalStateException("Timed out waiting for publish outcome");
    }

    public String formatModelToJson(EmailMessage model) {
        return new Gson().toJson(model);
    }
}
