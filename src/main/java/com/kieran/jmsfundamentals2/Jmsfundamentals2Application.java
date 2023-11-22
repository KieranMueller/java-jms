package com.kieran.jmsfundamentals2;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class Jmsfundamentals2Application {

    public static void main(String[] args) {
        SpringApplication.run(Jmsfundamentals2Application.class, args);
    }
//
//    // JMS 1.x ---------------------------------------------------------------------------------------------------------
//    @Bean
//    public void basicJms1() {
//        log.info("In basicJms1()");
//
//        InitialContext initialContext = null;
//        Connection connection = null;
//
//        try {
//            initialContext = new InitialContext();
//            ConnectionFactory connectionFactory =
//                    (ConnectionFactory) initialContext.lookup("ConnectionFactory");
//            connection = connectionFactory.createConnection();
//            Session session = connection.createSession();
//            Queue queue = (Queue) initialContext.lookup("queue/myQueue");
//            MessageProducer producer = session.createProducer(queue);
//            TextMessage message = session.createTextMessage("I am the creator");
//            producer.send(message);
//            log.warn("Message Sent: " + message.getText());
//
//            MessageConsumer consumer = session.createConsumer(queue);
//            connection.start();
//            TextMessage messageReceived = (TextMessage) consumer.receive(2000);
//            log.warn("Message Received: " + messageReceived.getText());
//        } catch (NamingException | JMSException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (initialContext != null) {
//                try {
//                    initialContext.close();
//                } catch (NamingException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @Bean
//    public void topic() throws Exception {
//
//        log.info("In topic()");
//
//        InitialContext initialContext = new InitialContext();
//        Topic topic = (Topic) initialContext.lookup("topic/myTopic");
//
//        ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
//        Connection connection = cf.createConnection();
//        Session session = connection.createSession();
//
//        MessageProducer producer = session.createProducer(topic);
//        MessageConsumer consumer1 = session.createConsumer(topic);
//        MessageConsumer consumer2 = session.createConsumer(topic);
//
//        TextMessage message = session.createTextMessage("Jasper is a black cat.");
//        producer.send(message);
//        connection.start();
//
//        TextMessage message1 = (TextMessage) consumer1.receive();
//        log.warn("Consumer 1 message received: " + message1.getText());
//
//        TextMessage message2 = (TextMessage) consumer2.receive();
//        log.warn("Consumer 2 message received: " + message2.getText());
//
//        connection.close();
//        initialContext.close();
//    }
//
//    @Bean
//    public void queueBrowser() {
//        log.warn("In queueBrowser()");
//
//        InitialContext initialContext = null;
//        Connection connection = null;
//
//        try {
//            initialContext = new InitialContext();
//            ConnectionFactory connectionFactory =
//                    (ConnectionFactory) initialContext.lookup("ConnectionFactory");
//            connection = connectionFactory.createConnection();
//            Session session = connection.createSession();
//            Queue queue = (Queue) initialContext.lookup("queue/myQueue");
//            MessageProducer producer = session.createProducer(queue);
//
//            TextMessage message1 = session.createTextMessage("Message 1");
//            TextMessage message2 = session.createTextMessage("Message 2");
//
//            producer.send(message1);
//            producer.send(message2);
//
//            QueueBrowser browser = session.createBrowser(queue);
//            Enumeration messagesEnum = browser.getEnumeration();
//
//            while (messagesEnum.hasMoreElements()) {
//                TextMessage eachMessage = (TextMessage) messagesEnum.nextElement();
//                log.warn("Browsing: {}", eachMessage.getText());
//            }
//
//            MessageConsumer consumer = session.createConsumer(queue);
//            connection.start();
//            TextMessage messageReceived = (TextMessage) consumer.receive(2000);
//            log.warn("Message Received: " + messageReceived.getText());
//            messageReceived = (TextMessage) consumer.receive(2000);
//            log.warn("Message Received: " + messageReceived.getText());
//        } catch (NamingException | JMSException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (initialContext != null) {
//                try {
//                    initialContext.close();
//                } catch (NamingException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    // End JMS 1.x -----------------------------------------------------------------------------------------------------
//
//    // JMS 2.x ---------------------------------------------------------------------------------------------------------
//    @Bean
//    public void jmsContext() throws Exception {
//        log.info("Starting jmsContext()");
//        InitialContext context = new InitialContext();
//        Queue queue = (Queue) context.lookup("queue/myQueue");
//
//        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//             JMSContext jmsContext = cf.createContext()) {
//
//            jmsContext.createProducer().send(queue, "Jasper is being a good boy.");
//            String messageReceived = jmsContext.createConsumer(queue).receiveBody(String.class);
//
//            log.warn("Message Received: {}", messageReceived);
//        }
//    }
//
//    @Bean
//    public void messagePriority() throws NamingException {
//        log.info("Starting messagePriority()");
//        InitialContext context = new InitialContext();
//        Queue queue = (Queue) context.lookup("queue/myQueue");
//
//        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//             JMSContext jmsContext = cf.createContext()) {
//
//            JMSProducer producer = jmsContext.createProducer();
//
//            String[] messages = new String[3];
//            messages[0] = "howdy";
//            messages[1] = "What is up";
//            messages[2] = "Bonjour mon ami";
//
//            producer.setPriority(3);
//            producer.send(queue, messages[0]);
//
//            producer.setPriority(1);
//            producer.send(queue, messages[1]);
//
//            producer.setPriority(9);
//            producer.send(queue, messages[2]);
//
//            JMSConsumer consumer = jmsContext.createConsumer(queue);
//
//            for (int i = 1; i <= 3; i++) {
//                log.warn("Message {} Received: {}", i, consumer.receiveBody(String.class));
//            }
//        }
//    }
//
//    @Bean
//    public void requestReply() throws NamingException {
//        log.info("Started requestReply()");
//
//        InitialContext context = new InitialContext();
//        Queue queue = (Queue) context.lookup("queue/requestQueue");
//        Queue replyQueue = (Queue) context.lookup("queue/replyQueue");
//
//        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//             JMSContext jmsContext = cf.createContext()) {
//
//            JMSProducer producer = jmsContext.createProducer();
//            producer.send(queue, "Want to have dinner at 6:30pm?");
//
//            JMSConsumer consumer = jmsContext.createConsumer(queue);
//            log.warn("Message Received: {}", consumer.receiveBody(String.class));
//
//            JMSProducer replyProducer = jmsContext.createProducer();
//            replyProducer.send(replyQueue, "Acknowledged!");
//
//            JMSConsumer replyConsumer = jmsContext.createConsumer(replyQueue);
//            log.warn("Reply Received: {}", replyConsumer.receiveBody(String.class));
//        }
//    }
//
//    @Bean
//    public void setReplyTo() throws NamingException, JMSException {
//        log.info("Starting setReplyTo()");
//
//        InitialContext context = new InitialContext();
//        Queue queue = (Queue) context.lookup("queue/requestQueue");
//        Queue replyQueue = (Queue) context.lookup("queue/replyQueue");
//
//        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//             JMSContext jmsContext = cf.createContext()) {
//
//            JMSProducer producer = jmsContext.createProducer();
//            TextMessage message = jmsContext.createTextMessage("Want to have dinner at 6:30pm?");
//            message.setJMSReplyTo(replyQueue);
//            producer.send(queue, message);
//
//            JMSConsumer consumer = jmsContext.createConsumer(queue);
//            TextMessage messageReceived = (TextMessage) consumer.receive();
//            log.warn("Message Received: {}", messageReceived.getText());
//
//            JMSProducer replyProducer = jmsContext.createProducer();
//            replyProducer.send(messageReceived.getJMSReplyTo(), "Acknowledged!");
//
//            JMSConsumer replyConsumer = jmsContext.createConsumer(messageReceived.getJMSReplyTo());
//            log.warn("Reply Received: {}", replyConsumer.receiveBody(String.class));
//        }
//    }
//
//    @Bean
//    public void correlationId() throws NamingException, JMSException {
//        log.info("Starting correlationId()");
//
//        InitialContext context = new InitialContext();
//        Queue queue = (Queue) context.lookup("queue/requestQueue");
//
//        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//             JMSContext jmsContext = cf.createContext()) {
//
//            JMSProducer producer = jmsContext.createProducer();
//            TemporaryQueue replyQueue = jmsContext.createTemporaryQueue();
//            TextMessage message = jmsContext.createTextMessage("Want to have dinner at 6:30pm?");
//            message.setJMSReplyTo(replyQueue);
//            producer.send(queue, message);
//
//            Map<String, TextMessage> requestMessages = new HashMap<>();
//            requestMessages.put(message.getJMSMessageID(), message);
//
//            JMSConsumer consumer = jmsContext.createConsumer(queue);
//            TextMessage messageReceived = (TextMessage) consumer.receive();
//            log.warn("Message Received: {}, {}", messageReceived.getText(),
//                    messageReceived.getJMSMessageID());
//
//            JMSProducer replyProducer = jmsContext.createProducer();
//            TextMessage replyMessage = jmsContext.createTextMessage("Ack");
//            replyMessage.setJMSCorrelationID(messageReceived.getJMSMessageID());
//            replyProducer.send(messageReceived.getJMSReplyTo(), replyMessage);
//
//            JMSConsumer replyConsumer = jmsContext.createConsumer(messageReceived.getJMSReplyTo());
//            TextMessage replyReceived = (TextMessage) replyConsumer.receive();
//            log.warn("Reply Received: {}, correlation: {}", replyReceived.getText(), replyReceived.getJMSCorrelationID());
//            log.warn("{}", requestMessages.get(replyReceived.getJMSCorrelationID()));
//        }
//    }
//
//    @Bean
//    public void timeToLive() throws NamingException, InterruptedException {
//        log.info("Starting timeToLive()");
//
//        InitialContext context = new InitialContext();
//        Queue queue = (Queue) context.lookup("queue/myQueue");
//
//        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//             JMSContext jmsContext = cf.createContext()) {
//
//            JMSProducer producer = jmsContext.createProducer();
//            producer.setTimeToLive(100);
//            producer.send(queue, "I expired in two seconds!");
//            Thread.sleep(200);
//
//            Message messageReceived = jmsContext.createConsumer(queue).receive(100);
//            log.warn("Message Received: {}", messageReceived);
//
//            Queue expiryQueue = (Queue) context.lookup("queue/expiryQueue");
//            String expiredMessage = jmsContext.createConsumer(expiryQueue).receiveBody(String.class);
//            log.warn("Expired Message Received: {}", expiredMessage);
//        }
//    }
//
//    @Bean
//    public void messageDelay() throws NamingException, InterruptedException {
//        log.info("Starting messageDelay()");
//
//        InitialContext context = new InitialContext();
//        Queue queue = (Queue) context.lookup("queue/myQueue");
//
//        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
//             JMSContext jmsContext = cf.createContext()) {
//
//            JMSProducer producer = jmsContext.createProducer();
//            producer.setDeliveryDelay(500);
//            long start = System.currentTimeMillis();
//            producer.send(queue, "I was delayed");
//
//            String messageReceived = jmsContext.createConsumer(queue).receiveBody(String.class);
//            log.warn("Message Received: {} {}ms", messageReceived, System.currentTimeMillis() - start);
//        }
//    }

    @Bean
    public void messageProperties() throws NamingException, JMSException {
        log.info("Starting messageProperties()");

        InitialContext context = new InitialContext();
        Queue queue = (Queue) context.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext()) {

            JMSProducer producer = jmsContext.createProducer();
            TextMessage message = jmsContext.createTextMessage("lions and tigers are cool");

            message.setBooleanProperty("isFactual", true);
            message.setStringProperty("otherAnimalThatIsCool", "jaguar");

            producer.send(queue, message);

            JMSConsumer consumer = jmsContext.createConsumer(queue);
            Message messageReceived = consumer.receive();
            log.warn("Message Received: {}", messageReceived.getBody(String.class));
            log.warn("isFactual: {}", messageReceived.getBooleanProperty("isFactual"));
            log.warn("otherAnimalThatIsCool: {}", messageReceived.getStringProperty("otherAnimalThatIsCool"));
        }
    }
}
