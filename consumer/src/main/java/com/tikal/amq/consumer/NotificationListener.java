package com.tikal.amq.consumer;

import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by IntelliJ IDEA.
 * User: shalom
 * Date: Nov 22, 2009
 * Time: 4:23:47 PM
 */
public class NotificationListener implements MessageListener {


    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;

            LoggerFactory.getLogger(getClass()).debug("Got notification: "+textMessage.getText());
            
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }


}