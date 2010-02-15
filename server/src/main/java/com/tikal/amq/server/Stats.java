package com.tikal.amq.server;

import org.apache.activemq.broker.jmx.*;

import javax.management.*;
import javax.management.remote.*;

/**
 * Created by IntelliJ IDEA.
 * User: shalom
 * Date: Feb 14, 2010
 * Time: 4:41:53 PM
 */
public class Stats {
    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL(
                "service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(url, null);
        connector.connect();
        MBeanServerConnection connection = connector.getMBeanServerConnection();
        ObjectName name = new ObjectName(
                "my-amq-domain-name:BrokerName=AMQBroker,Type=Broker");
        BrokerViewMBean mbean = (BrokerViewMBean) MBeanServerInvocationHandler
                .newProxyInstance(connection, name, BrokerViewMBean.class, true);
        System.out.println("Statistics for broker " + mbean.getBrokerId()
                + " - " + mbean.getBrokerName());
        System.out.println("\n-----------------\n");
        System.out.println("Total message count: " + mbean.getTotalMessageCount() + "\n");
        System.out.println("Total number of consumers: " + mbean.getTotalConsumerCount());
        System.out.println("Total number of Queues: " + mbean.getQueues().length);
//               Please post comments or corrections to the Author Online Forum
//                        Licensed to Liat Petersberg <liat@tikalk.com>
//                  Draft Administering and Monitoring ActiveMQ                         Draft
        for (ObjectName queueName : mbean.getQueues()) {
            QueueViewMBean queueMbean = (QueueViewMBean) MBeanServerInvocationHandler
                    .newProxyInstance(connection, queueName,
                            QueueViewMBean.class, true);
            System.out.println("\n-----------------\n");
            System.out.println("Statistics for queue " + queueMbean.getName());
            System.out.println("Size: " + queueMbean.getQueueSize());
            System.out.println("Number of consumers: " + queueMbean.getConsumerCount());
        }
    }
}
