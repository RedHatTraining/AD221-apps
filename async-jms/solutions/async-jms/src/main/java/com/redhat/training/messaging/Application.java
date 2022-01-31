package com.redhat.training.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.redhat.training.model.Order;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.ObjectMessage;

import java.math.BigDecimal;
import java.lang.Integer;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		initializeAMQ();
		SpringApplication.run(Application.class, args);
	}

	public static void initializeAMQ() {
		System.out.println("-------------------- Initializing AMQ ----------------------");
		thread(
			new OrderProducer(
				new Integer(1),
				new BigDecimal("0.01"),
				true,
				"Order 1"
			),
			false
		);
		thread(
			new OrderProducer(
				new Integer(2),
				new BigDecimal("0.01"),
				true,
				"Order 2"
			),
			false
		);
		thread(
			new OrderProducer(
				new Integer(3),
				new BigDecimal("0.02"),
				true,
				"Order 3"
			),
			false
		);
		thread(
			new OrderProducer(
				new Integer(4),
				new BigDecimal("0.015"),
				false,
				"Order 4"
			),
			true
		);
		thread(
			new OrderProducer(
				new Integer(5),
				new BigDecimal("0.012"),
				false,
				"Order 5"
			),
			true
		);

	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

	public static class OrderProducer implements Runnable {
		Order order = new Order();

		OrderProducer(Integer Id, BigDecimal discount, Boolean delivered, String desc) {
			order.setId(Id);
			order.setDiscount(discount);
			order.setDelivered(delivered);
			order.setDescription(desc);
		}

		public Order getOrder() {
			return order;
		}

		public void run() {
			try {
				// Create a ConnectionFactory
				ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				cf.setBrokerURL("tcp://localhost:61616");
				cf.setUser("admin");
				cf.setPassword("admin");

				// Create a Connection
				Connection connection = cf.createConnection();
				connection.start();

				// Create a Session
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

				// Create the destination (Topic or Queue)
				Destination destination = session.createQueue("jms_order_input");
				Destination AMQPQueue = session.createQueue("amqp_order_input");
				Destination LogOrdersQueue = session.createTopic("log_orders");

				// Create the object message
				ObjectMessage msg = session.createObjectMessage();
				msg.setObject(order);

				// Create a MessageProducer from the Session to the Topic or Queue
				MessageProducer producer = session.createProducer(destination);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

				// Send the message
				producer.send(msg);

				// Clean up
				session.close();
				connection.close();
			}
			catch (Exception e) {
				System.out.println("Caught: " + e);
				e.printStackTrace();
			}
		}
	}

}
