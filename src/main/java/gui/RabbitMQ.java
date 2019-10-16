package gui;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQBasicProperties;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;

public class RabbitMQ {

    public static final String userName = "fitnessCrew";
    public static final String password = "fitnessCrew";
    public static final String host = "localhost";
    public static final String virtualHost = "fitnessCrew/";
    public static final int portNumber = 5672;

    public RabbitMQ () {}

    public static void sendRabbitMQMessage (String nameExchange, String nameQueue, String nameBind, String message) {

        try {

            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername(userName);
            connectionFactory.setPassword(password);
            connectionFactory.setHost(host);
            connectionFactory.setPort(portNumber);
            connectionFactory.setVirtualHost(virtualHost);

            Connection connection = connectionFactory.newConnection();
            if (connection == null) return;

            System.out.println("Conexão RabbitMq estabelida: ");
            System.out.println("Porta: " + connection.getPort() + " Endereço: " + connection.getAddress());

            AMQP.BasicProperties properties = generateRabbitMQBasicProperties("86400000", "fitnessCrew", "Fitness Crew App", "text/plain");

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(nameExchange, "direct", true);
            channel.queueDeclare(nameQueue, true, false, false, null);
            channel.queueBind(nameQueue, nameExchange, nameBind);
            channel.basicPublish(nameExchange, nameBind, properties, message.getBytes());

            System.out.println("Mensagem enviada ao RabbitMQ");

            channel.close();
            connection.close();

        } catch (Exception e) {
            System.err.println("Erro ao conectar com RabbitMQ: " + e.getMessage());
        }
    }

    public static void receiveRabbitMQMessage (String nameQueue) {

        try {

            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername(userName);
            connectionFactory.setPassword(password);
            connectionFactory.setHost(host);
            connectionFactory.setPort(portNumber);
            connectionFactory.setVirtualHost(virtualHost);

            Connection connection = connectionFactory.newConnection();
            if (connection == null) return;

            System.out.println("Conexão RabbitMq estabelida: ");
            System.out.println("Porta: " + connection.getPort() + " Endereço: " + connection.getAddress());

            Channel channel = connection.createChannel();
            channel.basicConsume(nameQueue, true,  (s, delivery) -> {

                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("Mensagem: " + message);

            }, s -> {

                System.out.println("Erro ao receber mensagem do RabbitMQ.");

            });

        } catch (Exception e) {
            System.err.println("Erro ao conectar com o RabbitMQ: " + e.getMessage());
        }
    }

    public static void countMessagesAndConsumers (String nameQueue) {

        try {

            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername(userName);
            connectionFactory.setPassword(password);
            connectionFactory.setHost(host);
            connectionFactory.setPort(portNumber);
            connectionFactory.setVirtualHost(virtualHost);

            Connection connection = connectionFactory.newConnection();
            if (connection == null) return;

            System.out.println("Conexão RabbitMq estabelida: ");
            System.out.println("Porta: " + connection.getPort() + " Endereço: " + connection.getAddress());

            Channel channel = connection.createChannel();
            AMQP.Queue.DeclareOk response = channel.queueDeclarePassive(nameQueue);

            System.out.println(response.getMessageCount() + " mensagens na fila " + nameQueue);
            System.out.println(response.getConsumerCount() + " consumidores na fila " + nameQueue);

        } catch (Exception e) {
            System.err.println("Erro ao conectar com o RabbitMQ: " + e.getMessage());
        }
    }

    public static AMQP.BasicProperties generateRabbitMQBasicProperties (String expiration, String userId, String appId, String contentType) {

        int nonPersistentMessage = 1;
        int persistentMessage = 2;

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(persistentMessage);
        builder.expiration(expiration);
        builder.priority(9);
        builder.userId(userId);
        builder.appId(appId);
        builder.contentEncoding("UTF-8");
        builder.contentType(contentType);
        builder.timestamp(Date.valueOf(LocalDate.now()));

        return builder.build();
    }

}
