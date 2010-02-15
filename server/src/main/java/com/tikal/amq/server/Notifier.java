package com.tikal.amq.server;

import org.slf4j.LoggerFactory;
import org.springframework.jms.core.*;

import javax.jms.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: shalom
 * Date: Jan 11, 2010
 * Time: 10:38:03 PM
 */
public class Notifier extends TimerTask{

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void run() {
        jmsTemplate.send(new MessageCreator(){
            @Override
            public Message createMessage(Session session) throws JMSException {
                String date = new Date().toString();
                LoggerFactory.getLogger(getClass()).debug("Sending notification at "+date);
                Message message = session.createTextMessage("Notification at "+date);
                message.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
                return message;
            }
        });
    }
}
