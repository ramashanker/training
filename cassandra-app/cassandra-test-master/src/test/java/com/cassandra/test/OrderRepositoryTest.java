package com.cassandra.test;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import com.cassandra.test.config.CassandraConfigTest;
import com.cassandra.test.config.EmbeddedLegacyCassandra;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.log4j.Logger;
import org.apache.thrift.transport.TTransportException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.cassandra.test.domain.cassandra.Order;
import com.cassandra.test.repository.OrderRepository;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "OrderRepositoryTest")
@ContextConfiguration(classes = { CassandraConfigTest.class })
public class OrderRepositoryTest {

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS test_01 WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";
    public static final String KEYSPACE_ACTIVATE_QUERY = "USE test_01;";
    private static Logger LOGGER = Logger.getLogger(OrderRepositoryTest.class);
    @Autowired
    private OrderRepository orderRepository;

    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        EmbeddedLegacyCassandra.startEmbeddedCassandra(EmbeddedLegacyCassandra.DEFAULT_CASSANDRA_YML_FILE, 1000000L);
        final Cluster cluster = Cluster.builder()
                                       .addContactPoints("127.0.0.1")
                                       .withPort(9142)
                                       .build();
        LOGGER.info("Server Started at 127.0.0.1:9142... ");
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        LOGGER.info("KeySpace created and activated.");
        Thread.sleep(5000);
    }

    @Test
    public void test() {
        final Order order = new Order();
        order.setOrderID("1212");
        order.setAmount(1212.12f);
        order.setDiscount(12.12f);

        orderRepository.save(order);
        final Iterable<Order> rules = orderRepository.findByOrderId(order.getOrderID());
        assertEquals(order.getOrderID(),
                     rules.iterator()
                          .next()
                          .getOrderID());
    }

}
