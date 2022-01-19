package com.redhat.training.transform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.redhat.training.model.OrderItem;
import com.redhat.training.model.CatalogItem;
import com.redhat.training.model.Order;
import com.redhat.training.model.Customer;

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
				new Customer(
					"Frank",
					"Smith",
					"fsmith",
					"password",
					"fsmith@email.com",
					false
				)
			),
			false
		);
		thread(
			new OrderProducer(
				new Integer(2),
				new BigDecimal("0.01"),
				true,
				new Customer(
					"John",
					"Smith",
					"jsmith",
					"password",
					"jsmith@email.com",
					false
				)
			),
			false
		);
		thread(
			new OrderProducer(
				new Integer(3),
				new BigDecimal("0.02"),
				true,
				new Customer(
					"Jerry",
					"Jones",
					"jjones",
					"password",
					"jjones@email.com",
					false
				)
			),
			false
		);
		thread(
			new OrderProducer(
				new Integer(4),
				new BigDecimal("0.015"),
				false,
				new Customer(
					"Jess",
					"Thompson",
					"jthompson",
					"password",
					"jthompson@email.com",
					false
				)
			),
			true
		);
		thread(
			new OrderProducer(
				new Integer(5),
				new BigDecimal("0.012"),
				false,
				new Customer(
					"Tony",
					"Anderson",
					"tanderson",
					"password",
					"tanderson@email.com",
					false
				)
			),
			true
		);

	}

	public static OrderItem getOrderItem(int id, int qty, float price, CatalogItem item) {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(new Integer(id));
		orderItem.setQuantity(new Integer(qty));
		orderItem.setExtPrice(new BigDecimal(price));
		orderItem.setItem(item);

		return orderItem;
	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

	public static class OrderProducer implements Runnable {
		Order order = new Order();

		OrderProducer(Integer Id, BigDecimal discount, Boolean delivered, Customer customer) {
			CatalogItem ci1 = new CatalogItem("ci1", "catalog item 1", new BigDecimal("5"),
			"catalog item 1 description", "ci1 author", "path/to/ci1.png",
			"category ci1", true);

			CatalogItem ci2 = new CatalogItem("ci2", "catalog item 2", new BigDecimal("10"),
			"catalog item 2 description", "ci2 author", "path/to/c21.png",
			"category ci2", true);

			OrderItem oi1 = getOrderItem(1, 2, 10.0f , ci1);
			OrderItem oi2 = getOrderItem(2, 3, 30.0f , ci2);

			order.getOrderItems().add(oi1);
			order.getOrderItems().add(oi2);
			order.setId(Id);
			order.setDiscount(discount);
			order.setDelivered(delivered);
			order.setCustomer(customer);
			customer.getOrders().add(order);
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
				Destination destination = session.createQueue("orderInput");

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
